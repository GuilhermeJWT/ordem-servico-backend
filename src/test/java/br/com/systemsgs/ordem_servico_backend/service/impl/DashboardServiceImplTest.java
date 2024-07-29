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
    }

    @DisplayName("Teste para Mockar os dados do Dashboard")
    @Test
    void retornaDadosDashboard() {
        when(utilVendas.calculaTotalVendas()).thenReturn(dashboardResponse.getTotal_vendas());
        when(utilVendas.somaTotalItensVendidosTodoPeriodo()).thenReturn(dashboardResponse.getQuantidadeItensVendidosTodoPeriodo());
        when(utilClientes.somaQuantidadeClientesCadastrados()).thenReturn(dashboardResponse.getQuantidadeClientesCadastrados());
        when(utilProdutos.somaEstoqueAtualProdutos()).thenReturn(dashboardResponse.getQuantidadeProdutosEstoqueAtual());
        when(utilOrdemServico.quantidadeOsRealizadas()).thenReturn(dashboardResponse.getQuantidadeOrdemServicoRealizadas());
        when(utilOrdemServico.quantidadeOsStatusEmAndamento()).thenReturn(dashboardResponse.getQuantidadeOrdensServicoEmAndamento());
        when(utilContasPagar.totalContasPagar()).thenReturn(dashboardResponse.getTotalContasPagar());
        when(utilContasReceber.totalContasReceber()).thenReturn(dashboardResponse.getTotalContasReceber());
        when(utilContasReceber.quantidadeContasInadimplentes()).thenReturn(dashboardResponse.getQuantidadeContasReceberInadimplentes());
        when(utilContasReceber.quantidadeContasReceberVencidas()).thenReturn(dashboardResponse.getQuantidadeContasReceberVencidas());

        DashboardResponse response = dashboardServiceImpl.retornaDadosDashboard();

        assertEquals(dadosDashboard().getTotal_vendas(), response.getTotal_vendas());
        assertEquals(dadosDashboard().getQuantidadeItensVendidosTodoPeriodo(), response.getQuantidadeItensVendidosTodoPeriodo());
        assertEquals(dadosDashboard().getQuantidadeClientesCadastrados(), response.getQuantidadeClientesCadastrados());
        assertEquals(dadosDashboard().getQuantidadeProdutosEstoqueAtual(), response.getQuantidadeProdutosEstoqueAtual());
        assertEquals(dadosDashboard().getQuantidadeOrdemServicoRealizadas(), response.getQuantidadeOrdemServicoRealizadas());
        assertEquals(dadosDashboard().getQuantidadeOrdensServicoEmAndamento(), response.getQuantidadeOrdensServicoEmAndamento());
        assertEquals(dadosDashboard().getTotalContasPagar(), response.getTotalContasPagar());
        assertEquals(dadosDashboard().getTotalContasReceber(), response.getTotalContasReceber());
        assertEquals(dadosDashboard().getQuantidadeContasReceberInadimplentes(), response.getQuantidadeContasReceberInadimplentes());
        assertEquals(dadosDashboard().getQuantidadeContasReceberVencidas(), response.getQuantidadeContasReceberVencidas());
    }

    private void startDashboard(){
        dashboardResponse = new DashboardResponse(
                dadosDashboard().getTotal_vendas(),
                dadosDashboard().getQuantidadeItensVendidosTodoPeriodo(),
                dadosDashboard().getQuantidadeClientesCadastrados(),
                dadosDashboard().getQuantidadeProdutosEstoqueAtual(),
                dadosDashboard().getQuantidadeOrdemServicoRealizadas(),
                dadosDashboard().getQuantidadeOrdensServicoEmAndamento(),
                dadosDashboard().getTotalContasPagar(),
                dadosDashboard().getTotalContasReceber(),
                dadosDashboard().getQuantidadeContasReceberInadimplentes(),
                dadosDashboard().getQuantidadeContasReceberVencidas()
        );
    }
}