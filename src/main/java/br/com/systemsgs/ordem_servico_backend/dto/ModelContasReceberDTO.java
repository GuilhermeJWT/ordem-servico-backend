package br.com.systemsgs.ordem_servico_backend.dto;

import br.com.systemsgs.ordem_servico_backend.enums.FormaPagamento;
import br.com.systemsgs.ordem_servico_backend.enums.StatusContasPagar;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelContasReceberDTO {

    private Long id;

    @FutureOrPresent(message = "Data de Vencimento deve ser no presente ou futuro.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd/MM/yyyy")
    private Date data_vencimento;

    @NotNull(message = "Informe o Valor.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que 0.")
    @DecimalMax(value = "999999.0", message = "Valor muito alto para a conta.")
    private BigDecimal valor = BigDecimal.ZERO;

    @Size(max = 250, message = "Observação da conta deve ter no máximo 250 Caracteres")
    private String observacao = "Sem Observação.";

    @NotNull(message = "Forma de Pagamento deve ser Informada.")
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @NotNull(message = "Status da Conta deve ser Informada.")
    @Enumerated(EnumType.STRING)
    private StatusContasPagar statusContasPagar;

    @JsonProperty("codigo_cliente")
    @NotNull(message = "Cliente deve ser Informado.")
    private Long codigoCliente;

}
