package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.repository.ContasReceberRepository;
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
class UtilContasReceberTest extends ConfigDadosEstaticosEntidades {

    @InjectMocks
    private UtilContasReceber utilContasReceber;

    @Mock
    private ContasReceberRepository contasReceberRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        utilContasReceber = new UtilContasReceber(contasReceberRepository);
    }

    @DisplayName("Teste para calcular o Total de Contas a Receber no Dashboard")
    @Test
    void testTotalContasReceber() {
        when(contasReceberRepository.totalContasReceber()).thenReturn(dadosDashboard().getTotalContasReceber());

        Optional<BigDecimal> response = utilContasReceber.totalContasReceber();

        assertNotNull(response);
        assertTrue(response.isPresent());
        assertEquals(response, dadosDashboard().getTotalContasReceber());
    }

    @DisplayName("Teste para calcular Quantidade de Contas a Receber Vencidas no Dashboard")
    @Test
    void testTotalContasReceberVencidas() {
        when(contasReceberRepository.quantidadeContasReceberVencidas()).thenReturn(dadosDashboard().getQuantidadeContasReceberVencidas());

        Optional<Integer> response = utilContasReceber.quantidadeContasReceberVencidas();

        assertNotNull(response);
        assertTrue(response.isPresent());
        assertEquals(response, dadosDashboard().getQuantidadeContasReceberVencidas());
    }

    @DisplayName("Teste para retornar NullPointerException caso não tenha dados")
    @Test
    void totalContasReceberVencidasNullPointerException() {
        when(contasReceberRepository.quantidadeContasReceberVencidas()).thenThrow(new NullPointerException());

        try {
            var response = utilContasReceber.quantidadeContasReceberVencidas();
        }catch (NullPointerException exception){
            assertEquals(NullPointerException.class, exception.getClass());
        }
    }

    @DisplayName("Teste para retornar NullPointerException caso não tenha dados")
    @Test
    void totalContasReceberNullPointerException() {
        when(contasReceberRepository.totalContasReceber()).thenThrow(new NullPointerException());

        try {
            var response = utilContasReceber.totalContasReceber();
        }catch (NullPointerException exception){
            assertEquals(NullPointerException.class, exception.getClass());
        }
    }

    @DisplayName("Teste para retornar a quantidade de Contas a Receber Inadimplentes")
    @Test
    void quantidadeContasInadimplentes() {
        when(contasReceberRepository.quantidadeContasInadimplentes()).
                thenReturn(dadosDashboard().getQuantidadeContasReceberInadimplentes());

        Optional<Integer> response = utilContasReceber.quantidadeContasInadimplentes();

        assertNotNull(response);
        assertTrue(response.isPresent());
        assertEquals(response, dadosDashboard().getQuantidadeContasReceberInadimplentes());
    }

    @DisplayName("Teste para retornar NullPointerException caso não tenha dados")
    @Test
    void testQuantidadeContasReceberNullPointerException() {
        when(contasReceberRepository.quantidadeContasInadimplentes()).thenThrow(new NullPointerException());

        try {
            var response = utilContasReceber.quantidadeContasInadimplentes();
        }catch (NullPointerException exception){
            assertEquals(NullPointerException.class, exception.getClass());
        }
    }

}