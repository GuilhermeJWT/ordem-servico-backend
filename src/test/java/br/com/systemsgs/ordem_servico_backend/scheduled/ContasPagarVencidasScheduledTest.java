package br.com.systemsgs.ordem_servico_backend.scheduled;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.enums.StatusContas;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasPagar;
import br.com.systemsgs.ordem_servico_backend.notification.NotificaEmailService;
import br.com.systemsgs.ordem_servico_backend.repository.ContasPagarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class ContasPagarVencidasScheduledTest extends ConfigDadosEstaticosEntidades {

    @InjectMocks
    private ContasPagarVencidasScheduled contasPagarVencidasScheduled;

    @Mock
    private ContasPagarRepository contasPagarRepository;

    @Mock
    private NotificaEmailService notificaEmailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contasPagarVencidasScheduled = new ContasPagarVencidasScheduled(contasPagarRepository, notificaEmailService);
    }

    @DisplayName("Teste Contas Pagar Vencidas - Scheduled")
    @Test
    void testContasPagarVencidasQuandoExistemContasVencidas() {
        List<ModelContasPagar> contasVencidas = new ArrayList<>();
        contasVencidas.add(dadosContasPagar());

        when(contasPagarRepository.pesquisaContasPagarExpiradas()).thenReturn(contasVencidas);

        contasPagarVencidasScheduled.identificaContasPagarComVencimento();

        verify(contasPagarRepository).saveAll(contasVencidas);
        assert contasVencidas.stream().allMatch(conta -> conta.getStatusContas() == StatusContas.VENCIDA);
    }

    @DisplayName("Teste Contas Pagar Vencidas 0 Dados, não chama o método Salvar - Scheduled")
    @Test
    void testContasPagarVencidasQuandoNaoExistemContasVencidas() {
        when(contasPagarRepository.pesquisaContasPagarExpiradas()).thenReturn(new ArrayList<>());

        contasPagarVencidasScheduled.identificaContasPagarComVencimento();

        verify(contasPagarRepository, never()).saveAll(anyList());
    }

    @DisplayName("Teste para identificar Contas a Pagar com Vencimento - Scheduled")
    @Test
    void testIdentificaContasPagarComVencimento() {
        contasPagarVencidasScheduled.identificaContasPagarComVencimento();

        verify(contasPagarRepository, times(1)).pesquisaContasPagarExpiradas();
    }
}