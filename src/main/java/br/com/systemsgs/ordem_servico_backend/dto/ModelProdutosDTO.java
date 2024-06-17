package br.com.systemsgs.ordem_servico_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotBlank(message = "Descrição deve ser Informada.")
    private String descricao;

    @Positive(message = "A Quantidade deve ser um Valor Positivo. ")
    @Min(value = 1,message = "Quantidade Minima deve ser 1.")
    @NotNull(message = "Quantidade deve ser Informada.")
    private Integer quantidade;

    @Positive(message = "A Quantidade Minima deve ser um Valor Positivo.")
    @Min(value = 1,message = "Quantidade Minima deve ser 1.")
    private Integer quantidade_minima;

    @Positive(message = "O Preço de Compra deve ser um Valor Positivo.")
    @Min(value = 1, message = "Preço de Compra deve ser maior que R$ 1.00.")
    private BigDecimal preco_compra;

    @Positive(message = "O Preço de Venda deve ser um Valor Positivo.")
    @Min(value = 1, message = "Preço de Venda deve ser maior que R$ 1.00.")
    private BigDecimal preco_venda;

    @Min(value = 12, message = "Código de Barras deve ter no minimo 12 Números.")
    private String codigo_barras;

}
