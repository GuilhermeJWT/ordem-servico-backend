package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.ModelClientesDTO;
import br.com.systemsgs.ordem_servico_backend.exception.ClienteNaoEncontradoException;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClienteServiceImplTest {

    private ModelClientes modelClientes;
    private ModelClientesDTO modelClientesDTO;
    private Optional<ModelClientes> modelClientesOptional;

    private ConfigDadosEstaticosEntidades getDadosEstaticosCliente = new ConfigDadosEstaticosEntidades();

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

        ModelClientes response = clienteService.pesquisaPorId(getDadosEstaticosCliente.dadosClientes().getId());

        assertNotNull(response);

        assertEquals(getDadosEstaticosCliente.dadosClientes().getId(), response.getId());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getNome(), response.getNome());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCelular(), response.getCelular());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCpf(), response.getCpf());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getEmail(), response.getEmail());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getEndereco(), response.getEndereco());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCidade(), response.getCidade());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getEstado(), response.getEstado());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCep(), response.getCep());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getOrdemServicos(), response.getOrdemServicos());
    }

    @DisplayName("Pesquisa um Cliente por ID")
    @Test
    void pesquisaClienteInexistenteRetornaNotFound(){
        when(clienteRepository.findById(anyLong())).thenThrow(new ClienteNaoEncontradoException());

        try{
            clienteService.pesquisaPorId(getDadosEstaticosCliente.dadosClientes().getId());
        }catch (Exception exception){
            assertEquals(ClienteNaoEncontradoException.class, exception.getClass());
            assertEquals(getDadosEstaticosCliente.mensagemErro().get(0), exception.getMessage());
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

        assertEquals(getDadosEstaticosCliente.dadosClientes().getId(), response.get(0).getId());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getNome(), response.get(0).getNome());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCelular(), response.get(0).getCelular());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCpf(), response.get(0).getCpf());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getEmail(), response.get(0).getEmail());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getEndereco(), response.get(0).getEndereco());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCidade(), response.get(0).getCidade());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getEstado(), response.get(0).getEstado());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCep(), response.get(0).getCep());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getOrdemServicos(), response.get(0).getOrdemServicos());
    }

    @DisplayName("Salva um Cliente com Sucesso")
    @Test
    void deveSalvarUmClientes() {
        when(clienteRepository.save(any())).thenReturn(modelClientes);

        ModelClientes response = clienteService.salvarClientes(modelClientesDTO);

        assertNotNull(response);
        assertEquals(getDadosEstaticosCliente.dadosClientes().getId(), response.getId());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getNome(), response.getNome());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCelular(), response.getCelular());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCpf(), response.getCpf());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getEmail(), response.getEmail());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getEndereco(), response.getEndereco());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCidade(), response.getCidade());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getEstado(), response.getEstado());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCep(), response.getCep());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getOrdemServicos(), response.getOrdemServicos());
    }

    @DisplayName("Deleta com Cliente com Sucesso")
    @Test
    void deletarCliente() {
        doNothing().when(clienteRepository).deleteById(anyLong());

        clienteService.deletarCliente(getDadosEstaticosCliente.dadosClientes().getId());
        verify(clienteRepository, times(1)).deleteById(anyLong());
    }

    @DisplayName("Atualiza um Cliente com Sucesso")
    @Test
    void updateClientes() {
        when(clienteRepository.save(any())).thenReturn(modelClientes);
        when(utilClientes.pesquisarClientePeloId(anyLong())).thenReturn(modelClientes);

        ModelClientes response = clienteService.
                updateClientes(getDadosEstaticosCliente.dadosClientes().getId(), modelClientesDTO);

        assertNotNull(response);
        assertEquals(getDadosEstaticosCliente.dadosClientes().getId(), response.getId());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getNome(), response.getNome());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCelular(), response.getCelular());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCpf(), response.getCpf());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getEmail(), response.getEmail());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getEndereco(), response.getEndereco());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCidade(), response.getCidade());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getEstado(), response.getEstado());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getCep(), response.getCep());
        assertEquals(getDadosEstaticosCliente.dadosClientes().getOrdemServicos(), response.getOrdemServicos());
    }

    private void startCliente(){
        modelClientes = new ModelClientes(
                getDadosEstaticosCliente.dadosClientes().getId(),
                getDadosEstaticosCliente.dadosClientes().getNome(),
                getDadosEstaticosCliente.dadosClientes().getCpf(),
                getDadosEstaticosCliente.dadosClientes().getCelular(),
                getDadosEstaticosCliente.dadosClientes().getEmail(),
                getDadosEstaticosCliente.dadosClientes().getEndereco(),
                getDadosEstaticosCliente.dadosClientes().getCidade(),
                getDadosEstaticosCliente.dadosClientes().getEstado(),
                getDadosEstaticosCliente.dadosClientes().getCep(),
                getDadosEstaticosCliente.dadosClientes().getOrdemServicos()
        );
        modelClientesDTO = new ModelClientesDTO(
                getDadosEstaticosCliente.dadosClientes().getId(),
                getDadosEstaticosCliente.dadosClientes().getNome(),
                getDadosEstaticosCliente.dadosClientes().getCpf(),
                getDadosEstaticosCliente.dadosClientes().getCelular(),
                getDadosEstaticosCliente.dadosClientes().getEmail(),
                getDadosEstaticosCliente.dadosClientes().getEndereco(),
                getDadosEstaticosCliente.dadosClientes().getCidade(),
                getDadosEstaticosCliente.dadosClientes().getEstado(),
                getDadosEstaticosCliente.dadosClientes().getCep()
        );
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