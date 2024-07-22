package br.com.systemsgs.ordem_servico_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelVendasDTO {

    @JsonProperty("desconto")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que 0.")
    @DecimalMax(value = "999999.0", message = "Valor muito alto para o Desconto.")
    private BigDecimal desconto = BigDecimal.ZERO;

    @JsonProperty("id_cliente")
    @NotNull(message = "Informe um Cliente.")
    private Long idCliente;

    @JsonProperty("id_tecnico_responsavel")
    @NotNull(message = "Informe um Técnico Responsavel pela Venda.")
    private Long idTecnicoResponsavel;

    @JsonProperty("itens")
    @NotNull(message = "Informe pelomenos 1 Produto para a Venda")
    private List<ModelItensVendasDTO> itens = new ArrayList<>();
}
