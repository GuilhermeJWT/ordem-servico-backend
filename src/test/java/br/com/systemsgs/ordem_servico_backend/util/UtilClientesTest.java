package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.exception.ClienteNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class UtilClientesTest {

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
    public static final String CLIENTE_NAO_ENCONTRADO = "Cliente não Encontrado!";

    private Optional<ModelClientes> modelClientesOptional;

    @InjectMocks
    private UtilClientes utilClientes;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startClienteOptional();
    }

    @DisplayName("Pesquisa um Cliente por ID e retorna a Entidade para Validação")
    @Test
    void pesquisarClientePeloId() {
        when(clienteRepository.findById(anyLong())).thenReturn(modelClientesOptional);

        ModelClientes response = utilClientes.pesquisarClientePeloId(ID);

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

    @DisplayName("Pesquisa um Cliente por ID e retorna Not Found")
    @Test
    void pesquisaClienteInexistenteRetornaNotFound(){
        when(clienteRepository.findById(anyLong())).thenThrow(new ClienteNaoEncontradoException());

        try{
            utilClientes.pesquisarClientePeloId(ID);
        }catch (Exception exception){
            assertEquals(ClienteNaoEncontradoException.class, exception.getClass());
            assertEquals(CLIENTE_NAO_ENCONTRADO, exception.getMessage());
        }
    }

    private void startClienteOptional(){
        modelClientesOptional = Optional.of(new ModelClientes(ID, NOME, CPF, CELULAR, EMAIL, ENDERECO, CIDADE, ESTADO, CEP, ORDEM_SERVICO));
    }
}