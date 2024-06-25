package br.com.systemsgs.ordem_servico_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDTO {

    @Email(message = "Informe um E-mail Válido.")
    @NotBlank(message = "E-mail deve ser Informado.")
    private String email;

    @NotBlank(message = "Senha deve ser Informada.")
    private String password;
}
