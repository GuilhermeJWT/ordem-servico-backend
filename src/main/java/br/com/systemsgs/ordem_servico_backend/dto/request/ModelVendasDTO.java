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
    @DecimalMin(value = "0.1", inclusive = false, message = "{desconto.minimo}")
    @DecimalMax(value = "999999.0", message = "{desconto.alto}")
    private BigDecimal desconto = BigDecimal.ZERO;

    @JsonProperty("id_cliente")
    @NotNull(message = "{cliente.obrigatorio}")
    private Long idCliente;

    @JsonProperty("id_tecnico_responsavel")
    @NotNull(message = "{tecnico.responsavel.obrigatorio}")
    private Long idTecnicoResponsavel;

    @JsonProperty("itens")
    @NotNull(message = "{produto.obrigatorio}")
    private List<ModelItensVendasDTO> itens = new ArrayList<>();
}
