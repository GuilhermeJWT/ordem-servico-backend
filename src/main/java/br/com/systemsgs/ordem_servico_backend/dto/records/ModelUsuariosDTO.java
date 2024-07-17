package br.com.systemsgs.ordem_servico_backend.dto.records;

import br.com.systemsgs.ordem_servico_backend.enums.Role;

import java.util.List;

public record ModelUsuariosDTO(Long id, String email, List<Role> roles) {}
