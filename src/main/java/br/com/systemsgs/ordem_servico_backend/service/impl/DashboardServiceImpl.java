package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.response.DashboardResponse;
import br.com.systemsgs.ordem_servico_backend.service.DashboardService;
import br.com.systemsgs.ordem_servico_backend.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@CacheConfig(cacheNames = "dashboard")
@Service
public class DashboardServiceImpl implements DashboardService {

    private final UtilVendas utilVendas;
    private final UtilClientes utilClientes;
    private final UtilProdutos utilProdutos;
    private final UtilOrdemServico utilOrdemServico;
    private final UtilContasPagar utilContasPagar;
    private final UtilContasReceber utilContasReceber;

    @Autowired
    public DashboardServiceImpl(UtilVendas utilVendas,
                                UtilClientes utilClientes,
                                UtilProdutos utilProdutos,
                                UtilOrdemServico utilOrdemServico,
                                UtilContasPagar utilContasPagar,
                                UtilContasReceber utilContasReceber) {
        this.utilVendas = utilVendas;
        this.utilClientes = utilClientes;
        this.utilProdutos = utilProdutos;
        this.utilOrdemServico = utilOrdemServico;
        this.utilContasPagar = utilContasPagar;
        this.utilContasReceber = utilContasReceber;
    }

    @Cacheable(value = "dashboard")
    @Override
    public DashboardResponse retornaDadosDashboard(){
        DashboardResponse dashboardResponse = new DashboardResponse();

        var totalVendas = utilVendas.calculaTotalVendas();
        var totalItensVendidos = utilVendas.somaTotalItensVendidosTodoPeriodo();
        var quantidadeClientes = utilClientes.somaQuantidadeClientesCadastrados();
        var quantidadeEstoque = utilProdutos.somaEstoqueAtualProdutos();
        var quantidadeOrdemServico = utilOrdemServico.quantidadeOsRealizadas();
        var quantidadeOsEmAndamento = utilOrdemServico.quantidadeOsStatusEmAndamento();
        var totalContasPagar = utilContasPagar.totalContasPagar();
        var quantidadeContasPagarVencidas = utilContasPagar.quantidadeContasPagarVencidas();
        var totalContasReceber = utilContasReceber.totalContasReceber();
        var quantidadeContasReceberInadimplentes = utilContasReceber.quantidadeContasInadimplentes();
        var quantidadeContasReceberVencidas = utilContasReceber.quantidadeContasReceberVencidas();

        dashboardResponse.setTotalVendas(Optional.of(totalVendas.orElse(new BigDecimal(0))));
        dashboardResponse.setQuantidadeItensVendidosTodoPeriodo(Optional.of(totalItensVendidos.orElse(0)));
        dashboardResponse.setQuantidadeClientesCadastrados(Optional.of(quantidadeClientes.orElse(0)));
        dashboardResponse.setQuantidadeProdutosEstoqueAtual(Optional.of(quantidadeEstoque.orElse(0)));
        dashboardResponse.setQuantidadeOrdemServicoRealizadas(Optional.of(quantidadeOrdemServico.orElse(0)));
        dashboardResponse.setQuantidadeOrdensServicoEmAndamento(Optional.of(quantidadeOsEmAndamento.orElse(0)));
        dashboardResponse.setTotalContasPagar(Optional.of(totalContasPagar.orElse(new BigDecimal(0))));
        dashboardResponse.setQuantidadeContasPagarVencidas(Optional.of(quantidadeContasPagarVencidas.orElse(0)));
        dashboardResponse.setTotalContasReceber(Optional.of(totalContasReceber.orElse(new BigDecimal(0))));
        dashboardResponse.setQuantidadeContasReceberInadimplentes(Optional.of(quantidadeContasReceberInadimplentes.orElse(0)));
        dashboardResponse.setQuantidadeContasReceberVencidas(Optional.of(quantidadeContasReceberVencidas.orElse(0)));

        return dashboardResponse;
    }
}
