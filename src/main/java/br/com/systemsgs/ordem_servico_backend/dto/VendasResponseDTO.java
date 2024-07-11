package br.com.systemsgs.ordem_servico_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendasResponseDTO {

    @JsonProperty("id_venda")
    private Long idVenda;

    @JsonProperty("total_itens_do_pedido")
    private Integer totalItens;

    @JsonProperty("valor_total_venda")
    private BigDecimal totalVenda;

    @JsonProperty("data_hora_venda")
    private LocalDateTime dataVenda;

    @JsonProperty("desconto")
    private BigDecimal desconto;

    @JsonProperty("nome_do_cliente")
    private String nomeCliente;

    @JsonProperty("nome_tecnico_responsavel")
    private String nomeTecnicoResponsavel;

    @JsonProperty("produtos_do_pedido")
    private List<String> descricaoProdutos;
}
