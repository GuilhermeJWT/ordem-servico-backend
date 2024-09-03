package br.com.systemsgs.ordem_servico_backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelProdutosDTO {

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
    private Integer quantidadeMinima;

    @Positive(message = "{preco.compra.valor.positivo}")
    @Min(value = 1, message = "{preco.compra.minimo}")
    private BigDecimal precoCompra;

    @Positive(message = "{preco.venda.positivo}")
    @Min(value = 1, message = "{preco.venda.minimo}")
    private BigDecimal precoVenda;

    @Min(value = 8, message = "{codigo.barras.minimo}")
    private String codigoBarras;

}
