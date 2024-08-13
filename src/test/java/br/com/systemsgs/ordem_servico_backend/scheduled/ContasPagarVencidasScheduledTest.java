package br.com.systemsgs.ordem_servico_backend.scheduled;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.enums.StatusContas;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasPagar;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles(value = "test")
@SpringBootTest
class ContasPagarVencidasScheduledTest extends ConfigDadosEstaticosEntidades {

    @InjectMocks
    private ContasPagarVencidasScheduled contasPagarVencidasScheduled;

    @Mock
    private ContasPagarRepository contasPagarRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Teste Contas Pagar Vencidas - Scheduled")
    @Test
    void testContasPagarVencidasQuandoExistemContasVencidas() {
        List<ModelContasPagar> contasVencidas = new ArrayList<>();
        contasVencidas.add(dadosContasPagar());

        when(contasPagarRepository.pesquisaContasPagarExpiradas()).thenReturn(contasVencidas);

        contasPagarVencidasScheduled.contasPagarVencidas();

        verify(contasPagarRepository).saveAll(anyList());
        assert contasVencidas.stream().allMatch(conta -> conta.getStatusContas() == StatusContas.VENCIDA);
    }
}