package br.com.systemsgs.ordem_servico_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelItensVendasDTO {

    @NotNull(message = "{quantidade.produtos.obrigatorio}")
    @JsonProperty("quantidade")
    private Integer quantidade;

    @NotNull(message = "{valor.produto.obrigatorio")
    @JsonProperty("valor_produto")
    private BigDecimal valorProduto;

    @NotNull(message = "{produto.obrigatorio")
    @JsonProperty("id_produto")
    private Long idProduto;

}
