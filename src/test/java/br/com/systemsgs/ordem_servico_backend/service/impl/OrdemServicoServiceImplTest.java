package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.ModelClientesDTO;
import br.com.systemsgs.ordem_servico_backend.dto.ModelOrdemServicoDTO;
import br.com.systemsgs.ordem_servico_backend.exception.ClienteNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import br.com.systemsgs.ordem_servico_backend.repository.OrdemServicoRepository;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import br.com.systemsgs.ordem_servico_backend.util.UtilOrdemServico;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class OrdemServicoServiceImplTest {

    private ModelOrdemServico modelOrdemServico;
    private ModelOrdemServicoDTO modelOrdemServicoDTO;
    private Optional<ModelOrdemServico> modelOrdemServicoOptional;
    private ModelClientes modelClientes;
    private ModelClientesDTO modelClientesDTO;

    private ConfigDadosEstaticosEntidades getDadosEstaticosOS = new ConfigDadosEstaticosEntidades();

    @InjectMocks
    private OrdemServicoServiceImpl ordemServicoServiceImpl;

    @Mock
    private OrdemServicoRepository ordemServicoRepository;

    @Mock
    private UtilOrdemServico utilOrdemServico;

    @Mock
    private UtilClientes utilClientes;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startOrdemServico();
    }

    @DisplayName("Pesquisa uma OS por ID")
    @Test
    void pesquisaPorId() {
        when(ordemServicoRepository.findById(anyLong())).thenReturn(modelOrdemServicoOptional);

        ModelOrdemServico response = ordemServicoServiceImpl.pesquisaPorId(getDadosEstaticosOS.dadosOrdemServico().getId());

        assertNotNull(response);
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getId(), response.getId());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getDefeito(), response.getDefeito());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getDescricao(), response.getDescricao());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getLaudo_tecnico(), response.getLaudo_tecnico());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getStatus(), response.getStatus());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getData_inicial(), response.getData_inicial());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getData_final(), response.getData_final());

        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getId(), response.getCliente().getId());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getNome(), response.getCliente().getNome());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getCpf(), response.getCliente().getCpf());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getCelular(), response.getCliente().getCelular());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getEmail(), response.getCliente().getEmail());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getEndereco(), response.getCliente().getEndereco());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getCidade(), response.getCliente().getCidade());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getEstado(), response.getCliente().getEstado());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getCep(), response.getCliente().getCep());
    }

    @DisplayName("Pesquisa uma OS por ID")
    @Test
    void pesquisaOSInexistenteRetornaNotFound(){
        when(ordemServicoRepository.findById(anyLong())).thenThrow(new RecursoNaoEncontradoException());

        try{
            ordemServicoServiceImpl.pesquisaPorId(getDadosEstaticosOS.dadosOrdemServico().getId());
        }catch (Exception exception){
            assertEquals(RecursoNaoEncontradoException.class, exception.getClass());
            assertEquals(getDadosEstaticosOS.mensagemErro().get(1), exception.getMessage());
        }
    }

    @DisplayName("Retorna uma lista de OS")
    @Test
    void listarOrdemServico() {
        when(ordemServicoRepository.findAll()).thenReturn(List.of(modelOrdemServico));

        List<ModelOrdemServico> response = ordemServicoServiceImpl.listarOS();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(ModelOrdemServico.class, response.get(0).getClass());

        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getId(), response.get(0).getId());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getDefeito(), response.get(0).getDefeito());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getDescricao(), response.get(0).getDescricao());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getLaudo_tecnico(), response.get(0).getLaudo_tecnico());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getStatus(), response.get(0).getStatus());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getData_inicial(), response.get(0).getData_inicial());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getData_final(), response.get(0).getData_final());

        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getId(), response.get(0).getCliente().getId());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getNome(), response.get(0).getCliente().getNome());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getCpf(), response.get(0).getCliente().getCpf());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getCelular(), response.get(0).getCliente().getCelular());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getEmail(), response.get(0).getCliente().getEmail());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getEndereco(), response.get(0).getCliente().getEndereco());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getCidade(), response.get(0).getCliente().getCidade());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getEstado(), response.get(0).getCliente().getEstado());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getCep(), response.get(0).getCliente().getCep());
    }

    @DisplayName("Valida um Cliente antes de Salvar")
    @Test
    void validaClienteAntesSalvar() {
        when(utilClientes.pesquisarClientePeloId(anyLong())).thenReturn(modelClientes);

        assertNotNull(modelClientesDTO.getClass());

        assertEquals(getDadosEstaticosOS.dadosClientes().getId(), modelClientesDTO.getId());
        assertEquals(getDadosEstaticosOS.dadosClientes().getNome(), modelClientesDTO.getNome());
        assertEquals(getDadosEstaticosOS.dadosClientes().getCelular(), modelClientesDTO.getCelular());
        assertEquals(getDadosEstaticosOS.dadosClientes().getCpf(), modelClientesDTO.getCpf());
        assertEquals(getDadosEstaticosOS.dadosClientes().getEmail(), modelClientesDTO.getEmail());
        assertEquals(getDadosEstaticosOS.dadosClientes().getEndereco(), modelClientesDTO.getEndereco());
        assertEquals(getDadosEstaticosOS.dadosClientes().getCidade(), modelClientesDTO.getCidade());
        assertEquals(getDadosEstaticosOS.dadosClientes().getEstado(), modelClientesDTO.getEstado());
        assertEquals(getDadosEstaticosOS.dadosClientes().getCep(), modelClientesDTO.getCep());
    }

    @DisplayName("Retorna Cliente não Encontrado - Not Found")
    @Test
    void pesquisaClienteRetornaNotFound(){
        try{
            when(ordemServicoServiceImpl.validaCliente(modelOrdemServicoDTO)).thenThrow(new ClienteNaoEncontradoException());
        }catch (Exception exception){
            assertEquals(ClienteNaoEncontradoException.class, exception.getClass());
            assertEquals(getDadosEstaticosOS.mensagemErro().get(0), exception.getMessage());
        }
    }

    @DisplayName("Salva com OS com Sucesso")
    @Test
    void salvarOS() {
        when(utilClientes.pesquisarClientePeloId(anyLong())).thenReturn(modelClientes);
        //when(utilClientes.pesquisarClientePeloId(getDadosEstaticosOS.dadosOrdemServico().getId())).thenReturn(modelClientes);
        when(ordemServicoRepository.save(any())).thenReturn(modelOrdemServico);

        ModelOrdemServico response = ordemServicoServiceImpl.salvarOS(modelOrdemServicoDTO);

        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getId(), response.getId());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getDefeito(), response.getDefeito());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getDescricao(), response.getDescricao());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getLaudo_tecnico(), response.getLaudo_tecnico());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getStatus(), response.getStatus());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getData_inicial(), response.getData_inicial());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getData_final(), response.getData_final());

        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getId(), response.getCliente().getId());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getNome(), response.getCliente().getNome());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getCpf(), response.getCliente().getCpf());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getCelular(), response.getCliente().getCelular());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getEmail(), response.getCliente().getEmail());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getEndereco(), response.getCliente().getEndereco());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getCidade(), response.getCliente().getCidade());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getEstado(), response.getCliente().getEstado());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getCep(), response.getCliente().getCep());
    }

    @DisplayName("Atualiza uma OS com Sucesso")
    @Test
    void atualizarOS() {
        when(ordemServicoRepository.save(any())).thenReturn(modelOrdemServico);
        when(utilClientes.pesquisarClientePeloId(anyLong())).thenReturn(modelClientes);

        ModelOrdemServico response = ordemServicoServiceImpl.
                atualizarOS(getDadosEstaticosOS.dadosOrdemServico().getId(), modelOrdemServicoDTO);

        assertNotNull(response);

        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getId(), response.getId());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getDefeito(), response.getDefeito());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getDescricao(), response.getDescricao());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getLaudo_tecnico(), response.getLaudo_tecnico());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getStatus(), response.getStatus());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getData_inicial(), response.getData_inicial());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getData_final(), response.getData_final());

        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getId(), response.getCliente().getId());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getNome(), response.getCliente().getNome());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getCpf(), response.getCliente().getCpf());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getCelular(), response.getCliente().getCelular());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getEmail(), response.getCliente().getEmail());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getEndereco(), response.getCliente().getEndereco());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getCidade(), response.getCliente().getCidade());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getEstado(), response.getCliente().getEstado());
        assertEquals(getDadosEstaticosOS.dadosOrdemServico().getCliente().getCep(), response.getCliente().getCep());
    }

    @DisplayName("Deleta uma OS com Sucesso")
    @Test
    void deletarOS() {
        doNothing().when(ordemServicoRepository).deleteById(anyLong());

        ordemServicoServiceImpl.deletarOS(getDadosEstaticosOS.dadosOrdemServico().getId());
        verify(ordemServicoRepository, times(1)).deleteById(anyLong());
    }

    private void startOrdemServico(){
        modelClientes = new ModelClientes(
                getDadosEstaticosOS.dadosClientes().getId(),
                getDadosEstaticosOS.dadosClientes().getNome(),
                getDadosEstaticosOS.dadosClientes().getCpf(),
                getDadosEstaticosOS.dadosClientes().getCelular(),
                getDadosEstaticosOS.dadosClientes().getEmail(),
                getDadosEstaticosOS.dadosClientes().getEndereco(),
                getDadosEstaticosOS.dadosClientes().getCidade(),
                getDadosEstaticosOS.dadosClientes().getEstado(),
                getDadosEstaticosOS.dadosClientes().getCep(),
                getDadosEstaticosOS.dadosClientes().getOrdemServicos()
        );
        modelClientesDTO = new ModelClientesDTO(
                getDadosEstaticosOS.dadosClientes().getId(),
                getDadosEstaticosOS.dadosClientes().getNome(),
                getDadosEstaticosOS.dadosClientes().getCpf(),
                getDadosEstaticosOS.dadosClientes().getCelular(),
                getDadosEstaticosOS.dadosClientes().getEmail(),
                getDadosEstaticosOS.dadosClientes().getEndereco(),
                getDadosEstaticosOS.dadosClientes().getCidade(),
                getDadosEstaticosOS.dadosClientes().getEstado(),
                getDadosEstaticosOS.dadosClientes().getCep()
        );
        modelOrdemServico = new ModelOrdemServico(
                getDadosEstaticosOS.dadosOrdemServico().getId(),
                getDadosEstaticosOS.dadosOrdemServico().getDefeito(),
                getDadosEstaticosOS.dadosOrdemServico().getDescricao(),
                getDadosEstaticosOS.dadosOrdemServico().getLaudo_tecnico(),
                getDadosEstaticosOS.dadosOrdemServico().getStatus(),
                getDadosEstaticosOS.dadosOrdemServico().getData_inicial(),
                getDadosEstaticosOS.dadosOrdemServico().getData_final(),
                getDadosEstaticosOS.dadosOrdemServico().getCliente()
        );
        modelOrdemServicoDTO = new ModelOrdemServicoDTO(
                getDadosEstaticosOS.dadosOrdemServico().getId(),
                getDadosEstaticosOS.dadosOrdemServico().getDefeito(),
                getDadosEstaticosOS.dadosOrdemServico().getDescricao(),
                getDadosEstaticosOS.dadosOrdemServico().getLaudo_tecnico(),
                getDadosEstaticosOS.dadosOrdemServico().getStatus(),
                getDadosEstaticosOS.dadosOrdemServico().getData_inicial(),
                getDadosEstaticosOS.dadosOrdemServico().getData_final(),
                getDadosEstaticosOS.dadosOrdemServico().getCliente());
        modelOrdemServicoOptional = Optional.of(new ModelOrdemServico(
                getDadosEstaticosOS.dadosOrdemServico().getId(),
                getDadosEstaticosOS.dadosOrdemServico().getDefeito(),
                getDadosEstaticosOS.dadosOrdemServico().getDescricao(),
                getDadosEstaticosOS.dadosOrdemServico().getLaudo_tecnico(),
                getDadosEstaticosOS.dadosOrdemServico().getStatus(),
                getDadosEstaticosOS.dadosOrdemServico().getData_inicial(),
                getDadosEstaticosOS.dadosOrdemServico().getData_final(),
                getDadosEstaticosOS.dadosOrdemServico().getCliente()
        ));
    }
}