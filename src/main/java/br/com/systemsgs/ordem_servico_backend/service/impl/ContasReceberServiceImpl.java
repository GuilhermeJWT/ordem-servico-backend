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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "contasreceber")
@Service
public class ContasReceberServiceImpl implements ContasReceberService {

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

    private List<ContasReceberResponse> converteListaContasResponse(List<ModelContasReceber> listModelsContasReceber){
        return listModelsContasReceber.stream().map(modelContasReceber -> mapper.map(modelContasReceber, ContasReceberResponse.class)).toList();
    }

    private ContasReceberResponse converteEntidadeEmResponse(ModelContasReceber modelContasReceber){
        return mapper.map(modelContasReceber, ContasReceberResponse.class);
    }

}
