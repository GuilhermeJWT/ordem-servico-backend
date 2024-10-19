package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.response.ContasPagarResponse;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelContasPagarDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ContasPagarReceberNaoEncontradaException;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasPagar;
import br.com.systemsgs.ordem_servico_backend.repository.ContasPagarRepository;
import br.com.systemsgs.ordem_servico_backend.service.ContasPagarService;
import br.com.systemsgs.ordem_servico_backend.service.GerarRelatorioService;
import br.com.systemsgs.ordem_servico_backend.util.BaseRelatorioUtil;
import br.com.systemsgs.ordem_servico_backend.util.UtilFornecedores;
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

@CacheConfig(cacheNames = "contaspagar")
@Service
public class ContasPagarServiceImpl extends BaseRelatorioUtil implements ContasPagarService, GerarRelatorioService {

    private static final String NOME_RELATORIO = "Relatório de Contas a Pagar";
    private static final String[] COLUNAS = {"ID", "Data Emissão", "Data Vencimento", "Valor", "Status", "Fornecedor"};

    private final ContasPagarRepository contasPagarRepository;
    private final UtilFornecedores utilFornecedores;
    private final ModelMapper mapper;

    @Autowired
    public ContasPagarServiceImpl(ContasPagarRepository contasPagarRepository,
                                  UtilFornecedores utilFornecedores,
                                  ModelMapper mapper) {
        this.contasPagarRepository = contasPagarRepository;
        this.utilFornecedores = utilFornecedores;
        this.mapper = mapper;
    }

    @Cacheable(value = "contaspagar", key = "#id")
    @Override
    public ContasPagarResponse pesquisaPorId(Long id) {
        return converteEntidadeEmResponse(contasPagarRepository.findById(id).orElseThrow(() -> new ContasPagarReceberNaoEncontradaException()));
    }

    @Cacheable(value = "contaspagar")
    @Override
    public List<ContasPagarResponse> listarContasPagar() {
        return converteListaContasResponse(contasPagarRepository.findAll());
    }

    @Transactional
    @Override
    public ContasPagarResponse cadastrarContasPagar(ModelContasPagarDTO modelContasPagarDTO) {
        ModelContasPagar modelContasPagar = new ModelContasPagar();

        var fornecedor = utilFornecedores.pesquisarFornecedorPeloId(modelContasPagarDTO.getFornecedor());

        modelContasPagar.setFornecedor(fornecedor);
        modelContasPagar.setObservacao(modelContasPagarDTO.getObservacao());
        modelContasPagar.setValor(modelContasPagarDTO.getValor());
        modelContasPagar.setFormaPagamento(modelContasPagarDTO.getFormaPagamento());
        modelContasPagar.setDataVencimento(modelContasPagarDTO.getDataVencimento());
        modelContasPagar.setStatusContas(modelContasPagarDTO.getStatusContas());

        return converteEntidadeEmResponse(contasPagarRepository.save(modelContasPagar));
    }

    @CacheEvict(value = "contaspagar", key = "#id")
    @Override
    public void deletarContasPagar(Long id) {
        contasPagarRepository.deleteById(id);
    }

    @CachePut(value = "contaspagar", key = "#id")
    @Override
    public ContasPagarResponse alterarContasPagar(Long id, ModelContasPagarDTO modelContasPagarDTO) {
        ModelContasPagar contasPagarPesquisada = pesquisaContasPagarPeloId(modelContasPagarDTO.getId());
        mapper.map(modelContasPagarDTO, ModelContasPagar.class);
        BeanUtils.copyProperties(modelContasPagarDTO, contasPagarPesquisada, "id");

        var contasPagarAtualizada = contasPagarRepository.save(contasPagarPesquisada);

        return converteEntidadeEmResponse(contasPagarAtualizada);
    }

    @Override
    public Page<ContasPagarResponse> listarContasPagarPaginada(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return contasPagarRepository.findAll(pageable).map(contas -> mapper.map(contas, ContasPagarResponse.class));
    }

    @Override
    public ResponseEntity<byte[]> gerarRelatorioExcel(HttpServletResponse response) throws IOException {
        List<ModelContasPagar> contasPagar = contasPagarRepository.findAll();

        byte[] excelData = configuraRelatorioExcel(NOME_RELATORIO,contasPagar, COLUNAS, (row, conta) -> {
            row.createCell(0).setCellValue(conta.getId());
            row.createCell(1).setCellValue(conta.getDataEmissao());
            row.createCell(2).setCellValue(conta.getDataVencimento());
            row.createCell(3).setCellValue(String.valueOf(conta.getValor()));
            row.createCell(4).setCellValue(String.valueOf(conta.getStatusContas()));
            row.createCell(5).setCellValue(conta.getFornecedor().getNome());
        });

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=relatorio-contas-pagar.xlsx");

        return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }

    @Override
    public byte[] gerarRelatorioPdf() throws IOException {
        List<ModelContasPagar> contasPagar = contasPagarRepository.findAll();
        List<String[]> dados = new ArrayList<>();
        float[] tamanhoColunas = {1, 3, 3, 2, 2, 3};

        for (ModelContasPagar conta : contasPagar) {
            String[] linha = {
                    String.valueOf(conta.getId()),
                    String.valueOf(conta.getDataEmissao()),
                    String.valueOf(conta.getDataVencimento()),
                    String.valueOf(conta.getValor()),
                    String.valueOf(conta.getStatusContas()),
                    String.valueOf(conta.getFornecedor().getNome())
            };
            dados.add(linha);
        }
        return configuraRelatorioPdf(NOME_RELATORIO, COLUNAS, dados, tamanhoColunas);
    }

    private List<ContasPagarResponse> converteListaContasResponse(List<ModelContasPagar> listModelsContasPagar){
      return listModelsContasPagar.stream().map(modelContasPagar -> mapper.map(modelContasPagar, ContasPagarResponse.class)).toList();
    }

    private ContasPagarResponse converteEntidadeEmResponse(ModelContasPagar modelContasPagar){
        return mapper.map(modelContasPagar, ContasPagarResponse.class);
    }

    private ModelContasPagar pesquisaContasPagarPeloId(Long id){
        return contasPagarRepository.findById(id).orElseThrow(() -> new ContasPagarReceberNaoEncontradaException());
    }
}
