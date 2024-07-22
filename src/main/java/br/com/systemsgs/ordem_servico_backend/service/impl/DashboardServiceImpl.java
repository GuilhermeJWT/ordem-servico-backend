package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.response.DashboardResponse;
import br.com.systemsgs.ordem_servico_backend.service.DashboardService;
import br.com.systemsgs.ordem_servico_backend.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "dashboard")
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private UtilVendas utilVendas;

    @Autowired
    private UtilClientes utilClientes;

    @Autowired
    private UtilProdutos utilProdutos;

    @Autowired
    private UtilOrdemServico utilOrdemServico;

    @Autowired
    private UtilContasPagar utilContasPagar;

    @Autowired
    private UtilContasReceber utilContasReceber;

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
        var totalContasReceber = utilContasReceber.totalContasReceber();
        var quantidadeContasReceberInadimplentes = utilContasReceber.quantidadeContasInadimplentes();

        dashboardResponse.setTotal_vendas(totalVendas);
        dashboardResponse.setQuantidadeItensVendidosTodoPeriodo(totalItensVendidos);
        dashboardResponse.setQuantidadeClientesCadastrados(quantidadeClientes);
        dashboardResponse.setQuantidadeProdutosEstoqueAtual(quantidadeEstoque);
        dashboardResponse.setQuantidadeOrdemServicoRealizadas(quantidadeOrdemServico);
        dashboardResponse.setQuantidadeOrdensServicoEmAndamento(quantidadeOsEmAndamento);
        dashboardResponse.setTotalContasPagar(totalContasPagar);
        dashboardResponse.setTotalContasReceber(totalContasReceber);
        dashboardResponse.setQuantidadeContasReceberInadimplentes(quantidadeContasReceberInadimplentes);

        return dashboardResponse;
    }
}
