package br.com.systemsgs.ordem_servico_backend.dto.request;

import br.com.systemsgs.ordem_servico_backend.enums.Role;

public record ModelUsuariosDTO(String email, String senha, Role role) {}
