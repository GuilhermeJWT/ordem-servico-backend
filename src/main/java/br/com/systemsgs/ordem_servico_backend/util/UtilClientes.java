package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.exception.errors.ClienteNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UtilClientes {

    @Autowired
    private ClienteRepository clienteRepository;

    public ModelClientes pesquisarClientePeloId(Long id){
         ModelClientes pesquisaCliente = clienteRepository.findById(id).
                 orElseThrow(() -> new ClienteNaoEncontradoException());

        return pesquisaCliente;
    }

    public Optional<Integer> somaQuantidadeClientesCadastrados(){
        return clienteRepository.somaClientesCadastrados();
    }
}
