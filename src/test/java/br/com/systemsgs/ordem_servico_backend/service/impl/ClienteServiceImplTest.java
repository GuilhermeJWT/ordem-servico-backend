package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.ModelClientesDTO;
import br.com.systemsgs.ordem_servico_backend.exception.ClienteNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClienteServiceImplTest {

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
    public static final String CLIENTE_NAO_ENCONTRADO = "Cliente não Encontrado!";

    private ModelClientes modelClientes;
    private ModelClientesDTO modelClientesDTO;
    private Optional<ModelClientes> modelClientesOptional;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private UtilClientes utilClientes;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCliente();
    }

    @DisplayName("Pesquisa um Cliente por ID")
    @Test
    void pesquisaClientePorId() {
        when(clienteRepository.findById(anyLong())).thenReturn(modelClientesOptional);

        ModelClientes response = clienteService.pesquisaPorId(ID);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(CELULAR, response.getCelular());
        assertEquals(CPF, response.getCpf());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(ENDERECO, response.getEndereco());
        assertEquals(CIDADE, response.getCidade());
        assertEquals(ESTADO, response.getEstado());
        assertEquals(CEP, response.getCep());
        assertEquals(ORDEM_SERVICO, response.getOrdemServicos());
    }

    @DisplayName("Pesquisa um Cliente por ID")
    @Test
    void pesquisaClienteInexistenteRetornaNotFound(){
        when(clienteRepository.findById(anyLong())).thenThrow(new ClienteNaoEncontradoException());

        try{
            clienteService.pesquisaPorId(ID);
        }catch (Exception exception){
            assertEquals(ClienteNaoEncontradoException.class, exception.getClass());
            assertEquals(CLIENTE_NAO_ENCONTRADO, exception.getMessage());
        }

    }

    @DisplayName("Retorna uma lista de Clientes")
    @Test
    void listarClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of(modelClientes));

        List<ModelClientes> response = clienteService.listarClientes();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(ModelClientes.class, response.get(INDEX_0).getClass());

        assertEquals(ID, response.get(INDEX_0).getId());
        assertEquals(NOME, response.get(INDEX_0).getNome());
        assertEquals(CELULAR, response.get(INDEX_0).getCelular());
        assertEquals(CPF, response.get(INDEX_0).getCpf());
        assertEquals(EMAIL, response.get(INDEX_0).getEmail());
        assertEquals(ENDERECO, response.get(INDEX_0).getEndereco());
        assertEquals(CIDADE, response.get(INDEX_0).getCidade());
        assertEquals(ESTADO, response.get(INDEX_0).getEstado());
        assertEquals(CEP, response.get(INDEX_0).getCep());
        assertEquals(ORDEM_SERVICO, response.get(INDEX_0).getOrdemServicos());
    }

    @DisplayName("Salva um Cliente com Sucesso")
    @Test
    void deveSalvarUmClientes() {
        when(clienteRepository.save(any())).thenReturn(modelClientes);

        ModelClientes response = clienteService.salvarClientes(modelClientesDTO);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(CELULAR, response.getCelular());
        assertEquals(CPF, response.getCpf());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(ENDERECO, response.getEndereco());
        assertEquals(CIDADE, response.getCidade());
        assertEquals(ESTADO, response.getEstado());
        assertEquals(CEP, response.getCep());
        assertEquals(ORDEM_SERVICO, response.getOrdemServicos());
    }

    @DisplayName("Deleta com Cliente com Sucesso")
    @Test
    void deletarCliente() {
        doNothing().when(clienteRepository).deleteById(anyLong());

        clienteService.deletarCliente(ID);
        verify(clienteRepository, times(1)).deleteById(anyLong());
    }

    @DisplayName("Atualiza um Cliente com Sucesso")
    @Test
    void updateClientes() {
        when(clienteRepository.save(any())).thenReturn(modelClientes);
        when(utilClientes.pesquisarClientePeloId(anyLong())).thenReturn(modelClientes);

        ModelClientes response = clienteService.updateClientes(ID, modelClientesDTO);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(CELULAR, response.getCelular());
        assertEquals(CPF, response.getCpf());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(ENDERECO, response.getEndereco());
        assertEquals(CIDADE, response.getCidade());
        assertEquals(ESTADO, response.getEstado());
        assertEquals(CEP, response.getCep());
        assertEquals(ORDEM_SERVICO, response.getOrdemServicos());
    }

    private void startCliente(){
        modelClientes = new ModelClientes(ID, NOME, CPF, CELULAR, EMAIL, ENDERECO, CIDADE, ESTADO, CEP, ORDEM_SERVICO);
        modelClientesDTO = new ModelClientesDTO(ID, NOME, CPF, CELULAR, EMAIL, ENDERECO, CIDADE, ESTADO, CEP);
        modelClientesOptional = Optional.of(new ModelClientes(ID, NOME, CPF, CELULAR, EMAIL, ENDERECO, CIDADE, ESTADO, CEP, ORDEM_SERVICO));
    }

}