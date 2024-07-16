package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.exception.ClienteNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
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
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class UtilClientesTest extends ConfigDadosEstaticosEntidades{

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

        ModelClientes response = utilClientes.pesquisarClientePeloId(dadosClientes().getId());

        assertNotNull(response);

        assertEquals(dadosClientes().getId(), response.getId());
        assertEquals(dadosClientes().getNome(), response.getNome());
        assertEquals(dadosClientes().getCpf(), response.getCpf());
        assertEquals(dadosClientes().getCelular(), response.getCelular());
        assertEquals(dadosClientes().getEmail(), response.getEmail());
        assertEquals(dadosClientes().getEndereco(), response.getEndereco());
        assertEquals(dadosClientes().getCidade(), response.getCidade());
        assertEquals(dadosClientes().getEstado(), response.getEstado());
        assertEquals(dadosClientes().getCep(), response.getCep());
    }

    @DisplayName("Pesquisa um Cliente por ID e retorna Not Found")
    @Test
    void pesquisaClienteInexistenteRetornaNotFound(){
        when(clienteRepository.findById(anyLong())).thenThrow(new ClienteNaoEncontradoException());

        try{
            utilClientes.pesquisarClientePeloId(dadosClientes().getId());
        }catch (Exception exception){
            assertEquals(ClienteNaoEncontradoException.class, exception.getClass());
            assertEquals(mensagemErro().get(0), exception.getMessage());
        }
    }

    private void startClienteOptional(){
        modelClientesOptional = Optional.of(new ModelClientes(
                dadosClientes().getId(),
                dadosClientes().getNome(),
                dadosClientes().getCpf(),
                dadosClientes().getCelular(),
                dadosClientes().getEmail(),
                dadosClientes().getEndereco(),
                dadosClientes().getCidade(),
                dadosClientes().getEstado(),
                dadosClientes().getCep(),
                dadosClientes().getOrdemServicos())
        );
    }
}