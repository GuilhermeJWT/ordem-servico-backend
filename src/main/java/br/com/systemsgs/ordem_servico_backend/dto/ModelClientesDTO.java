package br.com.systemsgs.ordem_servico_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelClientesDTO extends RepresentationModel<ModelClientesDTO> {

    private Long id;

    @NotBlank(message = "Nome deve ser Informado.")
    private String nome;

    @CPF(message = "Informe um CPF Válido.")
    @NotBlank(message = "CPF deve ser Informado.")
    private String cpf;

    private String celular;

    @Email(message = "Informe um E-mail Válido.")
    private String email;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;
}
