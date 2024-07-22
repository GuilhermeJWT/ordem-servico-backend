package br.com.systemsgs.ordem_servico_backend.dto.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelProdutosHateoas extends RepresentationModel<ModelProdutosHateoas> {

    @JsonProperty("codigo_produto")
    private Long id;

    @JsonProperty("descricao_produto")
    private String descricao;

    @JsonProperty("quantidade")
    private Integer quantidade;

    @JsonProperty("quantidade_minima")
    private Integer quantidade_minima;

    @JsonProperty("preco_compra")
    private BigDecimal preco_compra;

    @JsonProperty("preco_vena")
    private BigDecimal preco_venda;

    @JsonProperty("codigo_barras")
    private String codigo_barras;

}
