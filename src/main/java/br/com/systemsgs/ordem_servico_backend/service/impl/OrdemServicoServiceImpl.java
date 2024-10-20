package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelOrdemServicoDTO;
import br.com.systemsgs.ordem_servico_backend.enums.TipoRelatorio;
import br.com.systemsgs.ordem_servico_backend.exception.errors.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.repository.OrdemServicoRepository;
import br.com.systemsgs.ordem_servico_backend.service.GerarRelatorioService;
import br.com.systemsgs.ordem_servico_backend.service.OrdemServicoService;
import br.com.systemsgs.ordem_servico_backend.util.BaseRelatorioUtil;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import br.com.systemsgs.ordem_servico_backend.util.UtilOrdemServico;
import br.com.systemsgs.ordem_servico_backend.util.UtilTecnicoResponsavel;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CacheConfig(cacheNames = "os")
@Service
public class OrdemServicoServiceImpl extends BaseRelatorioUtil implements OrdemServicoService, GerarRelatorioService {

    private static final String NOME_RELATORIO = "Relatório de Ordem de Serviço";
    private static final String[] COLUNAS = {"ID", "Data Emissão", "Data Vencimento", "Defeito", "Laudo Técnico", "Cliente", "Técnico", "Status"};

    private final OrdemServicoRepository ordemServicoRepository;
    private final UtilOrdemServico utilOrdemServico;
    private final UtilClientes utilClientes;
    private final ModelMapper mapper;
    private final UtilTecnicoResponsavel utilTecnicoResponsavel;

    @Autowired
    public OrdemServicoServiceImpl(OrdemServicoRepository ordemServicoRepository,
                                   UtilOrdemServico utilOrdemServico,
                                   UtilClientes utilClientes,
                                   ModelMapper mapper,
                                   UtilTecnicoResponsavel utilTecnicoResponsavel) {
        this.ordemServicoRepository = ordemServicoRepository;
        this.utilOrdemServico = utilOrdemServico;
        this.utilClientes = utilClientes;
        this.mapper = mapper;
        this.utilTecnicoResponsavel = utilTecnicoResponsavel;
    }

    @Cacheable(value = "os", key = "#id")
    @Override
    public ModelOrdemServico pesquisaPorId(Long id) {
        return ordemServicoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException());
    }

    @Cacheable(value = "os")
    @Override
    public List<ModelOrdemServico> listarOS() {
        return ordemServicoRepository.findAll();
    }

    @Transactional
    @Override
    public ModelOrdemServico salvarOS(ModelOrdemServicoDTO modelOrdemServicoDTO) {
         validaCliente(modelOrdemServicoDTO);
         utilTecnicoResponsavel.validaTecnicoExistente(modelOrdemServicoDTO);

        return ordemServicoRepository.save(mapper.map(modelOrdemServicoDTO, ModelOrdemServico.class));
    }

    @CacheEvict(value = "os", key = "#id")
    @Override
    public void deletarOS(Long id) {
        ordemServicoRepository.deleteById(id);
    }

    @CachePut(value = "os", key = "#id")
    @Override
    public ModelOrdemServico atualizarOS(Long id, ModelOrdemServicoDTO modelOrdemServicoDTO) {
        ModelOrdemServico osPesquisada = utilOrdemServico.pesquisaOsPorId(id);
        validaCliente(modelOrdemServicoDTO);
        utilTecnicoResponsavel.validaTecnicoExistente(modelOrdemServicoDTO);

        mapper.map(modelOrdemServicoDTO, ModelClientes.class);

        return ordemServicoRepository.save(osPesquisada);
    }

    public ModelOrdemServicoDTO validaCliente(ModelOrdemServicoDTO modelOrdemServicoDTO){
        ModelClientes pesquisaCliente = utilClientes.pesquisarClientePeloId(modelOrdemServicoDTO.getCliente().getId());

        modelOrdemServicoDTO.getCliente().setNome(pesquisaCliente.getNome());
        modelOrdemServicoDTO.getCliente().setCpf(pesquisaCliente.getCpf());
        modelOrdemServicoDTO.getCliente().setCelular(pesquisaCliente.getCelular());
        modelOrdemServicoDTO.getCliente().setEmail(pesquisaCliente.getEmail());
        modelOrdemServicoDTO.getCliente().setEndereco(pesquisaCliente.getEndereco());

        return modelOrdemServicoDTO;
    }

    @Override
    public ResponseEntity<byte[]> gerarRelatorioExcel(HttpServletResponse response) throws IOException {
        List<ModelOrdemServico> os = ordemServicoRepository.findAll();

        byte[] excelData = configuraRelatorioExcel(NOME_RELATORIO,os, COLUNAS, (row, ordem) -> {
            row.createCell(0).setCellValue(ordem.getId());
            row.createCell(1).setCellValue(ordem.getDataInicial());
            row.createCell(2).setCellValue(ordem.getDataFinal());
            row.createCell(3).setCellValue(ordem.getDefeito());
            row.createCell(4).setCellValue(ordem.getLaudoTecnico());
            row.createCell(5).setCellValue(ordem.getCliente().getNome());
            row.createCell(6).setCellValue(ordem.getTecnicoResponsavel().getNome());
            row.createCell(5).setCellValue(String.valueOf(ordem.getStatus()));
        });

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=relatorio-ordem-servico.xlsx");

        return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }

    @Override
    public byte[] gerarRelatorioPdf() throws IOException {
        List<ModelOrdemServico> os = ordemServicoRepository.findAll();
        List<String[]> dados = new ArrayList<>();
        float[] tamanhoColunas = {1, 2, 2, 4, 4, 3, 3, 2};

        for (ModelOrdemServico ordem : os) {
            String[] linha = {
                    String.valueOf(ordem.getId()),
                    String.valueOf(ordem.getDataInicial()),
                    String.valueOf(ordem.getDataFinal()),
                    String.valueOf(ordem.getDefeito()),
                    String.valueOf(ordem.getLaudoTecnico()),
                    String.valueOf(ordem.getCliente().getNome()),
                    String.valueOf(ordem.getTecnicoResponsavel().getNome()),
                    String.valueOf(ordem.getStatus())
            };
            dados.add(linha);
        }
        return configuraRelatorioPdf(NOME_RELATORIO, COLUNAS, dados, tamanhoColunas, TipoRelatorio.PAISAGEM);
    }
}
