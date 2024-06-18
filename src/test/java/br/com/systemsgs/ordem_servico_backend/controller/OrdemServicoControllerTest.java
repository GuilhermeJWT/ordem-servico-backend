package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.dto.ModelOrdemServicoDTO;
import br.com.systemsgs.ordem_servico_backend.enums.Status;
import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.repository.OrdemServicoRepository;
import br.com.systemsgs.ordem_servico_backend.service.OrdemServicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class OrdemServicoControllerTest {

    public static final Long ID = 1L;
    public static final String DEFEITO = "Trocar a tela";
    public static final String DESCRICAO = "Descricao OS";
    public static final String LAUDO_TECNICO = "Precisa trocar a tela";
    public static final Status STATUS = Status.ORCAMENTO;
    public static final Date DATA_INICIAL = new Date();
    public static final Date DATA_FINAL = new Date();
    public static final ModelClientes CLIENTE = new ModelClientes();
    public static final Integer INDEX_0 = 0;

    private ModelOrdemServico modelOrdemServico;
    private ModelOrdemServicoDTO modelOrdemServicoDTO;

    @InjectMocks
    private OrdemServicoController ordemServicoController;

    @Mock
    private OrdemServicoService ordemServicoService;

    @Mock
    private OrdemServicoRepository ordemServicoRepository;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startOrdemServico();
    }

    @DisplayName("Teste para retornar a lista de OS - retorna 200")
    @Test
    void retornaListaOS200() {
        when(ordemServicoService.listarOS()).thenReturn(List.of(modelOrdemServico));
        when(mapper.map(any(), any())).thenReturn(modelOrdemServicoDTO);

        ResponseEntity<List<ModelOrdemServicoDTO>> response = ordemServicoController.listaTodasOS();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(ModelOrdemServicoDTO.class, response.getBody().get(INDEX_0).getClass());

        assertEquals(ID, response.getBody().get(INDEX_0).getId());
        assertEquals(DEFEITO, response.getBody().get(INDEX_0).getDefeito());
        assertEquals(DESCRICAO, response.getBody().get(INDEX_0).getDescricao());
        assertEquals(LAUDO_TECNICO, response.getBody().get(INDEX_0).getLaudo_tecnico());
        assertEquals(STATUS, response.getBody().get(INDEX_0).getStatus());
        assertEquals(DATA_INICIAL, response.getBody().get(INDEX_0).getData_inicial());
        assertEquals(DATA_FINAL, response.getBody().get(INDEX_0).getData_final());
        assertEquals(CLIENTE, response.getBody().get(INDEX_0).getCliente());
    }

    @DisplayName("Pesquisa uma OS pelo ID e testa o Retorno do Body - retorna 200")
    @Test
    void pesquisarPorId() {
        when(ordemServicoService.listarOS()).thenReturn(List.of(modelOrdemServico));
        when(mapper.map(any(), any())).thenReturn(modelOrdemServicoDTO);

        ResponseEntity<ModelOrdemServicoDTO> response = ordemServicoController.pesquisarPorId(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ModelOrdemServicoDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(DEFEITO, response.getBody().getDefeito());
        assertEquals(DESCRICAO, response.getBody().getDescricao());
        assertEquals(LAUDO_TECNICO, response.getBody().getLaudo_tecnico());
        assertEquals(STATUS, response.getBody().getStatus());
        assertEquals(DATA_INICIAL, response.getBody().getData_inicial());
        assertEquals(DATA_FINAL, response.getBody().getData_final());
        assertEquals(CLIENTE, response.getBody().getCliente());
    }

    @DisplayName("Pesquisa uma OS inexistente e retorna 404")
    @Test
    void pesquisaOsRetorna404(){
        when(ordemServicoRepository.findById(anyLong())).thenThrow(new RecursoNaoEncontradoException());

        try{
            ordemServicoService.pesquisaPorId(ID);
        }catch (Exception exception){
            assertEquals(RecursoNaoEncontradoException.class, exception.getClass());
            assertEquals("Recurso não Encontrado!", exception.getMessage());
        }
    }

    @DisplayName("Salva uma OS e retorna 201")
    @Test
    void salvaOsRetorna201(){
        when(ordemServicoService.salvarOS(any())).thenReturn(modelOrdemServico);

        ResponseEntity<ModelOrdemServicoDTO> response = ordemServicoController.salvarOS(modelOrdemServicoDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @DisplayName("Atualiza uma OS e retorna 200")
    @Test
    void atualizaOsRetorna200(){
        when(ordemServicoService.atualizarOS(ID,modelOrdemServicoDTO)).thenReturn(modelOrdemServico);
        when(mapper.map(any(), any())).thenReturn(modelOrdemServicoDTO);

        ResponseEntity<ModelOrdemServicoDTO> response = ordemServicoController.atualizarOS(ID, modelOrdemServicoDTO);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ModelOrdemServicoDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(DEFEITO, response.getBody().getDefeito());
        assertEquals(DESCRICAO, response.getBody().getDescricao());
        assertEquals(LAUDO_TECNICO, response.getBody().getLaudo_tecnico());
        assertEquals(STATUS, response.getBody().getStatus());
        assertEquals(DATA_INICIAL, response.getBody().getData_inicial());
        assertEquals(DATA_FINAL, response.getBody().getData_final());
        assertEquals(CLIENTE, response.getBody().getCliente());
    }

    @DisplayName("Deleta uma OS e retorna 204")
    @Test
    void deletaOsRetorna204(){
        doNothing().when(ordemServicoService).deletarOS(anyLong());

        ResponseEntity<ModelOrdemServicoDTO> response = ordemServicoController.deletarOS(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(ordemServicoService, times(1)).deletarOS(anyLong());
    }

    private void startOrdemServico(){
        modelOrdemServico = new ModelOrdemServico(ID, DEFEITO, DESCRICAO, LAUDO_TECNICO, STATUS, DATA_INICIAL, DATA_FINAL, CLIENTE);
        modelOrdemServicoDTO = new ModelOrdemServicoDTO(ID, DEFEITO, DESCRICAO, LAUDO_TECNICO, STATUS, DATA_INICIAL, DATA_FINAL, CLIENTE);
    }

}