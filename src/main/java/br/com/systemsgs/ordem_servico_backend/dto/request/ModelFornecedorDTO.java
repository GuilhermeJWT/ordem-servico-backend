package br.com.systemsgs.ordem_servico_backend.dto.request;

import br.com.systemsgs.ordem_servico_backend.enums.TipoPessoa;
import br.com.systemsgs.ordem_servico_backend.model.ModelEndereco;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelFornecedorDTO {

    private Long id;

    @Size(max = 250, message = "{nome.maximo.caracteres}")
    @NotBlank(message = "{nome.obrigatorio}")
    private String nome;

    @Size(max = 250, message = "{nome.fantasia.maximo.caracteres}")
    private String nomeFantasia;

    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa = TipoPessoa.PESSOA_JURIDICA;

    @Size(max = 18, message = "{cnpj.maximo.caracteres}")
    @CNPJ(message = "{cnpj.invalido}")
    private String cnpj;

    private ModelEndereco endereco;

}
