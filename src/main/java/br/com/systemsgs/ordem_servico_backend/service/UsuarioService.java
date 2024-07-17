package br.com.systemsgs.ordem_servico_backend.service;

import br.com.systemsgs.ordem_servico_backend.dto.records.JwtTokenDTO;
import br.com.systemsgs.ordem_servico_backend.dto.records.LoginUsuarioDTO;
import br.com.systemsgs.ordem_servico_backend.dto.SalvarUsuarioDTO;

public interface UsuarioService {

    void salvarUsuario(SalvarUsuarioDTO salvarUsuarioDTO);

    JwtTokenDTO autenticarUsuario(LoginUsuarioDTO loginUsuarioDTO);

}
