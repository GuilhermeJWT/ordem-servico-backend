package br.com.systemsgs.ordem_servico_backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {

    @JsonProperty("total_vendas")
    private Optional<BigDecimal> total_vendas;

    @JsonProperty("quantidade_itens_vendidos_todo_periodo")
    private Optional<Integer> quantidadeItensVendidosTodoPeriodo;

    @JsonProperty("quantidade_clientes_cadastrados")
    private Optional<Integer> quantidadeClientesCadastrados;

    @JsonProperty("quantidade_produtos_estoque_atual")
    private Optional<Integer> quantidadeProdutosEstoqueAtual;

    @JsonProperty("quantidade_ordens_servico_realizadas")
    private Optional<Integer> quantidadeOrdemServicoRealizadas;

    @JsonProperty("quantidade_ordens_servico_em_andamento")
    private Optional<Integer> quantidadeOrdensServicoEmAndamento;

    @JsonProperty("total_contas_pagar")
    private Optional<BigDecimal> totalContasPagar;

    @JsonProperty("total_contas_receber")
    private Optional<BigDecimal> totalContasReceber;

    @JsonProperty("quantidade_contas_receber_inadimplentes")
    private Optional<Integer> quantidadeContasReceberInadimplentes;

}
