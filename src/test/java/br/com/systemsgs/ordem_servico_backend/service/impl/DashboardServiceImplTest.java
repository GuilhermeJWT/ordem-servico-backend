package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.response.DashboardResponse;
import br.com.systemsgs.ordem_servico_backend.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class DashboardServiceImplTest extends ConfigDadosEstaticosEntidades {

    private DashboardResponse dashboardResponse;
    private DashboardResponse dashboardResponseZerado;

    @InjectMocks
    private DashboardServiceImpl dashboardServiceImpl;

    @Mock
    private UtilVendas utilVendas;

    @Mock
    private UtilClientes utilClientes;

    @Mock
    private UtilProdutos utilProdutos;

    @Mock
    private UtilOrdemServico utilOrdemServico;

    @Mock
    private UtilContasPagar utilContasPagar;

    @Mock
    private UtilContasReceber utilContasReceber;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startDashboard();
        dashboardServiceImpl = new DashboardServiceImpl(
                utilVendas,
                utilClientes,
                utilProdutos,
                utilOrdemServico,
                utilContasPagar,
                utilContasReceber);
    }

    @DisplayName("Teste para Mockar os dados do Dashboard")
    @Test
    void retornaDadosDashboard() {
        when(utilVendas.calculaTotalVendas()).thenReturn(dashboardResponse.getTotalVendas());
        when(utilVendas.somaTotalItensVendidosTodoPeriodo()).thenReturn(dashboardResponse.getQuantidadeItensVendidosTodoPeriodo());
        when(utilClientes.somaQuantidadeClientesCadastrados()).thenReturn(dashboardResponse.getQuantidadeClientesCadastrados());
        when(utilProdutos.somaEstoqueAtualProdutos()).thenReturn(dashboardResponse.getQuantidadeProdutosEstoqueAtual());
        when(utilOrdemServico.quantidadeOsRealizadas()).thenReturn(dashboardResponse.getQuantidadeOrdemServicoRealizadas());
        when(utilOrdemServico.quantidadeOsStatusEmAndamento()).thenReturn(dashboardResponse.getQuantidadeOrdensServicoEmAndamento());
        when(utilContasPagar.totalContasPagar()).thenReturn(dashboardResponse.getTotalContasPagar());
        when(utilContasPagar.quantidadeContasPagarVencidas()).thenReturn(dashboardResponse.getQuantidadeContasPagarVencidas());
        when(utilContasReceber.totalContasReceber()).thenReturn(dashboardResponse.getTotalContasReceber());
        when(utilContasReceber.quantidadeContasInadimplentes()).thenReturn(dashboardResponse.getQuantidadeContasReceberInadimplentes());
        when(utilContasReceber.quantidadeContasReceberVencidas()).thenReturn(dashboardResponse.getQuantidadeContasReceberVencidas());

        DashboardResponse response = dashboardServiceImpl.retornaDadosDashboard();

        assertEquals(dadosDashboard().getTotalVendas(), response.getTotalVendas());
        assertEquals(dadosDashboard().getQuantidadeItensVendidosTodoPeriodo(), response.getQuantidadeItensVendidosTodoPeriodo());
        assertEquals(dadosDashboard().getQuantidadeClientesCadastrados(), response.getQuantidadeClientesCadastrados());
        assertEquals(dadosDashboard().getQuantidadeProdutosEstoqueAtual(), response.getQuantidadeProdutosEstoqueAtual());
        assertEquals(dadosDashboard().getQuantidadeOrdemServicoRealizadas(), response.getQuantidadeOrdemServicoRealizadas());
        assertEquals(dadosDashboard().getQuantidadeOrdensServicoEmAndamento(), response.getQuantidadeOrdensServicoEmAndamento());
        assertEquals(dadosDashboard().getTotalContasPagar(), response.getTotalContasPagar());
        assertEquals(dadosDashboard().getQuantidadeContasPagarVencidas(), response.getQuantidadeContasPagarVencidas());
        assertEquals(dadosDashboard().getTotalContasReceber(), response.getTotalContasReceber());
        assertEquals(dadosDashboard().getQuantidadeContasReceberInadimplentes(), response.getQuantidadeContasReceberInadimplentes());
        assertEquals(dadosDashboard().getQuantidadeContasReceberVencidas(), response.getQuantidadeContasReceberVencidas());
    }

    @DisplayName("Teste para Mockar os dados do Dashboard com valores Null - Zerados")
    @Test
    void retornaDadosDashboardZerados() {
        when(utilVendas.calculaTotalVendas()).thenReturn(dashboardResponseZerado.getTotalVendas());
        when(utilVendas.somaTotalItensVendidosTodoPeriodo()).thenReturn(dashboardResponseZerado.getQuantidadeItensVendidosTodoPeriodo());
        when(utilClientes.somaQuantidadeClientesCadastrados()).thenReturn(dashboardResponseZerado.getQuantidadeClientesCadastrados());
        when(utilProdutos.somaEstoqueAtualProdutos()).thenReturn(dashboardResponseZerado.getQuantidadeProdutosEstoqueAtual());
        when(utilOrdemServico.quantidadeOsRealizadas()).thenReturn(dashboardResponseZerado.getQuantidadeOrdemServicoRealizadas());
        when(utilOrdemServico.quantidadeOsStatusEmAndamento()).thenReturn(dashboardResponseZerado.getQuantidadeOrdensServicoEmAndamento());
        when(utilContasPagar.totalContasPagar()).thenReturn(dashboardResponseZerado.getTotalContasPagar());
        when(utilContasPagar.quantidadeContasPagarVencidas()).thenReturn(dashboardResponseZerado.getQuantidadeContasPagarVencidas());
        when(utilContasReceber.totalContasReceber()).thenReturn(dashboardResponseZerado.getTotalContasReceber());
        when(utilContasReceber.quantidadeContasInadimplentes()).thenReturn(dashboardResponseZerado.getQuantidadeContasReceberInadimplentes());
        when(utilContasReceber.quantidadeContasReceberVencidas()).thenReturn(dashboardResponseZerado.getQuantidadeContasReceberVencidas());

        DashboardResponse response = dashboardServiceImpl.retornaDadosDashboard();

        assertEquals(dadosDashboardZerados().getTotalVendas(), response.getTotalVendas());
        assertEquals(dadosDashboardZerados().getQuantidadeItensVendidosTodoPeriodo(), response.getQuantidadeItensVendidosTodoPeriodo());
        assertEquals(dadosDashboardZerados().getQuantidadeClientesCadastrados(), response.getQuantidadeClientesCadastrados());
        assertEquals(dadosDashboardZerados().getQuantidadeProdutosEstoqueAtual(), response.getQuantidadeProdutosEstoqueAtual());
        assertEquals(dadosDashboardZerados().getQuantidadeOrdemServicoRealizadas(), response.getQuantidadeOrdemServicoRealizadas());
        assertEquals(dadosDashboardZerados().getQuantidadeOrdensServicoEmAndamento(), response.getQuantidadeOrdensServicoEmAndamento());
        assertEquals(dadosDashboardZerados().getTotalContasPagar(), response.getTotalContasPagar());
        assertEquals(dadosDashboardZerados().getQuantidadeContasPagarVencidas(), response.getQuantidadeContasPagarVencidas());
        assertEquals(dadosDashboardZerados().getTotalContasReceber(), response.getTotalContasReceber());
        assertEquals(dadosDashboardZerados().getQuantidadeContasReceberInadimplentes(), response.getQuantidadeContasReceberInadimplentes());
        assertEquals(dadosDashboardZerados().getQuantidadeContasReceberVencidas(), response.getQuantidadeContasReceberVencidas());
    }

    private void startDashboard(){
        dashboardResponse = new DashboardResponse(
                dadosDashboard().getTotalVendas(),
                dadosDashboard().getQuantidadeItensVendidosTodoPeriodo(),
                dadosDashboard().getQuantidadeClientesCadastrados(),
                dadosDashboard().getQuantidadeProdutosEstoqueAtual(),
                dadosDashboard().getQuantidadeOrdemServicoRealizadas(),
                dadosDashboard().getQuantidadeOrdensServicoEmAndamento(),
                dadosDashboard().getTotalContasPagar(),
                dadosDashboard().getQuantidadeContasPagarVencidas(),
                dadosDashboard().getTotalContasReceber(),
                dadosDashboard().getQuantidadeContasReceberInadimplentes(),
                dadosDashboard().getQuantidadeContasReceberVencidas()
        );
        dashboardResponseZerado = new DashboardResponse(
                dadosDashboardZerados().getTotalVendas(),
                dadosDashboardZerados().getQuantidadeItensVendidosTodoPeriodo(),
                dadosDashboardZerados().getQuantidadeClientesCadastrados(),
                dadosDashboardZerados().getQuantidadeProdutosEstoqueAtual(),
                dadosDashboardZerados().getQuantidadeOrdemServicoRealizadas(),
                dadosDashboardZerados().getQuantidadeOrdensServicoEmAndamento(),
                dadosDashboardZerados().getTotalContasPagar(),
                dadosDashboardZerados().getQuantidadeContasPagarVencidas(),
                dadosDashboardZerados().getTotalContasReceber(),
                dadosDashboardZerados().getQuantidadeContasReceberInadimplentes(),
                dadosDashboardZerados().getQuantidadeContasReceberVencidas()
        );
    }
}