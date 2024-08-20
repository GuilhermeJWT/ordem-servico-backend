package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelClientesDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.ClienteResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class ClienteServiceImplTest extends ConfigDadosEstaticosEntidades{

    private ModelClientes modelClientes;
    private ModelClientesDTO modelClientesDTO;
    private Optional<ModelClientes> modelClientesOptional;
    private ClienteResponse clienteResponse;

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
        clienteService = new ClienteServiceImpl(clienteRepository, utilClientes, mapper);
        startCliente();
    }

    @DisplayName("Pesquisa um Cliente por ID")
    @Test
    void pesquisaClientePorId() {
        when(clienteRepository.findById(modelClientes.getId())).thenReturn(modelClientesOptional);

        ModelClientes response = clienteService.pesquisaPorId(dadosClientes().getId());

        assertNotNull(response);

        assertEquals(dadosClientes().getId(), response.getId());
        assertEquals(dadosClientes().getNome(), response.getNome());
        assertEquals(dadosClientes().getCelular(), response.getCelular());
        assertEquals(dadosClientes().getCpf(), response.getCpf());
        assertEquals(dadosClientes().getEmail(), response.getEmail());
        assertEquals(dadosClientes().getEndereco().getEndereco(), response.getEndereco().getEndereco());
        assertEquals(dadosClientes().getEndereco().getComplemento(), response.getEndereco().getComplemento());
        assertEquals(dadosClientes().getEndereco().getCidade(), response.getEndereco().getCidade());
        assertEquals(dadosClientes().getEndereco().getEstado(), response.getEndereco().getEstado());
        assertEquals(dadosClientes().getEndereco().getCep(), response.getEndereco().getCep());
        assertEquals(dadosClientes().getOrdemServicos(), response.getOrdemServicos());
    }

    @DisplayName("Pesquisa um Cliente Inexistente por ID - retorna 404")
    @Test
    void pesquisaClienteInexistenteRetornaNotFound(){
        when(clienteRepository.findById(modelClientes.getId())).thenThrow(new ClienteNaoEncontradoException());

        try{
            clienteService.pesquisaPorId(dadosClientes().getId());
        }catch (Exception exception){
            assertEquals(ClienteNaoEncontradoException.class, exception.getClass());
            assertEquals(mensagemErro().get(0), exception.getMessage());
        }

    }

    @DisplayName("Teste para retornar os Clientes Paginados")
    @Test
    void testListarClientesPaginado() {
        List<ModelClientes> clientesList = Arrays.asList(modelClientes, modelClientes);
        Page<ModelClientes> clientesPage = new PageImpl<>(clientesList, PageRequest.of(0, 10), clientesList.size());

        when(clienteRepository.findAll(PageRequest.of(0, 10))).thenReturn(clientesPage);

        when(mapper.map(modelClientes, ClienteResponse.class)).thenReturn(clienteResponse);
        when(mapper.map(modelClientes, ClienteResponse.class)).thenReturn(clienteResponse);

        Page<ClienteResponse> response = clienteService.listarClientesPaginado(0, 10);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getContent().get(0)).isEqualTo(clienteResponse);
        assertThat(response.getContent().get(1)).isEqualTo(clienteResponse);

        assertEquals(dadosClientes().getId(), response.getContent().get(0).getId());
        assertEquals(dadosClientes().getNome(), response.getContent().get(0).getNome());
        assertEquals(dadosClientes().getCelular(), response.getContent().get(0).getCelular());
        assertEquals(dadosClientes().getCpf(), response.getContent().get(0).getCpf());
        assertEquals(dadosClientes().getEmail(), response.getContent().get(0).getEmail());

        assertEquals(dadosClientes().getEndereco().getEndereco(), response.getContent().get(0).getEndereco().getEndereco());
        assertEquals(dadosClientes().getEndereco().getComplemento(), response.getContent().get(0).getEndereco().getComplemento());
        assertEquals(dadosClientes().getEndereco().getCidade(), response.getContent().get(0).getEndereco().getCidade());
        assertEquals(dadosClientes().getEndereco().getEstado(), response.getContent().get(0).getEndereco().getEstado());
        assertEquals(dadosClientes().getEndereco().getCep(), response.getContent().get(0).getEndereco().getCep());
    }

    @DisplayName("Teste para Paginação de Clientes vazia - Paginação sem dados")
    @Test
    void listarClientesPaginadoComPaginaVazia() {
        Page<ModelClientes> clientesPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);

        when(clienteRepository.findAll(PageRequest.of(0, 10))).thenReturn(clientesPage);

        Page<ClienteResponse> response = clienteService.listarClientesPaginado(0, 10);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).isEmpty();
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
        assertEquals(dadosClientes().getEndereco().getEndereco(), response.get(0).getEndereco().getEndereco());
        assertEquals(dadosClientes().getEndereco().getComplemento(), response.get(0).getEndereco().getComplemento());
        assertEquals(dadosClientes().getEndereco().getCidade(), response.get(0).getEndereco().getCidade());
        assertEquals(dadosClientes().getEndereco().getEstado(), response.get(0).getEndereco().getEstado());
        assertEquals(dadosClientes().getEndereco().getCep(), response.get(0).getEndereco().getCep());
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
        assertEquals(dadosClientes().getEndereco().getEndereco(), response.getEndereco().getEndereco());
        assertEquals(dadosClientes().getEndereco().getComplemento(), response.getEndereco().getComplemento());
        assertEquals(dadosClientes().getEndereco().getCidade(), response.getEndereco().getCidade());
        assertEquals(dadosClientes().getEndereco().getEstado(), response.getEndereco().getEstado());
        assertEquals(dadosClientes().getEndereco().getCep(), response.getEndereco().getCep());
        assertEquals(dadosClientes().getOrdemServicos(), response.getOrdemServicos());
    }

    @DisplayName("Deleta com Cliente com Sucesso")
    @Test
    void deletarCliente() {
        doNothing().when(clienteRepository).deleteById(modelClientes.getId());

        clienteService.deletarCliente(dadosClientes().getId());
        verify(clienteRepository, times(1)).deleteById(modelClientes.getId());
    }

    @DisplayName("Atualiza um Cliente com Sucesso")
    @Test
    void updateClientes() {
        when(clienteRepository.save(modelClientes)).thenReturn(modelClientes);
        when(utilClientes.pesquisarClientePeloId(modelClientes.getId())).thenReturn(modelClientes);

        ModelClientes response = clienteService.
                updateClientes(dadosClientes().getId(), modelClientesDTO);

        assertNotNull(response);
        assertEquals(dadosClientes().getId(), response.getId());
        assertEquals(dadosClientes().getNome(), response.getNome());
        assertEquals(dadosClientes().getCelular(), response.getCelular());
        assertEquals(dadosClientes().getCpf(), response.getCpf());
        assertEquals(dadosClientes().getEmail(), response.getEmail());
        assertEquals(dadosClientes().getEndereco().getEndereco(), response.getEndereco().getEndereco());
        assertEquals(dadosClientes().getEndereco().getComplemento(), response.getEndereco().getComplemento());
        assertEquals(dadosClientes().getEndereco().getCidade(), response.getEndereco().getCidade());
        assertEquals(dadosClientes().getEndereco().getEstado(), response.getEndereco().getEstado());
        assertEquals(dadosClientes().getEndereco().getCep(), response.getEndereco().getCep());
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
                dadosClientes().getOrdemServicos()
        );
        modelClientesDTO = new ModelClientesDTO(
                dadosClientes().getId(),
                dadosClientes().getNome(),
                dadosClientes().getCpf(),
                dadosClientes().getCelular(),
                dadosClientes().getEmail(),
                dadosClientes().getEndereco().getEndereco(),
                dadosClientes().getEndereco().getCidade(),
                dadosClientes().getEndereco().getEstado(),
                dadosClientes().getEndereco().getCep(),
                dadosClientes().getEndereco().getComplemento()
        );
        modelClientesOptional = Optional.of(new ModelClientes(
                dadosClientes().getId(),
                dadosClientes().getNome(),
                dadosClientes().getCpf(),
                dadosClientes().getCelular(),
                dadosClientes().getEmail(),
                dadosClientes().getEndereco(),
                dadosClientes().getOrdemServicos())
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