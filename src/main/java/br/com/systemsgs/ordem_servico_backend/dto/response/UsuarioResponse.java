package br.com.systemsgs.ordem_servico_backend.dto.response;

import br.com.systemsgs.ordem_servico_backend.enums.Role;

import java.util.List;

public record UsuarioResponse(Long id, String email, List<Role> roles) {}
