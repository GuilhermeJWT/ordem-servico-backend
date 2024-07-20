package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.response.ContasPagarResponse;
import br.com.systemsgs.ordem_servico_backend.dto.ModelContasPagarDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ContasPagarNaoEncontradaException;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasPagar;
import br.com.systemsgs.ordem_servico_backend.repository.ContasPagarRepository;
import br.com.systemsgs.ordem_servico_backend.service.ContasPagarService;
import br.com.systemsgs.ordem_servico_backend.util.UtilFornecedores;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContasPagarServiceImpl implements ContasPagarService {

    @Autowired
    private ContasPagarRepository contasPagarRepository;

    @Autowired
    private UtilFornecedores utilFornecedores;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ContasPagarResponse pesquisaPorId(Long id) {
        ContasPagarResponse contasPagarResponse = new ContasPagarResponse();

        var pesquisaConta = contasPagarRepository.findById(id).orElseThrow(() -> new ContasPagarNaoEncontradaException());

        contasPagarResponse.setCodigoContaPagar(pesquisaConta.getId());
        contasPagarResponse.setData_vencimento(pesquisaConta.getData_vencimento());
        contasPagarResponse.setValor_conta_pagar(pesquisaConta.getValor());
        contasPagarResponse.setObservacao(pesquisaConta.getObservacao());
        contasPagarResponse.setFormaPagamento(String.valueOf(pesquisaConta.getFormaPagamento()));
        contasPagarResponse.setStatusContaPagar(String.valueOf(pesquisaConta.getStatusContasPagar()));
        contasPagarResponse.setNomeFornecedor(pesquisaConta.getFornecedor().getNome());

        return contasPagarResponse;
    }

    @Override
    public List<ModelContasPagar> listarContasPagar() {
        return contasPagarRepository.findAll();
    }

    @Override
    public ModelContasPagar cadastrarContasPagar(ModelContasPagarDTO modelContasPagarDTO) {
        ModelContasPagar modelContasPagar = new ModelContasPagar();

        var fornecedor = utilFornecedores.pesquisarFornecedorPeloId(modelContasPagarDTO.getCodigoFornecedor());

        modelContasPagar.setFornecedor(fornecedor);
        modelContasPagar.setObservacao(modelContasPagarDTO.getObservacao());
        modelContasPagar.setValor(modelContasPagarDTO.getValor());
        modelContasPagar.setFormaPagamento(modelContasPagarDTO.getFormaPagamento());
        modelContasPagar.setData_vencimento(modelContasPagarDTO.getData_vencimento());
        modelContasPagar.setStatusContasPagar(modelContasPagarDTO.getStatusContasPagar());

        contasPagarRepository.save(modelContasPagar);

        return modelContasPagar;
    }

    @Override
    public void deletarContasPagar(Long id) {
        contasPagarRepository.deleteById(id);
    }

    @Override
    public ModelContasPagar alterarContasPAgar(Long id, ModelContasPagarDTO modelContasPagarDTO) {
        ModelContasPagar contasPagarPesquisada = pesquisaContasPagarPeloId(modelContasPagarDTO.getId());
        mapper.map(modelContasPagarDTO, ModelContasPagar.class);
        BeanUtils.copyProperties(modelContasPagarDTO, contasPagarPesquisada, "id");

        return contasPagarRepository.save(contasPagarPesquisada);
    }

    public ModelContasPagar pesquisaContasPagarPeloId(Long id){
        ModelContasPagar pesquisaContaPagar = contasPagarRepository.
                findById(id).orElseThrow(() -> new ContasPagarNaoEncontradaException());

        return pesquisaContaPagar;
    }
}
