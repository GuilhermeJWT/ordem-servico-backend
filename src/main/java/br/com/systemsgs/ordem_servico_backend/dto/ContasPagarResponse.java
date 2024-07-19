package br.com.systemsgs.ordem_servico_backend.dto;

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
public class ContasPagarResponse {

    @JsonProperty("codigo_conta_pagar")
    private Long codigoContaPagar;

    @JsonProperty("data_vencimento")
    private Date data_vencimento;

    @JsonProperty("valor_conta_pagar")
    private BigDecimal valor_conta_pagar;

    @JsonProperty("observacao")
    private String observacao;

    @JsonProperty("forma_pagamento")
    private String formaPagamento;

    @JsonProperty("status_conta_pagar")
    private String statusContaPagar;

    @JsonProperty("nome_fornecedor")
    private String nomeFornecedor;

}
