package br.com.systemsgs.ordem_servico_backend.service;

import br.com.systemsgs.ordem_servico_backend.dto.ModelClientesDTO;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;

import java.util.List;

public interface ClienteService {

    ModelClientes pesquisaPorId(Long id);

    List<ModelClientes> listarClientes();

    ModelClientes salvarClientes(ModelClientesDTO modelClientesDTO);

    void deletarCliente(Long id);

    ModelClientes updateClientes(Long id, ModelClientesDTO modelClientesDTO);

}
