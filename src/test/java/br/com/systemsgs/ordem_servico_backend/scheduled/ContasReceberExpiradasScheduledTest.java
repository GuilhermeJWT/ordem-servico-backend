package br.com.systemsgs.ordem_servico_backend.scheduled;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.enums.StatusContas;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasReceber;
import br.com.systemsgs.ordem_servico_backend.repository.ContasReceberRepository;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testContasReceberVencidasQuandoExistemContasVencidas() {
        List<ModelContasReceber> contasVencidas = new ArrayList<>();
        contasVencidas.add(dadosContasReceber());

        when(contasReceberRepository.pesquisaContasReceberExpiradas()).thenReturn(contasVencidas);

        contasReceberExpiradasScheduled.contasReceberVencidas();

        verify(contasReceberRepository).saveAll(anyList());
        assert contasVencidas.stream().allMatch(conta -> conta.getStatusContasReceber() == StatusContas.VENCIDA);
    }

    @Test
    void testContasReceberVencidasQuandoNaoExistemContasVencidas() {
        when(contasReceberRepository.pesquisaContasReceberExpiradas()).thenReturn(new ArrayList<>());

        contasReceberExpiradasScheduled.contasReceberVencidas();

        verify(contasReceberRepository, never()).saveAll(anyList());
    }

    @Test
    void testVerificaContasReceberVencidas() {
        contasReceberExpiradasScheduled.verificaContasReceberVencidas();

        verify(contasReceberRepository, times(1)).pesquisaContasReceberExpiradas();
    }
}