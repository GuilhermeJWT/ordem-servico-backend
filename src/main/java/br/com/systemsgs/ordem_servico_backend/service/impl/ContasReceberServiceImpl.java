package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelContasReceberDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.ContasReceberResponse;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ContasPagarReceberNaoEncontradaException;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasReceber;
import br.com.systemsgs.ordem_servico_backend.repository.ContasReceberRepository;
import br.com.systemsgs.ordem_servico_backend.service.ContasReceberService;
import br.com.systemsgs.ordem_servico_backend.service.GerarRelatorioService;
import br.com.systemsgs.ordem_servico_backend.util.BaseRelatorioUtil;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CacheConfig(cacheNames = "contasreceber")
@Service
public class ContasReceberServiceImpl extends BaseRelatorioUtil implements ContasReceberService, GerarRelatorioService {

    private static final String NOME_RELATORIO = "Relatório de Contas a Receber";
    private static final String[] COLUNAS = {"ID", "Data Emissão", "Data Vencimento", "Valor", "Status", "Cliente"};

    private final ContasReceberRepository contasReceberRepository;
    private final UtilClientes utilClientes;
    private final ModelMapper mapper;

    @Autowired
    public ContasReceberServiceImpl(ContasReceberRepository contasReceberRepository,
                                    UtilClientes utilClientes,
                                    ModelMapper mapper) {
        this.contasReceberRepository = contasReceberRepository;
        this.utilClientes = utilClientes;
        this.mapper = mapper;
    }

    @Cacheable(value = "contasreceber", key = "#id")
    @Override
    public ContasReceberResponse pesquisaPorId(Long id) {
        return converteEntidadeEmResponse(contasReceberRepository.findById(id).orElseThrow(() -> new ContasPagarReceberNaoEncontradaException()));
    }

    @Cacheable(value = "contasreceber")
    @Override
    public List<ContasReceberResponse> listarContasReceber() {
        return converteListaContasResponse(contasReceberRepository.findAll());
    }

    @Transactional
    @Override
    public ContasReceberResponse cadastrarContasReceber(ModelContasReceberDTO modelContasReceberDTO) {
        ModelContasReceber modelContasReceber = new ModelContasReceber();

        var cliente = utilClientes.pesquisarClientePeloId(modelContasReceberDTO.getCodigoCliente());

        modelContasReceber.setDataVencimento(modelContasReceberDTO.getDataVencimento());
        modelContasReceber.setValor(modelContasReceberDTO.getValor());
        modelContasReceber.setObservacao(modelContasReceberDTO.getObservacao());
        modelContasReceber.setFormaPagamento(modelContasReceberDTO.getFormaPagamento());
        modelContasReceber.setStatusContasReceber(modelContasReceberDTO.getStatusContas());
        modelContasReceber.setCliente(cliente);

        return converteEntidadeEmResponse(contasReceberRepository.save(modelContasReceber));
    }

    @CachePut(value = "contasreceber", key = "#id")
    @Override
    public ContasReceberResponse alterarContasReceber(Long id, ModelContasReceberDTO modelContasReceberDTO) {
        ModelContasReceber contaReceberPesquisada = contasReceberRepository.findById(modelContasReceberDTO.getId())
                .orElseThrow(() -> new ContasPagarReceberNaoEncontradaException());
        mapper.map(modelContasReceberDTO, ModelContasReceber.class);
        BeanUtils.copyProperties(modelContasReceberDTO, contaReceberPesquisada, "id");

        var contaReceberAtualizada = contasReceberRepository.save(contaReceberPesquisada);

        return converteEntidadeEmResponse(contaReceberAtualizada);
    }

    @Override
    public Page<ContasReceberResponse> listarContasReceberPaginada(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return contasReceberRepository.findAll(pageable).map(contas -> mapper.map(contas, ContasReceberResponse.class));
    }

    @CacheEvict(value = "contasreceber", key = "#id")
    @Override
    public void deletarContasReceber(Long id) {
        contasReceberRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<byte[]> gerarRelatorioExcel(HttpServletResponse response) throws IOException {
        List<ModelContasReceber> contasReceber = contasReceberRepository.findAll();

        byte[] excelData = configuraRelatorioExcel(NOME_RELATORIO,contasReceber, COLUNAS, (row, conta) -> {
            row.createCell(0).setCellValue(conta.getId());
            row.createCell(1).setCellValue(conta.getDataEmissao());
            row.createCell(2).setCellValue(conta.getDataVencimento());
            row.createCell(3).setCellValue(String.valueOf(conta.getValor()));
            row.createCell(4).setCellValue(String.valueOf(conta.getStatusContasReceber()));
            row.createCell(5).setCellValue(conta.getCliente().getNome());
        });

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=relatorio-contas-receber.xlsx");

        return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }

    @Override
    public byte[] gerarRelatorioPdf() throws IOException {
        List<ModelContasReceber> contasReceber = contasReceberRepository.findAll();
        List<String[]> dados = new ArrayList<>();
        float[] tamanhoColunas = {1, 3, 3, 2, 2, 3};

        for (ModelContasReceber conta : contasReceber) {
            String[] linha = {
                    String.valueOf(conta.getId()),
                    String.valueOf(conta.getDataEmissao()),
                    String.valueOf(conta.getDataVencimento()),
                    String.valueOf(conta.getValor()),
                    String.valueOf(conta.getStatusContasReceber()),
                    String.valueOf(conta.getCliente().getNome())
            };
            dados.add(linha);
        }
        return configuraRelatorioPdf(NOME_RELATORIO, COLUNAS, dados, tamanhoColunas);
    }

    private List<ContasReceberResponse> converteListaContasResponse(List<ModelContasReceber> listModelsContasReceber){
        return listModelsContasReceber.stream().map(modelContasReceber -> mapper.map(modelContasReceber, ContasReceberResponse.class)).toList();
    }

    private ContasReceberResponse converteEntidadeEmResponse(ModelContasReceber modelContasReceber){
        return mapper.map(modelContasReceber, ContasReceberResponse.class);
    }
}
