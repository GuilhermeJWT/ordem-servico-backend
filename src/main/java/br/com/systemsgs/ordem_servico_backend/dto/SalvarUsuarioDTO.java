package br.com.systemsgs.ordem_servico_backend.dto;

import br.com.systemsgs.ordem_servico_backend.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalvarUsuarioDTO{

    @NotBlank(message = "E-mail deve ser Informado.")
    @Email(message = "Informe um E-mail Válido.")
    private String email;

    @NotBlank(message = "Senha deve ser Informada.")
    private String senha;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Informe as Permissões para o Usuário")
    private Role role;

}
