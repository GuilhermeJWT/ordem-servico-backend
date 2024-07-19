package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.ModelClientesDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ClienteNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class ClienteServiceImplTest extends ConfigDadosEstaticosEntidades{

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

        ModelClientes response = clienteService.pesquisaPorId(dadosClientes().getId());

        assertNotNull(response);

        assertEquals(dadosClientes().getId(), response.getId());
        assertEquals(dadosClientes().getNome(), response.getNome());
        assertEquals(dadosClientes().getCelular(), response.getCelular());
        assertEquals(dadosClientes().getCpf(), response.getCpf());
        assertEquals(dadosClientes().getEmail(), response.getEmail());
        assertEquals(dadosClientes().getEndereco(), response.getEndereco());
        assertEquals(dadosClientes().getCidade(), response.getCidade());
        assertEquals(dadosClientes().getEstado(), response.getEstado());
        assertEquals(dadosClientes().getCep(), response.getCep());
        assertEquals(dadosClientes().getOrdemServicos(), response.getOrdemServicos());
    }

    @DisplayName("Pesquisa um Cliente Inexistente por ID - retorna 404")
    @Test
    void pesquisaClienteInexistenteRetornaNotFound(){
        when(clienteRepository.findById(anyLong())).thenThrow(new ClienteNaoEncontradoException());

        try{
            clienteService.pesquisaPorId(dadosClientes().getId());
        }catch (Exception exception){
            assertEquals(ClienteNaoEncontradoException.class, exception.getClass());
            assertEquals(mensagemErro().get(0), exception.getMessage());
        }

    }

    @DisplayName("Retorna uma lista de Clientes")
    @Test
    void listarClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of(modelClientes));

        List<ModelClientes> response = clienteService.listarClientes();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(ModelClientes.class, response.get(0).getClass());

        assertEquals(dadosClientes().getId(), response.get(0).getId());
        assertEquals(dadosClientes().getNome(), response.get(0).getNome());
        assertEquals(dadosClientes().getCelular(), response.get(0).getCelular());
        assertEquals(dadosClientes().getCpf(), response.get(0).getCpf());
        assertEquals(dadosClientes().getEmail(), response.get(0).getEmail());
        assertEquals(dadosClientes().getEndereco(), response.get(0).getEndereco());
        assertEquals(dadosClientes().getCidade(), response.get(0).getCidade());
        assertEquals(dadosClientes().getEstado(), response.get(0).getEstado());
        assertEquals(dadosClientes().getCep(), response.get(0).getCep());
        assertEquals(dadosClientes().getOrdemServicos(), response.get(0).getOrdemServicos());
    }

    @DisplayName("Salva um Cliente com Sucesso")
    @Test
    void deveSalvarUmClientes() {
        when(clienteRepository.save(any())).thenReturn(modelClientes);

        ModelClientes response = clienteService.salvarClientes(modelClientesDTO);

        assertNotNull(response);
        assertEquals(dadosClientes().getId(), response.getId());
        assertEquals(dadosClientes().getNome(), response.getNome());
        assertEquals(dadosClientes().getCelular(), response.getCelular());
        assertEquals(dadosClientes().getCpf(), response.getCpf());
        assertEquals(dadosClientes().getEmail(), response.getEmail());
        assertEquals(dadosClientes().getEndereco(), response.getEndereco());
        assertEquals(dadosClientes().getCidade(), response.getCidade());
        assertEquals(dadosClientes().getEstado(), response.getEstado());
        assertEquals(dadosClientes().getCep(), response.getCep());
        assertEquals(dadosClientes().getOrdemServicos(), response.getOrdemServicos());
    }

    @DisplayName("Deleta com Cliente com Sucesso")
    @Test
    void deletarCliente() {
        doNothing().when(clienteRepository).deleteById(anyLong());

        clienteService.deletarCliente(dadosClientes().getId());
        verify(clienteRepository, times(1)).deleteById(anyLong());
    }

    @DisplayName("Atualiza um Cliente com Sucesso")
    @Test
    void updateClientes() {
        when(clienteRepository.save(any())).thenReturn(modelClientes);
        when(utilClientes.pesquisarClientePeloId(anyLong())).thenReturn(modelClientes);

        ModelClientes response = clienteService.
                updateClientes(dadosClientes().getId(), modelClientesDTO);

        assertNotNull(response);
        assertEquals(dadosClientes().getId(), response.getId());
        assertEquals(dadosClientes().getNome(), response.getNome());
        assertEquals(dadosClientes().getCelular(), response.getCelular());
        assertEquals(dadosClientes().getCpf(), response.getCpf());
        assertEquals(dadosClientes().getEmail(), response.getEmail());
        assertEquals(dadosClientes().getEndereco(), response.getEndereco());
        assertEquals(dadosClientes().getCidade(), response.getCidade());
        assertEquals(dadosClientes().getEstado(), response.getEstado());
        assertEquals(dadosClientes().getCep(), response.getCep());
        assertEquals(dadosClientes().getOrdemServicos(), response.getOrdemServicos());
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