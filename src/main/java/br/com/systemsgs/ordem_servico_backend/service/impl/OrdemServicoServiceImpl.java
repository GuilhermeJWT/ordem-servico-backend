package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.exception.ClienteNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.repository.OrdemServicoRepository;
import br.com.systemsgs.ordem_servico_backend.service.OrdemServicoService;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import br.com.systemsgs.ordem_servico_backend.util.UtilOrdemServico;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdemServicoServiceImpl implements OrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private UtilOrdemServico utilOrdemServico;

    @Autowired
    private UtilClientes utilClientes;

    @Override
    public ModelOrdemServico pesquisaPorId(Long id) {
        Optional<ModelOrdemServico> modelOrdemServico = ordemServicoRepository.findById(id);
        return modelOrdemServico.orElseThrow(() -> new RecursoNaoEncontradoException());
    }

    @Override
    public List<ModelOrdemServico> listarOS() {
        return ordemServicoRepository.findAll();
    }

    @Override
    public ModelOrdemServico salvarOS(ModelOrdemServico modelOrdemServico) {
         validaCliente(modelOrdemServico);
        return ordemServicoRepository.save(modelOrdemServico);
    }

    @Override
    public void deletarOS(Long id) {
        ordemServicoRepository.deleteById(id);
    }

    @Override
    public ModelOrdemServico atualizarOS(Long id, ModelOrdemServico modelOrdemServico) {
        ModelOrdemServico osPesquisada = utilOrdemServico.pesquisaOsPorId(id);
        BeanUtils.copyProperties(modelOrdemServico, osPesquisada, "id");

        return ordemServicoRepository.save(osPesquisada);
    }

    public ModelOrdemServico validaCliente(ModelOrdemServico modelOrdemServico){
        ModelClientes pesquisaCliente = utilClientes.pesquisarClientePeloId(modelOrdemServico.getCliente().getId());

        if(pesquisaCliente == null){
            throw new ClienteNaoEncontradoException();
        }
        modelOrdemServico.getCliente().setNome(pesquisaCliente.getNome());
        modelOrdemServico.getCliente().setCpf(pesquisaCliente.getCpf());
        modelOrdemServico.getCliente().setCelular(pesquisaCliente.getCelular());
        modelOrdemServico.getCliente().setEmail(pesquisaCliente.getEmail());
        modelOrdemServico.getCliente().setEndereco(pesquisaCliente.getEndereco());
        modelOrdemServico.getCliente().setCidade(pesquisaCliente.getCidade());
        modelOrdemServico.getCliente().setEstado(pesquisaCliente.getEstado());
        modelOrdemServico.getCliente().setCep(pesquisaCliente.getCep());

        return modelOrdemServico;
    }
}
