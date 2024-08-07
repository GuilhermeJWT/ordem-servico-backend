package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.response.ContasPagarResponse;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelContasPagarDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ContasPagarReceberNaoEncontradaException;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasPagar;
import br.com.systemsgs.ordem_servico_backend.repository.ContasPagarRepository;
import br.com.systemsgs.ordem_servico_backend.service.ContasPagarService;
import br.com.systemsgs.ordem_servico_backend.util.UtilFornecedores;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "contaspagar")
@Service
public class ContasPagarServiceImpl implements ContasPagarService {

    @Autowired
    private ContasPagarRepository contasPagarRepository;

    @Autowired
    private UtilFornecedores utilFornecedores;

    @Autowired
    private ModelMapper mapper;

    @Cacheable(value = "contaspagar", key = "#id")
    @Override
    public ContasPagarResponse pesquisaPorId(Long id) {
        var pesquisaConta = contasPagarRepository.findById(id).orElseThrow(() -> new ContasPagarReceberNaoEncontradaException());

        return converteEntidadeEmResponse(pesquisaConta);
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
        modelContasPagar.setData_vencimento(modelContasPagarDTO.getData_vencimento());
        modelContasPagar.setStatusContas(modelContasPagarDTO.getStatusContas());

        var contaSalva = contasPagarRepository.save(modelContasPagar);

        return converteEntidadeEmResponse(contaSalva);
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
