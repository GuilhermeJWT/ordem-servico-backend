package br.com.systemsgs.ordem_servico_backend.service;

import br.com.systemsgs.ordem_servico_backend.dto.ModelUserDTO;
import br.com.systemsgs.ordem_servico_backend.model.ModelUser;

import java.util.List;

public interface UsuarioService {

    ModelUser pesquisaPorId(Long id);

    List<ModelUser> listarUsuarios();

    ModelUser salvarUsuarios(ModelUserDTO modelUsuariosDTO);

    void deletarUsuario(Long id);

    ModelUser updateUsuarios(Long id, ModelUserDTO modelUsuariosDTO);

}
