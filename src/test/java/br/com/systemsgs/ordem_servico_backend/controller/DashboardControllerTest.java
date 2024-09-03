package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.response.DashboardResponse;
import br.com.systemsgs.ordem_servico_backend.service.DashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class DashboardControllerTest extends ConfigDadosEstaticosEntidades {

    private DashboardResponse dashboardResponse;

    @InjectMocks
    private DashboardController dashboardController;

    @Mock
    private DashboardService dashboardService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        dashboardController = new DashboardController(dashboardService);
        startDashboard();
    }

    @DisplayName("Teste para retornar os dados do Dashboard - retorna 200")
    @Test
    void retornaDadosDashboard() {
        when(dashboardService.retornaDadosDashboard()).thenReturn(dashboardResponse);

        ResponseEntity<DashboardResponse> response = dashboardController.retornaDadosDashboard();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(DashboardResponse.class, response.getBody().getClass());

        assertEquals(dadosDashboard().getTotalVendas(), response.getBody().getTotalVendas());
        assertEquals(dadosDashboard().getQuantidadeItensVendidosTodoPeriodo(), response.getBody().getQuantidadeItensVendidosTodoPeriodo());
        assertEquals(dadosDashboard().getQuantidadeClientesCadastrados(), response.getBody().getQuantidadeClientesCadastrados());
        assertEquals(dadosDashboard().getQuantidadeProdutosEstoqueAtual(), response.getBody().getQuantidadeProdutosEstoqueAtual());
        assertEquals(dadosDashboard().getQuantidadeOrdemServicoRealizadas(), response.getBody().getQuantidadeOrdemServicoRealizadas());
        assertEquals(dadosDashboard().getQuantidadeOrdensServicoEmAndamento(), response.getBody().getQuantidadeOrdensServicoEmAndamento());
        assertEquals(dadosDashboard().getTotalContasPagar(), response.getBody().getTotalContasPagar());
        assertEquals(dadosDashboard().getQuantidadeContasPagarVencidas(), response.getBody().getQuantidadeContasPagarVencidas());
        assertEquals(dadosDashboard().getTotalContasReceber(), response.getBody().getTotalContasReceber());
        assertEquals(dadosDashboard().getQuantidadeContasReceberInadimplentes(), response.getBody().getQuantidadeContasReceberInadimplentes());
        assertEquals(dadosDashboard().getQuantidadeContasReceberVencidas(), response.getBody().getQuantidadeContasReceberVencidas());
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
    }
}