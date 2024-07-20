package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.ModelClientesDTO;
import br.com.systemsgs.ordem_servico_backend.dto.ModelOrdemServicoDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.model.ModelTecnicoResponsavel;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import br.com.systemsgs.ordem_servico_backend.repository.OrdemServicoRepository;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import br.com.systemsgs.ordem_servico_backend.util.UtilOrdemServico;
import br.com.systemsgs.ordem_servico_backend.util.UtilTecnicoResponsavel;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ActiveProfiles(value = "test")
@SpringBootTest
class OrdemServicoServiceImplTest extends ConfigDadosEstaticosEntidades{

    private ModelOrdemServico modelOrdemServico;
    private ModelOrdemServicoDTO modelOrdemServicoDTO;
    private Optional<ModelOrdemServico> modelOrdemServicoOptional;
    private ModelClientes modelClientes;
    private ModelClientesDTO modelClientesDTO;
    private ModelTecnicoResponsavel modelTecnicoResponsavel;

    @InjectMocks
    private OrdemServicoServiceImpl ordemServicoServiceImpl;

    @Mock
    private OrdemServicoRepository ordemServicoRepository;

    @Mock
    private UtilOrdemServico utilOrdemServico;

    @Mock
    private UtilClientes utilClientes;

    @Mock
    private UtilTecnicoResponsavel utilTecnicoResponsavel;

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

        ModelOrdemServico response = ordemServicoServiceImpl.pesquisaPorId(dadosOrdemServico().getId());

        assertNotNull(response);
        assertEquals(dadosOrdemServico().getId(), response.getId());
        assertEquals(dadosOrdemServico().getDefeito(), response.getDefeito());
        assertEquals(dadosOrdemServico().getDescricao(), response.getDescricao());
        assertEquals(dadosOrdemServico().getLaudo_tecnico(), response.getLaudo_tecnico());
        assertEquals(dadosOrdemServico().getStatus(), response.getStatus());
        assertEquals(dadosOrdemServico().getData_inicial(), response.getData_inicial());
        assertEquals(dadosOrdemServico().getData_final(), response.getData_final());

        assertEquals(dadosOrdemServico().getCliente().getId(), response.getCliente().getId());
        assertEquals(dadosOrdemServico().getCliente().getNome(), response.getCliente().getNome());
        assertEquals(dadosOrdemServico().getCliente().getCpf(), response.getCliente().getCpf());
        assertEquals(dadosOrdemServico().getCliente().getCelular(), response.getCliente().getCelular());
        assertEquals(dadosOrdemServico().getCliente().getEmail(), response.getCliente().getEmail());

