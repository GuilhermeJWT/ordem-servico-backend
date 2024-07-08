package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.dto.ModelOrdemServicoDTO;
import br.com.systemsgs.ordem_servico_backend.exception.ClienteNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilClientes {

    @Autowired
    private ClienteRepository clienteRepository;

    public ModelClientes pesquisarClientePeloId(Long id){
         ModelClientes pesquisaCliente = clienteRepository.findById(id).
                 orElseThrow(() -> new ClienteNaoEncontradoException());

        return pesquisaCliente;
    }

    public ModelOrdemServicoDTO validaCliente(ModelOrdemServicoDTO modelOrdemServicoDTO){
        ModelClientes pesquisaCliente = pesquisarClientePeloId(modelOrdemServicoDTO.getCliente().getId());

        if(pesquisaCliente == null){
            throw new ClienteNaoEncontradoException();
        }
        modelOrdemServicoDTO.getCliente().setNome(pesquisaCliente.getNome());
        modelOrdemServicoDTO.getCliente().setCpf(pesquisaCliente.getCpf());
        modelOrdemServicoDTO.getCliente().setCelular(pesquisaCliente.getCelular());
        modelOrdemServicoDTO.getCliente().setEmail(pesquisaCliente.getEmail());
        modelOrdemServicoDTO.getCliente().setEndereco(pesquisaCliente.getEndereco());
        modelOrdemServicoDTO.getCliente().setCidade(pesquisaCliente.getCidade());
        modelOrdemServicoDTO.getCliente().setEstado(pesquisaCliente.getEstado());
        modelOrdemServicoDTO.getCliente().setCep(pesquisaCliente.getCep());

        return modelOrdemServicoDTO;
    }
}
