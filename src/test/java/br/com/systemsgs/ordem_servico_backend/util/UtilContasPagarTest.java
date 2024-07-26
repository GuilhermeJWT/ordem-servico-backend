package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.repository.ContasPagarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class UtilContasPagarTest extends ConfigDadosEstaticosEntidades {

    @InjectMocks
    private UtilContasPagar utilContasPagar;

    @Mock
    private ContasPagarRepository contasPagarRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Teste para calcular o Total de Contas a Pagar")
    @Test
    void totalContasPagar() {
        when(contasPagarRepository.totalContasPagar()).thenReturn(dadosDashboard().getTotalContasPagar());

        Optional<BigDecimal> response = utilContasPagar.totalContasPagar();

        assertNotNull(response);
        assertTrue(response.isPresent());
        assertEquals(response, dadosDashboard().getTotalContasPagar());
    }

    @DisplayName("Teste para retornar NullPointerException caso não tenha dados")
    @Test
    void totalContasPagarNullPointerException() {
        when(contasPagarRepository.totalContasPagar()).thenThrow(new NullPointerException());

        try {
            var response = utilContasPagar.totalContasPagar();
        }catch (NullPointerException exception){
            assertEquals(NullPointerException.class, exception.getClass());
        }
    }
}