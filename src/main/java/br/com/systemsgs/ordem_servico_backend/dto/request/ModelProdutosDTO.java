package br.com.systemsgs.ordem_servico_backend.dto.request;

import jakarta.validation.constraints.*;
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
public class ModelProdutosDTO extends RepresentationModel<ModelProdutosDTO> {

    private Long id;

    @Size(max = 250, message = "{descricao.maximo.caracteres}")
    @NotBlank(message = "{descricao.obrigatoria}")
    private String descricao;

    @Positive(message = "{quantidade.valor.positivo}")
    @Min(value = 1,message = "{quantidade.minima}")
    @NotNull(message = "{quantidade.produtos.obrigatorio}")
    private Integer quantidade;

    @Positive(message = "{quantidade.minima.positiva}")
    @Min(value = 1,message = "{quantidade.minima}")
    private Integer quantidade_minima;

    @Positive(message = "{preco.compra.valor.positivo}")
    @Min(value = 1, message = "{preco.compra.minimo}")
    private BigDecimal preco_compra;

    @Positive(message = "{preco.venda.positivo}")
    @Min(value = 1, message = "{preco.venda.minimo}")
    private BigDecimal preco_venda;

    @Min(value = 8, message = "{codigo.barras.minimo}")
    private String codigo_barras;

}
