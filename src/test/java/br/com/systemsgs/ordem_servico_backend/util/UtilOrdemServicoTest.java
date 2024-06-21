package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.enums.Status;
import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.repository.OrdemServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class UtilOrdemServicoTest {

    public static final Long ID = 1L;
    public static final String DEFEITO = "Trocar a tela";
    public static final String DESCRICAO = "Descricao OS";
    public static final String LAUDO_TECNICO = "Precisa trocar a tela";
    public static final Status STATUS = Status.ORCAMENTO;
    public static final Date DATA_INICIAL = new Date();
    public static final Date DATA_FINAL = new Date();
    public static final ModelClientes CLIENTE = new ModelClientes();
    public static final String RECURSO_NAO_ENCONTRADO = "Recurso não Encontrado!";

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

        ModelOrdemServico response = utilOrdemServico.pesquisaOsPorId(ID);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(DEFEITO, response.getDefeito());
        assertEquals(DESCRICAO, response.getDescricao());
        assertEquals(LAUDO_TECNICO, response.getLaudo_tecnico());
        assertEquals(STATUS, response.getStatus());
        assertEquals(DATA_INICIAL, response.getData_inicial());
        assertEquals(DATA_FINAL, response.getData_final());
        assertEquals(CLIENTE, response.getCliente());
    }

    @DisplayName("Pesquisa uma OS por ID e retorna Not Found")
    @Test
    void pesquisaOSInexistenteRetornaNotFound(){
        when(ordemServicoRepository.findById(anyLong())).thenThrow(new RecursoNaoEncontradoException());

        try{
            utilOrdemServico.pesquisaOsPorId(ID);
        }catch (Exception exception){
            assertEquals(RecursoNaoEncontradoException.class, exception.getClass());
            assertEquals(RECURSO_NAO_ENCONTRADO, exception.getMessage());
        }
    }

    private void startOrdemServicoOptinal(){
        modelOrdemServicoOptional = Optional.of(new ModelOrdemServico(ID, DEFEITO, DESCRICAO, LAUDO_TECNICO, STATUS, DATA_INICIAL, DATA_FINAL, CLIENTE));
    }
}