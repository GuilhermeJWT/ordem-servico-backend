package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
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
import static org.mockito.Mockito.when;

@ActiveProfiles(value = "test")
@SpringBootTest
class UtilTecnicoResponsavelTest extends ConfigDadosEstaticosEntidades {

    private Optional<ModelTecnicoResponsavel> modelTecnicoResponsavelOptional;

    @InjectMocks
    private UtilTecnicoResponsavel utilTecnicoResponsavel;

    @Mock
    private TecnicoRepository tecnicoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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

    private void startTecnicoResponsavel(){
        modelTecnicoResponsavelOptional = Optional.of(new ModelTecnicoResponsavel(
            dadosTecnicoResponsavel().getId(),
            dadosTecnicoResponsavel().getNome()
        ));
    }
}