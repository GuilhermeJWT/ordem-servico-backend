package br.com.systemsgs.ordem_servico_backend.dto;

import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;
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

    @NotNull(message = "Informe uma Quantidade do Produto.")
    @JsonProperty("quantidade")
    private Integer quantidade;

    @JsonProperty("valor_produto")
    private BigDecimal valorProduto;

    @JsonProperty("id_produto")
    private Long id_produto;

}
