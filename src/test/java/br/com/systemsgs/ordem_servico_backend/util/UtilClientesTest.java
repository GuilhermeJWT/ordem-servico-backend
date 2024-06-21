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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class UtilClientesTest {

    private Optional<ModelClientes> modelClientesOptional;

    private ConfigDadosEstaticosEntidades getDadosEstaticosCliente = new ConfigDadosEstaticosEntidades();

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

        ModelClientes response = utilClientes.pesquisarClientePeloId(getDadosEstaticosCliente.dadosClientes().getId());

        assertNotNull(response);

        assertEquals(getDadosEstaticosCliente.dadosClientes().getId(), response.getId());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getNome(), response.getNome());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCpf(), response.getCpf());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCelular(), response.getCelular());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getEmail(), response.getEmail());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getEndereco(), response.getEndereco());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCidade(), response.getCidade());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getEstado(), response.getEstado());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCep(), response.getCep());
    }

    @DisplayName("Pesquisa um Cliente por ID e retorna Not Found")
    @Test
    void pesquisaClienteInexistenteRetornaNotFound(){
        when(clienteRepository.findById(anyLong())).thenThrow(new ClienteNaoEncontradoException());

        try{
            utilClientes.pesquisarClientePeloId(getDadosEstaticosCliente.dadosClientes().getId());
        }catch (Exception exception){
            assertEquals(ClienteNaoEncontradoException.class, exception.getClass());
            assertEquals(getDadosEstaticosCliente.mensagemErro().get(0), exception.getMessage());
        }
    }

    private void startClienteOptional(){
        modelClientesOptional = Optional.of(new ModelClientes(
                getDadosEstaticosCliente.dadosClientes().getId(),
                getDadosEstaticosCliente.dadosClientes().getNome(),
                getDadosEstaticosCliente.dadosClientes().getCpf(),
                getDadosEstaticosCliente.dadosClientes().getCelular(),
                getDadosEstaticosCliente.dadosClientes().getEmail(),
                getDadosEstaticosCliente.dadosClientes().getEndereco(),
                getDadosEstaticosCliente.dadosClientes().getCidade(),
                getDadosEstaticosCliente.dadosClientes().getEstado(),
                getDadosEstaticosCliente.dadosClientes().getCep(),
                getDadosEstaticosCliente.dadosClientes().getOrdemServicos())
        );
    }
}