package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.exception.VendaNaoEncontradaException;
import br.com.systemsgs.ordem_servico_backend.model.ModelVendas;
import br.com.systemsgs.ordem_servico_backend.repository.VendasRepository;
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
class UtilVendasTest extends ConfigDadosEstaticosEntidades {

    private Optional<ModelVendas> modelVendasOptional;

    @InjectMocks
    private UtilVendas utilVendas;

    @Mock
    private VendasRepository vendasRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startVendaOptional();
    }

    @DisplayName("Pesquisa uma Venda por ID")
    @Test
    void pesquisarVendaPeloId() {
        when(vendasRepository.findById(anyLong())).thenReturn(modelVendasOptional);

        ModelVendas response = utilVendas.pesquisarVendaPeloId(dadosVenda().getIdVenda());

        assertNotNull(response);

        assertEquals(dadosVenda().getIdVenda(), response.getIdVenda());
        assertEquals(dadosVenda().getTotalItens(), response.getTotalItens());
        assertEquals(dadosVenda().getTotalVenda(), response.getTotalVenda());
        assertEquals(dadosVenda().getDataVenda(), response.getDataVenda());
        assertEquals(dadosVenda().getDesconto(), response.getDesconto());
        assertEquals(dadosVenda().getCliente().getNome(), response.getCliente().getNome());
        assertEquals(dadosVenda().getTecnicoResponsavel().getNome(), response.getTecnicoResponsavel().getNome());
        assertEquals(dadosVenda().getItens(), response.getItens());
    }

    @DisplayName("Pesquisa uma Venda Inexistente por ID - retorna Not Found")
    @Test
    void pesquisaVendaInexistenteRetornaNotFound(){
        when(vendasRepository.findById(anyLong())).thenThrow(new VendaNaoEncontradaException());

        try{
            utilVendas.pesquisarVendaPeloId(dadosVenda().getIdVenda());
        }catch (Exception exception){
            assertEquals(VendaNaoEncontradaException.class, exception.getClass());
            assertEquals(mensagemErro().get(2), exception.getMessage());
        }
    }

    private void startVendaOptional(){
        modelVendasOptional = Optional.of(new ModelVendas(
                dadosVenda().getIdVenda(),
                dadosVenda().getDataVenda(),
                dadosVenda().getTotalVenda(),
                dadosVenda().getDesconto(),
                dadosVenda().getTotalItens(),
                dadosVenda().getCliente(),
                dadosVenda().getTecnicoResponsavel(),
                dadosVenda().getItens()
        ));
    }
}