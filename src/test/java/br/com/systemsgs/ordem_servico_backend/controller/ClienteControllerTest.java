package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.dto.ModelClientesDTO;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
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
import static org.mockito.Mockito.when;

@SpringBootTest
class ClienteControllerTest {

    public static final Long ID = 1L;
    public static final String NOME = "Guilherme";
    public static final String CELULAR = "999999999";
    public static final String CPF = "819.945.180-73"; //gerado no GERADOR DE CPF
    public static final String EMAIL = "guilherme@gmail.com";
    public static final String ENDERECO = "Rua 1";
    public static final String CIDADE = "Caconde";
    public static final String ESTADO = "SP";
    public static final String CEP = "13770-000";
    public static final List<ModelOrdemServico> ORDEM_SERVICO = new ArrayList<>();
    public static final Integer INDEX_0 = 0;
    public static final Long CLIENTE_INEXISTENTE = 0L;

    private ModelClientes modelClientes;
    private ModelClientesDTO modelClientesDTO;

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

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
        assertEquals(ModelClientesDTO.class, response.getBody().get(INDEX_0).getClass());

        assertEquals(ID, response.getBody().get(INDEX_0).getId());
        assertEquals(NOME, response.getBody().get(INDEX_0).getNome());
        assertEquals(CELULAR, response.getBody().get(INDEX_0).getCelular());
        assertEquals(CPF, response.getBody().get(INDEX_0).getCpf());
        assertEquals(EMAIL, response.getBody().get(INDEX_0).getEmail());
        assertEquals(ENDERECO, response.getBody().get(INDEX_0).getEndereco());
        assertEquals(CIDADE, response.getBody().get(INDEX_0).getCidade());
        assertEquals(ESTADO, response.getBody().get(INDEX_0).getEstado());
        assertEquals(CEP, response.getBody().get(INDEX_0).getCep());
    }

    @DisplayName("Pesquisa um Cliente pelo ID e testa o Retorno do Body - retorna 200")
    @Test
    void pesquisaClientePorId200() {
        when(clienteService.listarClientes()).thenReturn(List.of(modelClientes));
        when(mapper.map(any(), any())).thenReturn(modelClientesDTO);

        ResponseEntity<ModelClientesDTO> response = clienteController.pesquisarPorId(CLIENTE_INEXISTENTE);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ModelClientesDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NOME, response.getBody().getNome());
        assertEquals(CELULAR, response.getBody().getCelular());
        assertEquals(CPF, response.getBody().getCpf());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(ENDERECO, response.getBody().getEndereco());
        assertEquals(CIDADE, response.getBody().getCidade());
        assertEquals(ESTADO, response.getBody().getEstado());
        assertEquals(CEP, response.getBody().getCep());
    }

    private void startCliente(){
        modelClientes = new ModelClientes(ID, NOME, CPF, CELULAR, EMAIL, ENDERECO, CIDADE, ESTADO, CEP, ORDEM_SERVICO);
        modelClientesDTO = new ModelClientesDTO(ID, NOME, CPF, CELULAR, EMAIL, ENDERECO, CIDADE, ESTADO, CEP);
    }

}