        assertEquals(dadosOrdemServico().getCliente().getEndereco().getEndereco(), response.getCliente().getEndereco().getEndereco());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getComplemento(), response.getCliente().getEndereco().getComplemento());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getCidade(), response.getCliente().getEndereco().getCidade());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getEstado(), response.getCliente().getEndereco().getEstado());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getCep(), response.getCliente().getEndereco().getCep());

        assertEquals(dadosOrdemServico().getTecnicoResponsavel().getId(), response.getTecnicoResponsavel().getId());
        assertEquals(dadosOrdemServico().getTecnicoResponsavel().getNome(), response.getTecnicoResponsavel().getNome());
    }

    @DisplayName("Pesquisa uma OS por ID")
    @Test
    void pesquisaOSInexistenteRetornaNotFound(){
        when(ordemServicoRepository.findById(anyLong())).thenThrow(new RecursoNaoEncontradoException());

        try{
            ordemServicoServiceImpl.pesquisaPorId(dadosOrdemServico().getId());
        }catch (Exception exception){
            assertEquals(RecursoNaoEncontradoException.class, exception.getClass());
            assertEquals(mensagemErro().get(1), exception.getMessage());
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

        assertEquals(dadosOrdemServico().getId(), response.get(0).getId());
        assertEquals(dadosOrdemServico().getDefeito(), response.get(0).getDefeito());
        assertEquals(dadosOrdemServico().getDescricao(), response.get(0).getDescricao());
        assertEquals(dadosOrdemServico().getLaudo_tecnico(), response.get(0).getLaudo_tecnico());
        assertEquals(dadosOrdemServico().getStatus(), response.get(0).getStatus());
        assertEquals(dadosOrdemServico().getData_inicial(), response.get(0).getData_inicial());
        assertEquals(dadosOrdemServico().getData_final(), response.get(0).getData_final());

        assertEquals(dadosOrdemServico().getCliente().getId(), response.get(0).getCliente().getId());
        assertEquals(dadosOrdemServico().getCliente().getNome(), response.get(0).getCliente().getNome());
        assertEquals(dadosOrdemServico().getCliente().getCpf(), response.get(0).getCliente().getCpf());
        assertEquals(dadosOrdemServico().getCliente().getCelular(), response.get(0).getCliente().getCelular());
        assertEquals(dadosOrdemServico().getCliente().getEmail(), response.get(0).getCliente().getEmail());

        assertEquals(dadosOrdemServico().getCliente().getEndereco().getEndereco(), response.get(0).getCliente().getEndereco().getEndereco());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getComplemento(), response.get(0).getCliente().getEndereco().getComplemento());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getCidade(), response.get(0).getCliente().getEndereco().getCidade());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getEstado(), response.get(0).getCliente().getEndereco().getEstado());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getCep(), response.get(0).getCliente().getEndereco().getCep());

        assertEquals(dadosOrdemServico().getTecnicoResponsavel().getId(), response.get(0).getTecnicoResponsavel().getId());
        assertEquals(dadosOrdemServico().getTecnicoResponsavel().getNome(), response.get(0).getTecnicoResponsavel().getNome());
    }

    @DisplayName("Valida um Cliente antes de Salvar")
    @Test
    void validaClienteAntesSalvar() {
        when(utilClientes.pesquisarClientePeloId(anyLong())).thenReturn(modelClientes);

        assertNotNull(modelClientesDTO.getClass());

        assertEquals(dadosClientes().getId(), modelClientesDTO.getId());
        assertEquals(dadosClientes().getNome(), modelClientesDTO.getNome());
        assertEquals(dadosClientes().getCelular(), modelClientesDTO.getCelular());
        assertEquals(dadosClientes().getCpf(), modelClientesDTO.getCpf());
        assertEquals(dadosClientes().getEmail(), modelClientesDTO.getEmail());
        assertEquals(dadosClientes().getEndereco().getEndereco(), modelClientesDTO.getEndereco());
        assertEquals(dadosClientes().getEndereco().getComplemento(), modelClientesDTO.getComplemento());
        assertEquals(dadosClientes().getEndereco().getCidade(), modelClientesDTO.getCidade());
        assertEquals(dadosClientes().getEndereco().getEstado(), modelClientesDTO.getEstado());
        assertEquals(dadosClientes().getEndereco().getCep(), modelClientesDTO.getCep());

    }

    @DisplayName("Salva com OS com Sucesso")
    @Test
    void salvarOS() {
        when(utilClientes.pesquisarClientePeloId(anyLong())).thenReturn(modelClientes);
        when(utilTecnicoResponsavel.validaTecnicoExistente(modelOrdemServicoDTO)).thenReturn(modelOrdemServicoDTO);
        when(ordemServicoRepository.save(any())).thenReturn(modelOrdemServico);

        ModelOrdemServico response = ordemServicoServiceImpl.salvarOS(modelOrdemServicoDTO);

        assertEquals(dadosOrdemServico().getId(), response.getId());
        assertEquals(dadosOrdemServico().getDefeito(), response.getDefeito());
        assertEquals(dadosOrdemServico().getDescricao(), response.getDescricao());
        assertEquals(dadosOrdemServico().getLaudo_tecnico(), response.getLaudo_tecnico());
        assertEquals(dadosOrdemServico().getStatus(), response.getStatus());
        assertEquals(dadosOrdemServico().getData_inicial(), response.getData_inicial());
        assertEquals(dadosOrdemServico().getData_final(), response.getData_final());

        assertEquals(dadosOrdemServico().getCliente().getId(), response.getCliente().getId());
        assertEquals(dadosOrdemServico().getCliente().getNome(), response.getCliente().getNome());
        assertEquals(dadosOrdemServico().getCliente().getCpf(), response.getCliente().getCpf());
        assertEquals(dadosOrdemServico().getCliente().getCelular(), response.getCliente().getCelular());
        assertEquals(dadosOrdemServico().getCliente().getEmail(), response.getCliente().getEmail());

        assertEquals(dadosOrdemServico().getCliente().getEndereco().getEndereco(), response.getCliente().getEndereco().getEndereco());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getComplemento(), response.getCliente().getEndereco().getComplemento());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getCidade(), response.getCliente().getEndereco().getCidade());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getEstado(), response.getCliente().getEndereco().getEstado());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getCep(), response.getCliente().getEndereco().getCep());

        assertEquals(dadosOrdemServico().getTecnicoResponsavel().getId(), response.getTecnicoResponsavel().getId());
        assertEquals(dadosOrdemServico().getTecnicoResponsavel().getNome(), response.getTecnicoResponsavel().getNome());
    }

    @DisplayName("Atualiza uma OS com Sucesso")
    @Test
    void atualizarOS() {
        when(ordemServicoRepository.save(any())).thenReturn(modelOrdemServico);
        when(utilClientes.pesquisarClientePeloId(anyLong())).thenReturn(modelClientes);

        ModelOrdemServico response = ordemServicoServiceImpl.
                atualizarOS(dadosOrdemServico().getId(), modelOrdemServicoDTO);

        assertNotNull(response);

        assertEquals(dadosOrdemServico().getId(), response.getId());
        assertEquals(dadosOrdemServico().getDefeito(), response.getDefeito());
        assertEquals(dadosOrdemServico().getDescricao(), response.getDescricao());
        assertEquals(dadosOrdemServico().getLaudo_tecnico(), response.getLaudo_tecnico());
        assertEquals(dadosOrdemServico().getStatus(), response.getStatus());
        assertEquals(dadosOrdemServico().getData_inicial(), response.getData_inicial());
        assertEquals(dadosOrdemServico().getData_final(), response.getData_final());

        assertEquals(dadosOrdemServico().getCliente().getId(), response.getCliente().getId());
        assertEquals(dadosOrdemServico().getCliente().getNome(), response.getCliente().getNome());
        assertEquals(dadosOrdemServico().getCliente().getCpf(), response.getCliente().getCpf());
        assertEquals(dadosOrdemServico().getCliente().getCelular(), response.getCliente().getCelular());
        assertEquals(dadosOrdemServico().getCliente().getEmail(), response.getCliente().getEmail());

        assertEquals(dadosOrdemServico().getCliente().getEndereco().getEndereco(), response.getCliente().getEndereco().getEndereco());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getComplemento(), response.getCliente().getEndereco().getComplemento());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getCidade(), response.getCliente().getEndereco().getCidade());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getEstado(), response.getCliente().getEndereco().getEstado());
        assertEquals(dadosOrdemServico().getCliente().getEndereco().getCep(), response.getCliente().getEndereco().getCep());

        assertEquals(dadosOrdemServico().getTecnicoResponsavel().getId(), response.getTecnicoResponsavel().getId());
        assertEquals(dadosOrdemServico().getTecnicoResponsavel().getNome(), response.getTecnicoResponsavel().getNome());
    }

    @DisplayName("Deleta uma OS com Sucesso")
    @Test
    void deletarOS() {
        doNothing().when(ordemServicoRepository).deleteById(anyLong());

        ordemServicoServiceImpl.deletarOS(dadosOrdemServico().getId());
        verify(ordemServicoRepository, times(1)).deleteById(anyLong());
    }

    private void startOrdemServico(){
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
        modelOrdemServico = new ModelOrdemServico(
                dadosOrdemServico().getId(),
                dadosOrdemServico().getDefeito(),
                dadosOrdemServico().getDescricao(),
                dadosOrdemServico().getLaudo_tecnico(),
                dadosOrdemServico().getStatus(),
                dadosOrdemServico().getData_inicial(),
                dadosOrdemServico().getData_final(),
                dadosOrdemServico().getCliente(),
                dadosOrdemServico().getTecnicoResponsavel()
        );
        modelOrdemServicoDTO = new ModelOrdemServicoDTO(
                dadosOrdemServico().getId(),
                dadosOrdemServico().getDefeito(),
                dadosOrdemServico().getDescricao(),
                dadosOrdemServico().getLaudo_tecnico(),
                dadosOrdemServico().getStatus(),
                dadosOrdemServico().getData_inicial(),
                dadosOrdemServico().getData_final(),
                dadosOrdemServico().getCliente(),
                dadosOrdemServico().getTecnicoResponsavel());

        modelOrdemServicoOptional = Optional.of(new ModelOrdemServico(
                dadosOrdemServico().getId(),
                dadosOrdemServico().getDefeito(),
                dadosOrdemServico().getDescricao(),
                dadosOrdemServico().getLaudo_tecnico(),
                dadosOrdemServico().getStatus(),
                dadosOrdemServico().getData_inicial(),
                dadosOrdemServico().getData_final(),
                dadosOrdemServico().getCliente(),
                dadosOrdemServico().getTecnicoResponsavel()
        ));
        modelTecnicoResponsavel = new ModelTecnicoResponsavel(
                dadosTecnicoResponsavel().getId(),
                dadosTecnicoResponsavel().getNome()
        );
    }
}