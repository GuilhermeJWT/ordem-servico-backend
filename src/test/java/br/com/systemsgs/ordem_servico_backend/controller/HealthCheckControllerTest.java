package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(value = "test")
@SpringBootTest
class HealthCheckControllerTest extends ConfigDadosEstaticosEntidades {

    @InjectMocks
    private HealthCheckController healthCheckController;

    public HealthCheckControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Teste para verificar se a aplicação está funcionando")
    @Test
    public void testHealthCheck() {
        String response = healthCheckController.healthCheck();
        assertEquals("UP", response);
    }
}