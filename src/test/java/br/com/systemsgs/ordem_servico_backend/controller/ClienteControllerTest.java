package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelClientesDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.ClienteResponse;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ClienteNaoEncontradoException;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class ClienteControllerTest extends ConfigDadosEstaticosEntidades{

    private ModelClientes modelClientes;
    private ModelClientesDTO modelClientesDTO;
    private ClienteResponse clienteResponse;

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
        when(mapper.map(any(), any())).thenReturn(clienteResponse);

        ResponseEntity<List<ClienteResponse>> response = clienteController.listarClientes();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(ClienteResponse.class, response.getBody().get(0).getClass());

        assertEquals(dadosClientes().getId(), response.getBody().get(0).getId());
        assertEquals(dadosClientes().getNome(), response.getBody().get(0).getNome());
        assertEquals(dadosClientes().getCelular(), response.getBody().get(0).getCelular());
        assertEquals(dadosClientes().getCpf(), response.getBody().get(0).getCpf());
        assertEquals(dadosClientes().getEmail(), response.getBody().get(0).getEmail());

        assertEquals(dadosClientes().getEndereco().getEndereco(), response.getBody().get(0).getEndereco().getEndereco());
        assertEquals(dadosClientes().getEndereco().getComplemento(), response.getBody().get(0).getEndereco().getComplemento());
        assertEquals(dadosClientes().getEndereco().getCidade(), response.getBody().get(0).getEndereco().getCidade());
        assertEquals(dadosClientes().getEndereco().getEstado(), response.getBody().get(0).getEndereco().getEstado());
        assertEquals(dadosClientes().getEndereco().getCep(), response.getBody().get(0).getEndereco().getCep());

    }

    @DisplayName("Pesquisa um Cliente pelo ID e testa o Retorno do Body - retorna 200")
    @Test
    void pesquisaClientePorId200() {
        when(clienteService.listarClientes()).thenReturn(List.of(modelClientes));
        when(mapper.map(any(), any())).thenReturn(clienteResponse);

        ResponseEntity<ClienteResponse> response = clienteController.
                pesquisarPorId(dadosClientes().getId());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ClienteResponse.class, response.getBody().getClass());

        assertEquals(dadosClientes().getId(), response.getBody().getId());
        assertEquals(dadosClientes().getNome(), response.getBody().getNome());
        assertEquals(dadosClientes().getCelular(), response.getBody().getCelular());
        assertEquals(dadosClientes().getCpf(), response.getBody().getCpf());
        assertEquals(dadosClientes().getEmail(), response.getBody().getEmail());

        assertEquals(dadosClientes().getEndereco().getEndereco(), response.getBody().getEndereco().getEndereco());
        assertEquals(dadosClientes().getEndereco().getComplemento(), response.getBody().getEndereco().getComplemento());
        assertEquals(dadosClientes().getEndereco().getCidade(), response.getBody().getEndereco().getCidade());
        assertEquals(dadosClientes().getEndereco().getEstado(), response.getBody().getEndereco().getEstado());
        assertEquals(dadosClientes().getEndereco().getCep(), response.getBody().getEndereco().getCep());

    }

    @DisplayName("Pesquisa um Cliente inexistente e retorna 404")
    @Test
    void pesquisaClienteRetorna404(){
        when(clienteRepository.findById(anyLong())).thenThrow(new ClienteNaoEncontradoException());

        try{
            clienteService.pesquisaPorId(dadosClientes().getId());
        }catch (Exception exception){
            assertEquals(ClienteNaoEncontradoException.class, exception.getClass());
            assertEquals(mensagemErro().get(0), exception.getMessage());
        }
    }

    @DisplayName("Salva um Cliente e retorna 201")
    @Test
    void salvaClienteRetorna201(){
        when(clienteService.salvarClientes(any())).thenReturn(modelClientes);

        ResponseEntity<ClienteResponse> response = clienteController.salvarCliente(modelClientesDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @DisplayName("Atualiza um Cliente e retorna 200")
    @Test
    void atualizaClienteRetorna200(){
        when(clienteService.updateClientes(dadosClientes().getId(),modelClientesDTO))
                .thenReturn(modelClientes);

        when(mapper.map(any(), any())).thenReturn(clienteResponse);

        ResponseEntity<ClienteResponse> response = clienteController.
                atualizarClientes(dadosClientes().getId(), modelClientesDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ClienteResponse.class, response.getBody().getClass());

        assertEquals(dadosClientes().getId(), response.getBody().getId());
        assertEquals(dadosClientes().getNome(), response.getBody().getNome());
        assertEquals(dadosClientes().getCelular(), response.getBody().getCelular());
        assertEquals(dadosClientes().getCpf(), response.getBody().getCpf());
        assertEquals(dadosClientes().getEmail(), response.getBody().getEmail());

        assertEquals(dadosClientes().getEndereco().getEndereco(), response.getBody().getEndereco().getEndereco());
        assertEquals(dadosClientes().getEndereco().getComplemento(), response.getBody().getEndereco().getComplemento());
        assertEquals(dadosClientes().getEndereco().getCidade(), response.getBody().getEndereco().getCidade());
        assertEquals(dadosClientes().getEndereco().getEstado(), response.getBody().getEndereco().getEstado());
        assertEquals(dadosClientes().getEndereco().getCep(), response.getBody().getEndereco().getCep());

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
                dadosClientes().getOrdemServicos()
        );
        modelClientesDTO = new ModelClientesDTO(
                dadosClientes().getId(),
                dadosClientes().getNome(),
                dadosClientes().getCpf(),
                dadosClientes().getCelular(),
                dadosClientes().getEmail(),
                dadosClientes().getEndereco().getEndereco(),
                dadosClientes().getEndereco().getComplemento(),
                dadosClientes().getEndereco().getCidade(),
                dadosClientes().getEndereco().getEstado(),
                dadosClientes().getEndereco().getCep()
        );
        clienteResponse = new ClienteResponse(
                dadosClientes().getId(),
                dadosClientes().getNome(),
                dadosClientes().getCpf(),
                dadosClientes().getCelular(),
                dadosClientes().getEmail(),
                dadosEndereco()
        );
    }
}