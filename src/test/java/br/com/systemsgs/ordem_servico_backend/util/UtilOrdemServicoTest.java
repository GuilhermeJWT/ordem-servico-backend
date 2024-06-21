package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.repository.OrdemServicoRepository;
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
import static org.mockito.Mockito.when;

@SpringBootTest
class UtilOrdemServicoTest {

    private ConfigDadosEstaticosEntidades getDadosEstaticosOS = new ConfigDadosEstaticosEntidades();

    private Optional<ModelOrdemServico> modelOrdemServicoOptional;

    @InjectMocks
    private UtilOrdemServico utilOrdemServico;

    @Mock
    private OrdemServicoRepository ordemServicoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startOrdemServicoOptinal();
    }

    @DisplayName("Pesquisa uma OS por ID e retorna a Entidade para Validação")
    @Test
    void pesquisarOSPeloId() {
        when(ordemServicoRepository.findById(anyLong())).thenReturn(modelOrdemServicoOptional);

        ModelOrdemServico response = utilOrdemServico.pesquisaOsPorId(getDadosEstaticosOS.dadosOrdemServico().getId());

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

    @DisplayName("Pesquisa uma OS por ID e retorna Not Found")
    @Test
    void pesquisaOSInexistenteRetornaNotFound(){
        when(ordemServicoRepository.findById(anyLong())).thenThrow(new RecursoNaoEncontradoException());

        try{
            utilOrdemServico.pesquisaOsPorId(getDadosEstaticosOS.dadosOrdemServico().getId());
        }catch (Exception exception){
            assertEquals(RecursoNaoEncontradoException.class, exception.getClass());
            assertEquals(getDadosEstaticosOS.mensagemErro().get(1), exception.getMessage());
        }
    }

    private void startOrdemServicoOptinal(){
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