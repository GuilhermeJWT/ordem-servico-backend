package br.com.systemsgs.ordem_servico_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelClientesDTO{

    private Long id;

    @Size(max = 250, message = "Nome do Cliente deve ter no máximo 250 Caracteres.")
    @NotBlank(message = "Nome deve ser Informado.")
    private String nome;

    @Size(max = 15, message = "Cpf deve ter no máximo 15 Caracteres.")
    @CPF(message = "Informe um CPF Válido.")
    @NotBlank(message = "CPF deve ser Informado.")
    private String cpf;

    @Size(max = 16, message = "Número de celular deve ter no máximo 16 Caracteres.")
    private String celular;

    @Size(max = 60, message = "Email do Cliente deve ter no máximo 60 Caracteres.")
    @Email(message = "Informe um E-mail Válido.")
    private String email;

    @Size(max = 150, message = "Endereço do Cliente deve ter no máximo 150 Caracteres.")
    private String endereco;

    @Size(max = 150, message = "Complemento do Endereço deve ter no máximo 150 Caracteres.")
    private String complemento;

    @Size(max = 40, message = "Cidade deve ter no máximo 40 Caracteres.")
    private String cidade;

    @Size(max = 25, message = "Estado deve ter no máximo 25 Caracteres.")
    private String estado;

    @Size(max = 10, message = "Cep deve ter no máximo 10 Caracteres.")
    private String cep;
}
