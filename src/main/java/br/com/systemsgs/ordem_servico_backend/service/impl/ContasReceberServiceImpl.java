package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelContasReceberDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.ContasReceberResponse;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ContasPagarReceberNaoEncontradaException;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasReceber;
import br.com.systemsgs.ordem_servico_backend.repository.ContasReceberRepository;
import br.com.systemsgs.ordem_servico_backend.service.ContasReceberService;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
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

@CacheConfig(cacheNames = "contasreceber")
@Service
public class ContasReceberServiceImpl implements ContasReceberService {

    @Autowired
    private ContasReceberRepository contasReceberRepository;

    @Autowired
    private UtilClientes utilClientes;

    @Autowired
    private ModelMapper mapper;

    @Cacheable(value = "contasreceber", key = "#id")
    @Override
    public ContasReceberResponse pesquisaPorId(Long id) {
        var pesquisaContaReceber = contasReceberRepository.findById(id)
                .orElseThrow(() -> new ContasPagarReceberNaoEncontradaException());

        return converteEntidadeEmResponse(pesquisaContaReceber);
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

        modelContasReceber.setData_vencimento(modelContasReceberDTO.getData_vencimento());
        modelContasReceber.setValor(modelContasReceberDTO.getValor());
        modelContasReceber.setObservacao(modelContasReceberDTO.getObservacao());
        modelContasReceber.setFormaPagamento(modelContasReceberDTO.getFormaPagamento());
        modelContasReceber.setStatusContasReceber(modelContasReceberDTO.getStatusContas());
        modelContasReceber.setCliente(cliente);

        var contaReceberSalva = contasReceberRepository.save(modelContasReceber);

        return converteEntidadeEmResponse(contaReceberSalva);
    }

    @CachePut(value = "contasreceber", key = "#id")
    @Override
    public ContasReceberResponse alterarContasReceber(Long id, ModelContasReceberDTO modelContasReceberDTO) {
        ModelContasReceber contaReceberPesquisada = pesquisaContasReceberPeloId(modelContasReceberDTO.getId());
        mapper.map(modelContasReceberDTO, ModelContasReceber.class);
        BeanUtils.copyProperties(modelContasReceberDTO, contaReceberPesquisada, "id");

        var contaReceberAtualizada = contasReceberRepository.save(contaReceberPesquisada);

        return converteEntidadeEmResponse(contaReceberAtualizada);
    }

    @CacheEvict(value = "contasreceber", key = "#id")
    @Override
    public void deletarContasReceber(Long id) {
        contasReceberRepository.deleteById(id);
    }

    private List<ContasReceberResponse> converteListaContasResponse(List<ModelContasReceber> listModelsContasReceber){
        return listModelsContasReceber.stream().map(modelContasReceber -> mapper.map(modelContasReceber, ContasReceberResponse.class)).toList();
    }

    private ContasReceberResponse converteEntidadeEmResponse(ModelContasReceber modelContasReceber){
        return mapper.map(modelContasReceber, ContasReceberResponse.class);
    }

    private ModelContasReceber pesquisaContasReceberPeloId(Long id){
        ModelContasReceber pesquisaContaReceber = contasReceberRepository.
                findById(id).orElseThrow(() -> new ContasPagarReceberNaoEncontradaException());

        return pesquisaContaReceber;
    }
}
