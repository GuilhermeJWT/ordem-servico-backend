package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.ModelClientesDTO;
import br.com.systemsgs.ordem_servico_backend.exception.ClienteNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import br.com.systemsgs.ordem_servico_backend.service.ClienteService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteControllerTest extends ConfigDadosEstaticosEntidades{

    private ModelClientes modelClientes;
    private ModelClientesDTO modelClientesDTO;

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startCliente();
    }

    @DisplayName("Teste para retornar a lista de Clientes - retorna 200")
    @Test
    void retornaListaClientes200() {
        when(clienteService.listarClientes()).thenReturn(List.of(modelClientes));
        when(mapper.map(any(), any())).thenReturn(modelClientesDTO);

        ResponseEntity<List<ModelClientesDTO>> response = clienteController.listarClientes();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(ModelClientesDTO.class, response.getBody().get(0).getClass());

        assertEquals(dadosClientes().getId(), response.getBody().get(0).getId());
        assertEquals(dadosClientes().getNome(), response.getBody().get(0).getNome());
        assertEquals(dadosClientes().getCelular(), response.getBody().get(0).getCelular());
        assertEquals(dadosClientes().getCpf(), response.getBody().get(0).getCpf());
        assertEquals(dadosClientes().getEmail(), response.getBody().get(0).getEmail());
        assertEquals(dadosClientes().getEndereco(), response.getBody().get(0).getEndereco());
        assertEquals(dadosClientes().getCidade(), response.getBody().get(0).getCidade());
        assertEquals(dadosClientes().getEstado(), response.getBody().get(0).getEstado());
        assertEquals(dadosClientes().getCep(), response.getBody().get(0).getCep());
    }

    @DisplayName("Pesquisa um Cliente pelo ID e testa o Retorno do Body - retorna 200")
    @Test
    void pesquisaClientePorId200() {
        when(clienteService.listarClientes()).thenReturn(List.of(modelClientes));
        when(mapper.map(any(), any())).thenReturn(modelClientesDTO);

        ResponseEntity<ModelClientesDTO> response = clienteController.
                pesquisarPorId(dadosClientes().getId());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ModelClientesDTO.class, response.getBody().getClass());

        assertEquals(dadosClientes().getId(), response.getBody().getId());
        assertEquals(dadosClientes().getNome(), response.getBody().getNome());
        assertEquals(dadosClientes().getCelular(), response.getBody().getCelular());
        assertEquals(dadosClientes().getCpf(), response.getBody().getCpf());
        assertEquals(dadosClientes().getEmail(), response.getBody().getEmail());
        assertEquals(dadosClientes().getEndereco(), response.getBody().getEndereco());
        assertEquals(dadosClientes().getCidade(), response.getBody().getCidade());
        assertEquals(dadosClientes().getEstado(), response.getBody().getEstado());
        assertEquals(dadosClientes().getCep(), response.getBody().getCep());
    }

    @DisplayName("Pesquisa um Cliente inexistente e retorna 404")
    @Test
    void pesquisaClienteRetorna404(){
        when(clienteRepository.findById(anyLong())).thenThrow(new ClienteNaoEncontradoException());

        try{
            clienteService.pesquisaPorId(dadosClientes().getId());
        }catch (Exception exception){
            assertEquals(ClienteNaoEncontradoException.class, exception.getClass());
            assertEquals("Cliente não Encontrado!", exception.getMessage());
        }
    }

    @DisplayName("Salva um Cliente e retorna 201")
    @Test
    void salvaClienteRetorna201(){
        when(clienteService.salvarClientes(any())).thenReturn(modelClientes);

        ResponseEntity<ModelClientesDTO> response = clienteController.salvarCliente(modelClientesDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @DisplayName("Atualiza um Cliente e retorna 200")
    @Test
    void atualizaClienteRetorna200(){
        when(clienteService.updateClientes(dadosClientes().getId(),modelClientesDTO))
                .thenReturn(modelClientes);

        when(mapper.map(any(), any())).thenReturn(modelClientesDTO);

        ResponseEntity<ModelClientesDTO> response = clienteController.
                atualizarClientes(dadosClientes().getId(), modelClientesDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ModelClientesDTO.class, response.getBody().getClass());

        assertEquals(dadosClientes().getId(), response.getBody().getId());
        assertEquals(dadosClientes().getNome(), response.getBody().getNome());
        assertEquals(dadosClientes().getCelular(), response.getBody().getCelular());
        assertEquals(dadosClientes().getCpf(), response.getBody().getCpf());
        assertEquals(dadosClientes().getEmail(), response.getBody().getEmail());
        assertEquals(dadosClientes().getEndereco(), response.getBody().getEndereco());
        assertEquals(dadosClientes().getCidade(), response.getBody().getCidade());
        assertEquals(dadosClientes().getEstado(), response.getBody().getEstado());
        assertEquals(dadosClientes().getCep(), response.getBody().getCep());
    }

    @DisplayName("Deleta um Cliente e retorna 204")
    @Test
    void deletaClienteRetorna204(){
        doNothing().when(clienteService).deletarCliente(anyLong());

        ResponseEntity<ModelClientesDTO> response = clienteController.delete(dadosClientes().getId());

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clienteService, times(1)).deletarCliente(anyLong());
    }

    private void startCliente(){
        modelClientes = new ModelClientes(
                dadosClientes().getId(),
                dadosClientes().getNome(),
                dadosClientes().getCpf(),
                dadosClientes().getCelular(),
                dadosClientes().getEmail(),
                dadosClientes().getEndereco(),
                dadosClientes().getCidade(),
                dadosClientes().getEstado(),
                dadosClientes().getCep(),
                dadosClientes().getOrdemServicos()
        );
        modelClientesDTO = new ModelClientesDTO(
                dadosClientes().getId(),
                dadosClientes().getNome(),
                dadosClientes().getCpf(),
                dadosClientes().getCelular(),
                dadosClientes().getEmail(),
                dadosClientes().getEndereco(),
                dadosClientes().getCidade(),
                dadosClientes().getEstado(),
                dadosClientes().getCep()
        );
    }
}