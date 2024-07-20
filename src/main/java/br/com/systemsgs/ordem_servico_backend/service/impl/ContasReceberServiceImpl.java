package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.ModelContasReceberDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.ContasReceberResponse;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ContasPagarReceberNaoEncontradaException;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasReceber;
import br.com.systemsgs.ordem_servico_backend.repository.ContasReceberRepository;
import br.com.systemsgs.ordem_servico_backend.service.ContasReceberService;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContasReceberServiceImpl implements ContasReceberService {

    @Autowired
    private ContasReceberRepository contasReceberRepository;

    @Autowired
    private UtilClientes utilClientes;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ContasReceberResponse pesquisaPorId(Long id) {
        ContasReceberResponse contasReceberResponse = new ContasReceberResponse();

        var pesquisaContaReceber = contasReceberRepository.findById(id)
                .orElseThrow(() -> new ContasPagarReceberNaoEncontradaException());

        contasReceberResponse.setCodigoContaReceber(pesquisaContaReceber.getId());
        contasReceberResponse.setData_vencimento(pesquisaContaReceber.getData_vencimento());
        contasReceberResponse.setValor_conta_receber(pesquisaContaReceber.getValor());
        contasReceberResponse.setObservacao(pesquisaContaReceber.getObservacao());
        contasReceberResponse.setFormaPagamento(String.valueOf(pesquisaContaReceber.getFormaPagamento()));
        contasReceberResponse.setStatusContaPagar(String.valueOf(pesquisaContaReceber.getStatusContasPagar()));
        contasReceberResponse.setNomeCliente(pesquisaContaReceber.getCliente().getNome());

        return contasReceberResponse;
    }

    @Override
    public List<ModelContasReceber> listarContasReceber() {
        return contasReceberRepository.findAll();
    }

    @Override
    public ModelContasReceber cadastrarContasReceber(ModelContasReceberDTO modelContasReceberDTO) {
        ModelContasReceber modelContasReceber = new ModelContasReceber();

        var cliente = utilClientes.pesquisarClientePeloId(modelContasReceberDTO.getCodigoCliente());

        modelContasReceber.setData_vencimento(modelContasReceberDTO.getData_vencimento());
        modelContasReceber.setValor(modelContasReceberDTO.getValor());
        modelContasReceber.setObservacao(modelContasReceberDTO.getObservacao());
        modelContasReceber.setFormaPagamento(modelContasReceberDTO.getFormaPagamento());
        modelContasReceber.setStatusContasPagar(modelContasReceberDTO.getStatusContasPagar());
        modelContasReceber.setCliente(cliente);

        contasReceberRepository.save(modelContasReceber);

        return modelContasReceber;
    }

    @Override
    public ModelContasReceber alterarContasReceber(Long id, ModelContasReceberDTO modelContasReceberDTO) {
        ModelContasReceber contaReceberPesquisada = pesquisaContasReceberPeloId(modelContasReceberDTO.getId());
        mapper.map(modelContasReceberDTO, ModelContasReceber.class);
        BeanUtils.copyProperties(modelContasReceberDTO, contaReceberPesquisada, "id");

        return contasReceberRepository.save(contaReceberPesquisada);
    }

    @Override
    public void deletarContasReceber(Long id) {
        contasReceberRepository.deleteById(id);
    }

    public ModelContasReceber pesquisaContasReceberPeloId(Long id){
        ModelContasReceber pesquisaContaReceber = contasReceberRepository.
                findById(id).orElseThrow(() -> new ContasPagarReceberNaoEncontradaException());

        return pesquisaContaReceber;
    }
}
