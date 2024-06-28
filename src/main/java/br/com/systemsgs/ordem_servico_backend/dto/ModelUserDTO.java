package br.com.systemsgs.ordem_servico_backend.dto;

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
public class ModelUserDTO {

    private Long id;

    @NotBlank(message = "Nome deve ser Informado.")
    private String nome;

    @Email(message = "Informe um E-mail Válido.")
    @NotNull(message = "E-mail deve ser Informado.")
    private String email;

    @NotBlank(message = "Senha deve ser Informada.")
    private String password;

    private String endereco;

    private String cidade;

    private String estado;

    private String cep;

}
