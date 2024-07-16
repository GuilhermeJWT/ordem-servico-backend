package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
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
        assertEquals(dadosOrdemServico().getCliente().getEndereco(), response.getCliente().getEndereco());
        assertEquals(dadosOrdemServico().getCliente().getCidade(), response.getCliente().getCidade());
        assertEquals(dadosOrdemServico().getCliente().getEstado(), response.getCliente().getEstado());
        assertEquals(dadosOrdemServico().getCliente().getCep(), response.getCliente().getCep());
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