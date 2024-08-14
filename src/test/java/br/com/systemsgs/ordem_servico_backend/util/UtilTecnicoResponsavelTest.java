package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelOrdemServicoDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.TecnicoResponsavelNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelTecnicoResponsavel;
import br.com.systemsgs.ordem_servico_backend.repository.TecnicoRepository;
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
class UtilTecnicoResponsavelTest extends ConfigDadosEstaticosEntidades {

    private Optional<ModelTecnicoResponsavel> modelTecnicoResponsavelOptional;
    private ModelOrdemServicoDTO modelOrdemServicoDTO;

    @InjectMocks
    private UtilTecnicoResponsavel utilTecnicoResponsavel;

    @Mock
    private TecnicoRepository tecnicoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        utilTecnicoResponsavel = new UtilTecnicoResponsavel(tecnicoRepository);
        startTecnicoResponsavel();
    }

    @DisplayName("Pesquisa um Técnico por ID")
    @Test
    void pesquisarTecnicoPeloId() {
        when(tecnicoRepository.findById(anyLong())).thenReturn(modelTecnicoResponsavelOptional);

        ModelTecnicoResponsavel response = utilTecnicoResponsavel.pesquisarTecnicoPeloId(dadosTecnicoResponsavel().getId());

        assertEquals(dadosTecnicoResponsavel().getId(), response.getId());
        assertEquals(dadosTecnicoResponsavel().getNome(), response.getNome());
    }

    @DisplayName("Pesquisa um Técnico Inexistente por ID - retorna Not Found")
    @Test
    void pesquisaTecnicoInexistenteRetornaNotFound(){
        when(tecnicoRepository.findById(anyLong())).thenThrow(new TecnicoResponsavelNaoEncontradoException());

        try{
            utilTecnicoResponsavel.pesquisarTecnicoPeloId(dadosTecnicoResponsavel().getId());
        }catch (Exception exception){
            assertEquals(TecnicoResponsavelNaoEncontradoException.class, exception.getClass());
            assertEquals(mensagemErro().get(3), exception.getMessage());
        }
    }

    @DisplayName("Pesquisa Técnico pelo ID - Optional")
    @Test
     void testPesquisarTecnicoPeloIdOptional() {
        when(tecnicoRepository.findById(modelTecnicoResponsavelOptional.get().getId())).thenReturn(modelTecnicoResponsavelOptional);

        ModelTecnicoResponsavel response = utilTecnicoResponsavel.pesquisarTecnicoPeloId(1L);

        assertNotNull(response);

        assertEquals(dadosTecnicoResponsavel().getId(), response.getId());
        assertEquals(dadosTecnicoResponsavel().getNome(), response.getNome());

        verify(tecnicoRepository, times(1)).findById(modelTecnicoResponsavelOptional.get().getId());
    }

    @DisplayName("Teste para validar um Técnico existente")
    @Test
     void testValidaTecnicoExistente() {
        when(tecnicoRepository.findById(modelTecnicoResponsavelOptional.get().getId())).thenReturn(modelTecnicoResponsavelOptional);

        ModelOrdemServicoDTO response = utilTecnicoResponsavel.validaTecnicoExistente(modelOrdemServicoDTO);

        assertNotNull(response);
        assertEquals(dadosTecnicoResponsavel().getId(), response.getTecnicoResponsavel().getId());
        assertEquals(dadosTecnicoResponsavel().getNome(), response.getTecnicoResponsavel().getNome());

        verify(tecnicoRepository, times(1)).findById(1L);
    }

    @DisplayName("Teste lançar exception caso não existe um Técnico para validação")
    @Test
    public void testValidaTecnicoExistenteNotFound() {
        when(tecnicoRepository.findById(modelTecnicoResponsavelOptional.get().getId())).thenReturn(Optional.empty());

        assertThrows(TecnicoResponsavelNaoEncontradoException.class, () -> {
            utilTecnicoResponsavel.validaTecnicoExistente(modelOrdemServicoDTO);
        });

        verify(tecnicoRepository, times(1)).findById(1L);
    }

    private void startTecnicoResponsavel(){
        modelTecnicoResponsavelOptional = Optional.of(new ModelTecnicoResponsavel(
            dadosTecnicoResponsavel().getId(),
            dadosTecnicoResponsavel().getNome()
        ));
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
    }
}