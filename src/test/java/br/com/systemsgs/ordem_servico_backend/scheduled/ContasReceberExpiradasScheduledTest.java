package br.com.systemsgs.ordem_servico_backend.scheduled;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.enums.StatusContas;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasReceber;
import br.com.systemsgs.ordem_servico_backend.notification.NotificaEmailService;
import br.com.systemsgs.ordem_servico_backend.repository.ContasReceberRepository;
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
class ContasReceberExpiradasScheduledTest extends ConfigDadosEstaticosEntidades {

    @InjectMocks
    private ContasReceberExpiradasScheduled contasReceberExpiradasScheduled;

    @Mock
    private ContasReceberRepository contasReceberRepository;

    @Mock
    private NotificaEmailService notificaEmailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contasReceberExpiradasScheduled = new ContasReceberExpiradasScheduled(
                contasReceberRepository,
                notificaEmailService);
    }

    @DisplayName("Teste para Contas a Receber Vencidas com Dados - Scheduled")
    @Test
    void testContasReceberVencidasQuandoExistemContasVencidas() {
        List<ModelContasReceber> contasVencidas = new ArrayList<>();
        contasVencidas.add(dadosContasReceber());

        when(contasReceberRepository.pesquisaContasReceberExpiradas()).thenReturn(contasVencidas);

        contasReceberExpiradasScheduled.verificaContasReceberVencidas();

        verify(contasReceberRepository).saveAll(contasVencidas);
        assert contasVencidas.stream().allMatch(conta -> conta.getStatusContasReceber() == StatusContas.VENCIDA);
    }

    @DisplayName("Teste para quando não existe Contas Vencidas")
    @Test
    void testContasReceberVencidasQuandoNaoExistemContasVencidas() {
        when(contasReceberRepository.pesquisaContasReceberExpiradas()).thenReturn(new ArrayList<>());

        contasReceberExpiradasScheduled.verificaContasReceberVencidas();

        verify(contasReceberRepository, never()).saveAll(anyList());
    }

    @DisplayName("Teste para verificar a chamada do Repository quando existe contas - 1 Acesso - Scheduled")
    @Test
    void testVerificaContasReceberVencidas() {
        contasReceberExpiradasScheduled.verificaContasReceberVencidas();

        verify(contasReceberRepository, times(1)).pesquisaContasReceberExpiradas();
    }
}