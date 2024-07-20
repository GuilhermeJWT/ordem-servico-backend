package br.com.systemsgs.ordem_servico_backend.dto;

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

    @Size(max = 250, message = "Nome do Fornecedor deve ter no máximo 250 Caracteres.")
    @NotBlank(message = "Nome do Fornecedor deve ser Informado.")
    private String nome;

    @Size(max = 250, message = "Nome Fantasia deve ter no máximo 250 Caracteres.")
    private String nomeFantasia;

    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa = TipoPessoa.PESSOA_JURIDICA;

    @Size(max = 18, message = "Cnpj do Fornecedor deve ter no máximo 18 Caracteres.")
    @CNPJ(message = "Informe um CNPJ Válido.")
    private String cnpj;

    private ModelEndereco endereco;

}
