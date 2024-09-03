package br.com.systemsgs.ordem_servico_backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContasReceberResponse {

    @JsonProperty("codigo_conta_receber")
    private Long id;

    @JsonProperty("data_vencimento")
    private Date dataVencimento;

    @JsonProperty("valor_conta_receber")
    private BigDecimal valor;

    @JsonProperty("observacao")
    private String observacao;

    @JsonProperty("forma_pagamento")
    private String formaPagamento;

    @JsonProperty("status_conta_receber")
    private String statusContasReceber;

    @JsonProperty("nome_cliente")
    private String nomeCliente;

}
