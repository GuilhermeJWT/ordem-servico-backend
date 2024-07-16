package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.ModelOrdemServicoDTO;
import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ActiveProfiles(value = "test")
@SpringBootTest
class OrdemServicoControllerTest extends ConfigDadosEstaticosEntidades{

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
        assertEquals(ModelOrdemServicoDTO.class, response.getBody().get(0).getClass());

        assertEquals(dadosOrdemServico().getId(), response.getBody().get(0).getId());
        assertEquals(dadosOrdemServico().getDefeito(), response.getBody().get(0).getDefeito());
        assertEquals(dadosOrdemServico().getDescricao(), response.getBody().get(0).getDescricao());
        assertEquals(dadosOrdemServico().getLaudo_tecnico(), response.getBody().get(0).getLaudo_tecnico());
        assertEquals(dadosOrdemServico().getStatus(), response.getBody().get(0).getStatus());
        assertEquals(dadosOrdemServico().getData_inicial(), response.getBody().get(0).getData_inicial());
        assertEquals(dadosOrdemServico().getData_final(), response.getBody().get(0).getData_final());

        assertEquals(dadosOrdemServico().getCliente().getId(), response.getBody().get(0).getCliente().getId());
        assertEquals(dadosOrdemServico().getCliente().getNome(), response.getBody().get(0).getCliente().getNome());
        assertEquals(dadosOrdemServico().getCliente().getCpf(), response.getBody().get(0).getCliente().getCpf());
        assertEquals(dadosOrdemServico().getCliente().getCelular(), response.getBody().get(0).getCliente().getCelular());
        assertEquals(dadosOrdemServico().getCliente().getEmail(), response.getBody().get(0).getCliente().getEmail());
        assertEquals(dadosOrdemServico().getCliente().getEndereco(), response.getBody().get(0).getCliente().getEndereco());
        assertEquals(dadosOrdemServico().getCliente().getCidade(), response.getBody().get(0).getCliente().getCidade());
        assertEquals(dadosOrdemServico().getCliente().getEstado(), response.getBody().get(0).getCliente().getEstado());
        assertEquals(dadosOrdemServico().getCliente().getCep(), response.getBody().get(0).getCliente().getCep());

