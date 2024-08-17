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

    @Size(max = 250, message = "{nome.maximo.cinquenta.caracteres}")
    @NotBlank(message = "{nome.obrigatorio}")
    private String nome;

    @Size(max = 15, message = "{cpf.maximo.caracteres}")
    @CPF(message = "{cpf.invalido}")
    @NotBlank(message = "{cpf.obrigatorio}")
    private String cpf;

    @Size(max = 16, message = "{celular.maximo.caracteres}")
    private String celular;

    @Size(max = 60, message = "{email.maximo.caracteres}")
    @Email(message = "{email.invalido}")
    private String email;

    @Size(max = 150, message = "{endereco.maximo.caracteres}")
    private String endereco;

    @Size(max = 150, message = "{complemento.maximo.caracteres}")
    private String complemento;

    @Size(max = 40, message = "{cidade.maximo.caracteres}")
    private String cidade;

    @Size(max = 25, message = "{estado.maximo.caracteres}")
    private String estado;

    @Size(max = 10, message = "{cep.maximo.caracteres}")
    private String cep;
}
