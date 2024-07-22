package br.com.systemsgs.ordem_servico_backend.dto;

import br.com.systemsgs.ordem_servico_backend.enums.FormaPagamento;
import br.com.systemsgs.ordem_servico_backend.enums.StatusContas;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
public class ModelContasPagarDTO {

    private Long id;

    @FutureOrPresent(message = "Data de Vencimento deve ser no presente ou futuro.")
    @NotNull(message = "A Data de Vencimento deve ser Informada.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd/MM/yyyy")
    private Date data_vencimento;

    @NotNull(message = "Informe o Valor.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que 0.")
    @DecimalMax(value = "999999.0", message = "Valor muito alto para a conta.")
    private BigDecimal valor = BigDecimal.ZERO;

    @Size(max = 250, message = "Observação da conta deve ter no máximo 250 Caracteres")
    private String observacao;

    @JsonProperty("forma_pagamento")
    @NotNull(message = "Forma de Pagamento deve ser Informada.")
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @JsonProperty("status_contas")
    @NotNull(message = "Status da Conta deve ser Informada.")
    @Enumerated(EnumType.STRING)
    private StatusContas statusContas;

    @JsonProperty("codigo_fornecedor")
    @NotNull(message = "Fornecedor deve ser Informado.")
    private Long codigoFornecedor;
}