        assertEquals(dadosOrdemServico().getTecnicoResponsavel().getId(), response.getBody().get(0).getTecnicoResponsavel().getId());
        assertEquals(dadosOrdemServico().getTecnicoResponsavel().getNome(), response.getBody().get(0).getTecnicoResponsavel().getNome());
    }

    @DisplayName("Pesquisa uma OS pelo ID e testa o Retorno do Body - retorna 200")
    @Test
    void pesquisarPorId() {
        when(ordemServicoService.listarOS()).thenReturn(List.of(modelOrdemServico));
        when(mapper.map(any(), any())).thenReturn(modelOrdemServicoDTO);

        ResponseEntity<ModelOrdemServicoDTO> response = ordemServicoController.
                pesquisarPorId(dadosOrdemServico().getId());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ModelOrdemServicoDTO.class, response.getBody().getClass());

        assertEquals(dadosOrdemServico().getId(), response.getBody().getId());
        assertEquals(dadosOrdemServico().getDefeito(), response.getBody().getDefeito());
        assertEquals(dadosOrdemServico().getDescricao(), response.getBody().getDescricao());
        assertEquals(dadosOrdemServico().getLaudo_tecnico(), response.getBody().getLaudo_tecnico());
        assertEquals(dadosOrdemServico().getStatus(), response.getBody().getStatus());
        assertEquals(dadosOrdemServico().getData_inicial(), response.getBody().getData_inicial());
        assertEquals(dadosOrdemServico().getData_final(), response.getBody().getData_final());

        assertEquals(dadosOrdemServico().getCliente().getId(), response.getBody().getCliente().getId());
        assertEquals(dadosOrdemServico().getCliente().getNome(), response.getBody().getCliente().getNome());
        assertEquals(dadosOrdemServico().getCliente().getCpf(), response.getBody().getCliente().getCpf());
        assertEquals(dadosOrdemServico().getCliente().getCelular(), response.getBody().getCliente().getCelular());
        assertEquals(dadosOrdemServico().getCliente().getEmail(), response.getBody().getCliente().getEmail());
        assertEquals(dadosOrdemServico().getCliente().getEndereco(), response.getBody().getCliente().getEndereco());
        assertEquals(dadosOrdemServico().getCliente().getCidade(), response.getBody().getCliente().getCidade());
        assertEquals(dadosOrdemServico().getCliente().getEstado(), response.getBody().getCliente().getEstado());
        assertEquals(dadosOrdemServico().getCliente().getCep(), response.getBody().getCliente().getCep());

        assertEquals(dadosOrdemServico().getTecnicoResponsavel().getId(), response.getBody().getTecnicoResponsavel().getId());
        assertEquals(dadosOrdemServico().getTecnicoResponsavel().getNome(), response.getBody().getTecnicoResponsavel().getNome());
    }

    @DisplayName("Pesquisa uma OS inexistente e retorna 404")
    @Test
    void pesquisaOsRetorna404(){
        when(ordemServicoRepository.findById(anyLong())).thenThrow(new RecursoNaoEncontradoException());

        try{
            ordemServicoService.pesquisaPorId(dadosOrdemServico().getId());
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
        when(ordemServicoService.atualizarOS(
                dadosOrdemServico().getId(),modelOrdemServicoDTO)).thenReturn(modelOrdemServico);

        when(mapper.map(any(), any())).thenReturn(modelOrdemServicoDTO);

        ResponseEntity<ModelOrdemServicoDTO> response = ordemServicoController.
                atualizarOS(dadosOrdemServico().getId(), modelOrdemServicoDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ModelOrdemServicoDTO.class, response.getBody().getClass());

        assertEquals(dadosOrdemServico().getId(), response.getBody().getId());
        assertEquals(dadosOrdemServico().getDefeito(), response.getBody().getDefeito());
        assertEquals(dadosOrdemServico().getDescricao(), response.getBody().getDescricao());
        assertEquals(dadosOrdemServico().getLaudo_tecnico(), response.getBody().getLaudo_tecnico());
        assertEquals(dadosOrdemServico().getStatus(), response.getBody().getStatus());
        assertEquals(dadosOrdemServico().getData_inicial(), response.getBody().getData_inicial());
        assertEquals(dadosOrdemServico().getData_final(), response.getBody().getData_final());

        assertEquals(dadosOrdemServico().getCliente().getId(), response.getBody().getCliente().getId());
        assertEquals(dadosOrdemServico().getCliente().getNome(), response.getBody().getCliente().getNome());
        assertEquals(dadosOrdemServico().getCliente().getCpf(), response.getBody().getCliente().getCpf());
        assertEquals(dadosOrdemServico().getCliente().getCelular(), response.getBody().getCliente().getCelular());
        assertEquals(dadosOrdemServico().getCliente().getEmail(), response.getBody().getCliente().getEmail());
        assertEquals(dadosOrdemServico().getCliente().getEndereco(), response.getBody().getCliente().getEndereco());
        assertEquals(dadosOrdemServico().getCliente().getCidade(), response.getBody().getCliente().getCidade());
        assertEquals(dadosOrdemServico().getCliente().getEstado(), response.getBody().getCliente().getEstado());
        assertEquals(dadosOrdemServico().getCliente().getCep(), response.getBody().getCliente().getCep());

        assertEquals(dadosOrdemServico().getTecnicoResponsavel().getId(), response.getBody().getTecnicoResponsavel().getId());
        assertEquals(dadosOrdemServico().getTecnicoResponsavel().getNome(), response.getBody().getTecnicoResponsavel().getNome());
    }

    @DisplayName("Deleta uma OS e retorna 204")
    @Test
    void deletaOsRetorna204(){
        doNothing().when(ordemServicoService).deletarOS(anyLong());

        ResponseEntity<ModelOrdemServicoDTO> response = ordemServicoController.deletarOS(dadosOrdemServico().getId());

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(ordemServicoService, times(1)).deletarOS(anyLong());
    }

    private void startOrdemServico(){
        modelOrdemServico = new ModelOrdemServico(
                dadosOrdemServico().getId(),
                dadosOrdemServico().getDefeito(),
                dadosOrdemServico().getDescricao(),
                dadosOrdemServico().getLaudo_tecnico(),
                dadosOrdemServico().getStatus(),
                dadosOrdemServico().getData_inicial(),
                dadosOrdemServico().getData_final(),
                dadosOrdemServico().getCliente(),
                dadosOrdemServico().getTecnicoResponsavel()
        );
        modelOrdemServicoDTO = new ModelOrdemServicoDTO(
                dadosOrdemServico().getId(),
                dadosOrdemServico().getDefeito(),
                dadosOrdemServico().getDescricao(),
                dadosOrdemServico().getLaudo_tecnico(),
                dadosOrdemServico().getStatus(),
                dadosOrdemServico().getData_inicial(),
                dadosOrdemServico().getData_final(),
                dadosOrdemServico().getCliente(),
                dadosOrdemServico().getTecnicoResponsavel()
        );
    }
}