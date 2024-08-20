package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.exception.errors.ClienteNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UtilClientes {

    private final ClienteRepository clienteRepository;

    @Autowired
    public UtilClientes(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ModelClientes pesquisarClientePeloId(Long id){
        return clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException());
    }

    public Optional<Integer> somaQuantidadeClientesCadastrados(){
        return clienteRepository.somaClientesCadastrados();
    }
}
