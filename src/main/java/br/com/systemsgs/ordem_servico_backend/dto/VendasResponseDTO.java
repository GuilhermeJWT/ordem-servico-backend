package br.com.systemsgs.ordem_servico_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VendasResponseDTO {

    @JsonProperty("id_venda")
    private Long idVenda;

    @JsonProperty("valor_total_venda")
    private BigDecimal totalVenda;

    @JsonProperty("total_itens_do_pedido")
    private Integer totalItens;

    @JsonProperty("data_hora_venda")
    private LocalDateTime dataVenda;

    @JsonProperty("desconto")
    private BigDecimal desconto;

    @JsonProperty("nome_do_cliente")
    private String nomeCliente;

    @JsonProperty("nome_tecnico_responsavel")
    private String nomeTecnicoResponsavel;
}
