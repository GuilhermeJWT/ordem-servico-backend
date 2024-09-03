package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.hateoas.ModelOrdemServicoHateoas;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelOrdemServicoDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.RecursoNaoEncontradoException;
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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        ordemServicoController = new OrdemServicoController(ordemServicoService, mapper);
        startOrdemServico();
    }

    @DisplayName("Teste para listar Ordens de Serviço com Link - Hateoas")
    @Test
     void testListarOsComLinkHateoas() {
        ModelOrdemServico ordemServico1 = dadosOrdemServico();
        ModelOrdemServico ordemServico2 = dadosOrdemServico();

        List<ModelOrdemServico> ordemServicoList = Arrays.asList(ordemServico1, ordemServico2);

        ModelOrdemServicoHateoas hateoas1 = new ModelOrdemServicoHateoas();
        hateoas1.setId(1L);

        ModelOrdemServicoHateoas hateoas2 = new ModelOrdemServicoHateoas();
        hateoas2.setId(2L);

        when(ordemServicoService.listarOS()).thenReturn(ordemServicoList);
        when(mapper.map(ordemServico1, ModelOrdemServicoHateoas.class)).thenReturn(hateoas1);
        when(mapper.map(ordemServico2, ModelOrdemServicoHateoas.class)).thenReturn(hateoas2);

        CollectionModel<ModelOrdemServicoHateoas> response = ordemServicoController.listarOsComLink();

        Link link1 = linkTo(methodOn(OrdemServicoController.class).pesquisarPorId(1L)).withRel("Pesquisa OS pelo ID");
        Link link2 = linkTo(methodOn(OrdemServicoController.class).pesquisarPorId(2L)).withRel("Pesquisa OS pelo ID");

        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getContent()).containsExactly(hateoas1, hateoas2);
        assertThat(hateoas1.getLinks()).contains(link1);
        assertThat(hateoas2.getLinks()).contains(link2);
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
        assertEquals(dadosOrdemServico().getLaudoTecnico(), response.getBody().get(0).getLaudoTecnico());
        assertEquals(dadosOrdemServico().getStatus(), response.getBody().get(0).getStatus());
        assertEquals(dadosOrdemServico().getDataInicial(), response.getBody().get(0).getDataInicial());
        assertEquals(dadosOrdemServico().getDataFinal(), response.getBody().get(0).getDataFinal());

        assertEquals(dadosOrdemServico().getCliente().getId(), response.getBody().get(0).getCliente().getId());
        assertEquals(dadosOrdemServico().getCliente().getNome(), response.getBody().get(0).getCliente().getNome());
        assertEquals(dadosOrdemServico().getCliente().getCpf(), response.getBody().get(0).getCliente().getCpf());
        assertEquals(dadosOrdemServico().getCliente().getCelular(), response.getBody().get(0).getCliente().getCelular());
        assertEquals(dadosOrdemServico().getCliente().getEmail(), response.getBody().get(0).getCliente().getEmail());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getEndereco(), response.getBody().get(0).getCliente().getEndereco().getEndereco());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getComplemento(), response.getBody().get(0).getCliente().getEndereco().getComplemento());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getCidade(), response.getBody().get(0).getCliente().getEndereco().getCidade());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getEstado(), response.getBody().get(0).getCliente().getEndereco().getEstado());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getCep(), response.getBody().get(0).getCliente().getEndereco().getCep());

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
        assertEquals(dadosOrdemServico().getLaudoTecnico(), response.getBody().getLaudoTecnico());
        assertEquals(dadosOrdemServico().getStatus(), response.getBody().getStatus());
        assertEquals(dadosOrdemServico().getDataInicial(), response.getBody().getDataInicial());
        assertEquals(dadosOrdemServico().getDataFinal(), response.getBody().getDataFinal());

        assertEquals(dadosOrdemServico().getCliente().getId(), response.getBody().getCliente().getId());
        assertEquals(dadosOrdemServico().getCliente().getNome(), response.getBody().getCliente().getNome());
        assertEquals(dadosOrdemServico().getCliente().getCpf(), response.getBody().getCliente().getCpf());
        assertEquals(dadosOrdemServico().getCliente().getCelular(), response.getBody().getCliente().getCelular());
        assertEquals(dadosOrdemServico().getCliente().getEmail(), response.getBody().getCliente().getEmail());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getEndereco(), response.getBody().getCliente().getEndereco().getEndereco());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getComplemento(), response.getBody().getCliente().getEndereco().getComplemento());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getCidade(), response.getBody().getCliente().getEndereco().getCidade());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getEstado(), response.getBody().getCliente().getEndereco().getEstado());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getCep(), response.getBody().getCliente().getEndereco().getCep());

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
            assertEquals(mensagemErro().get(1), exception.getMessage());
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
        assertEquals(dadosOrdemServico().getLaudoTecnico(), response.getBody().getLaudoTecnico());
        assertEquals(dadosOrdemServico().getStatus(), response.getBody().getStatus());
        assertEquals(dadosOrdemServico().getDataInicial(), response.getBody().getDataInicial());
        assertEquals(dadosOrdemServico().getDataFinal(), response.getBody().getDataFinal());

        assertEquals(dadosOrdemServico().getCliente().getId(), response.getBody().getCliente().getId());
        assertEquals(dadosOrdemServico().getCliente().getNome(), response.getBody().getCliente().getNome());
        assertEquals(dadosOrdemServico().getCliente().getCpf(), response.getBody().getCliente().getCpf());
        assertEquals(dadosOrdemServico().getCliente().getCelular(), response.getBody().getCliente().getCelular());
        assertEquals(dadosOrdemServico().getCliente().getEmail(), response.getBody().getCliente().getEmail());

        assertEquals(dadosOrdemServico().getCliente().getEndereco().getEndereco(), response.getBody().getCliente().getEndereco().getEndereco());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getComplemento(), response.getBody().getCliente().getEndereco().getComplemento());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getCidade(), response.getBody().getCliente().getEndereco().getCidade());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getEstado(), response.getBody().getCliente().getEndereco().getEstado());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getCep(), response.getBody().getCliente().getEndereco().getCep());

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
                dadosOrdemServico().getLaudoTecnico(),
                dadosOrdemServico().getStatus(),
                dadosOrdemServico().getDataInicial(),
                dadosOrdemServico().getDataFinal(),
                dadosOrdemServico().getCliente(),
                dadosOrdemServico().getTecnicoResponsavel()
        );
        modelOrdemServicoDTO = new ModelOrdemServicoDTO(
                dadosOrdemServico().getId(),
                dadosOrdemServico().getDefeito(),
                dadosOrdemServico().getDescricao(),
                dadosOrdemServico().getLaudoTecnico(),
                dadosOrdemServico().getStatus(),
                dadosOrdemServico().getDataInicial(),
                dadosOrdemServico().getDataFinal(),
                dadosOrdemServico().getCliente(),
                dadosOrdemServico().getTecnicoResponsavel()
        );
    }
}