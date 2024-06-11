package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import br.com.systemsgs.ordem_servico_backend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

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
}
