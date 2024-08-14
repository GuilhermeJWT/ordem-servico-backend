package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.exception.errors.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.repository.OrdemServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ActiveProfiles(value = "test")
@SpringBootTest
class UtilOrdemServicoTest extends ConfigDadosEstaticosEntidades{

    private Optional<ModelOrdemServico> modelOrdemServicoOptional;

    @InjectMocks
    private UtilOrdemServico utilOrdemServico;

    @Mock
    private OrdemServicoRepository ordemServicoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        utilOrdemServico = new UtilOrdemServico(ordemServicoRepository);
        startOrdemServicoOptinal();
    }

    @DisplayName("Pesquisa uma OS por ID e retorna a Entidade para Validação")
    @Test
    void pesquisarOSPeloId() {
        when(ordemServicoRepository.findById(anyLong())).thenReturn(modelOrdemServicoOptional);

        ModelOrdemServico response = utilOrdemServico.pesquisaOsPorId(dadosOrdemServico().getId());

        assertNotNull(response);

        assertEquals(dadosOrdemServico().getId(), response.getId());
        assertEquals(dadosOrdemServico().getDefeito(), response.getDefeito());
        assertEquals(dadosOrdemServico().getDescricao(), response.getDescricao());
        assertEquals(dadosOrdemServico().getLaudo_tecnico(), response.getLaudo_tecnico());
        assertEquals(dadosOrdemServico().getStatus(), response.getStatus());
        assertEquals(dadosOrdemServico().getData_inicial(), response.getData_inicial());
        assertEquals(dadosOrdemServico().getData_final(), response.getData_final());

        assertEquals(dadosOrdemServico().getCliente().getId(), response.getCliente().getId());
        assertEquals(dadosOrdemServico().getCliente().getNome(), response.getCliente().getNome());
        assertEquals(dadosOrdemServico().getCliente().getCpf(), response.getCliente().getCpf());
        assertEquals(dadosOrdemServico().getCliente().getCelular(), response.getCliente().getCelular());
        assertEquals(dadosOrdemServico().getCliente().getEmail(), response.getCliente().getEmail());

        assertEquals(dadosOrdemServico().getCliente().getEndereco().getEndereco(), response.getCliente().getEndereco().getEndereco());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getComplemento(), response.getCliente().getEndereco().getComplemento());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getCidade(), response.getCliente().getEndereco().getCidade());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getEstado(), response.getCliente().getEndereco().getEstado());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getCep(), response.getCliente().getEndereco().getCep());
    }

    @DisplayName("Pesquisa uma OS por ID e retorna Not Found")
    @Test
    void pesquisaOSInexistenteRetornaNotFound(){
        when(ordemServicoRepository.findById(anyLong())).thenThrow(new RecursoNaoEncontradoException());

        try{
            utilOrdemServico.pesquisaOsPorId(dadosOrdemServico().getId());
        }catch (Exception exception){
            assertEquals(RecursoNaoEncontradoException.class, exception.getClass());
            assertEquals(mensagemErro().get(1), exception.getMessage());
        }
    }

    @DisplayName("Teste para retornar a quantidade de OS realizadas para o Dashboard")
    @Test
    void testQuantidadeOsRealizadas(){
        when(ordemServicoRepository.quantidadeOsRealizadas())
                .thenReturn(dadosDashboard().getQuantidadeOrdemServicoRealizadas());

        Optional<Integer> response = utilOrdemServico.quantidadeOsRealizadas();

        assertNotNull(response);
        assertTrue(response.isPresent());
        assertEquals(response, dadosDashboard().getQuantidadeOrdemServicoRealizadas());
    }

    @DisplayName("Teste para retornar NullPointerException caso não tenha dados")
    @Test
    void testQuantidadeOsRealizadasNullPointerException() {
        when(ordemServicoRepository.quantidadeOsRealizadas()).thenThrow(new NullPointerException());

        try {
            var response = utilOrdemServico.quantidadeOsRealizadas();
        }catch (NullPointerException exception){
            assertEquals(NullPointerException.class, exception.getClass());
        }
    }

    @DisplayName("Teste para retornar a quantidade de OS em Andamento para o Dashboard")
    @Test
    void testQuantidadeOsStatusEmAndamento(){
        when(ordemServicoRepository.quantidadeOsEmAndamento())
                .thenReturn(dadosDashboard().getQuantidadeOrdensServicoEmAndamento());

        Optional<Integer> response = utilOrdemServico.quantidadeOsStatusEmAndamento();

        assertNotNull(response);
        assertTrue(response.isPresent());
        assertEquals(response, dadosDashboard().getQuantidadeOrdensServicoEmAndamento());
    }

    @DisplayName("Teste para retornar NullPointerException caso não tenha dados")
    @Test
    void testQuantidadeOsStatusEmAndamentoNullPointerException() {
        when(ordemServicoRepository.quantidadeOsEmAndamento()).thenThrow(new NullPointerException());

        try {
            var response = utilOrdemServico.quantidadeOsStatusEmAndamento();
        }catch (NullPointerException exception){
            assertEquals(NullPointerException.class, exception.getClass());
        }
    }

    private void startOrdemServicoOptinal(){
        modelOrdemServicoOptional = Optional.of(new ModelOrdemServico(
                dadosOrdemServico().getId(),
                dadosOrdemServico().getDefeito(),
                dadosOrdemServico().getDescricao(),
                dadosOrdemServico().getLaudo_tecnico(),
                dadosOrdemServico().getStatus(),
                dadosOrdemServico().getData_inicial(),
                dadosOrdemServico().getData_final(),
                dadosOrdemServico().getCliente(),
                dadosOrdemServico().getTecnicoResponsavel()
        ));
    }
}