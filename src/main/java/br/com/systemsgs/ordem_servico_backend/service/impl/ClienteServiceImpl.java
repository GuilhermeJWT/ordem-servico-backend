package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import br.com.systemsgs.ordem_servico_backend.service.ClienteService;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UtilClientes utilClientes;

    @Override
    public ModelClientes pesquisaPorId(Long id) {
        Optional<ModelClientes> modelClientes = clienteRepository.findById(id);
        return modelClientes.orElseThrow(() -> new RecursoNaoEncontradoException());
    }

    @Override
    public List<ModelClientes> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public ModelClientes salvarClientes(ModelClientes modelClientes) {
        return clienteRepository.save(modelClientes);
    }

    @Override
    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public ModelClientes updateClientes(Long id, ModelClientes modelClientes) {
        ModelClientes clientePesquisado = utilClientes.pesquisarClientePeloId(id);
        BeanUtils.copyProperties(modelClientes, clientePesquisado, "id");

        return clienteRepository.save(clientePesquisado);
    }
}
