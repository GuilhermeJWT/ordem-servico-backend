package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.response.VendasResponse;
import br.com.systemsgs.ordem_servico_backend.exception.errors.VendaNaoEncontradaException;
import br.com.systemsgs.ordem_servico_backend.model.ModelVendas;
import br.com.systemsgs.ordem_servico_backend.repository.VendasRepository;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import br.com.systemsgs.ordem_servico_backend.util.UtilProdutos;
import br.com.systemsgs.ordem_servico_backend.util.UtilTecnicoResponsavel;
import br.com.systemsgs.ordem_servico_backend.util.UtilVendas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class VendasServiceImplTest extends ConfigDadosEstaticosEntidades {

    @InjectMocks
    private VendasServiceImpl vendasService;

    @Mock
    private UtilVendas utilVendas;

    @Mock
    private VendasRepository vendasRepository;

    @Mock
    private UtilClientes utilClientes;

    @Mock
    private UtilProdutos utilProdutos;

    @Mock
    private UtilTecnicoResponsavel utilTecnicoResponsavel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Teste para Pesquisar uma Venda pelo ID")
    @Test
    void pesquisaVendaPorId() {
        ModelVendas modelVendas = dadosVenda();

        when(utilVendas.pesquisarVendaPeloId(dadosVenda().getIdVenda())).thenReturn(modelVendas);

        VendasResponse result = vendasService.pesquisaVendaPorId(dadosVenda().getIdVenda());

        verify(utilVendas, times(1)).pesquisarVendaPeloId(dadosVenda().getIdVenda());
        assertEquals(dadosVenda().getIdVenda(), result.getIdVenda());
    }

    @DisplayName("Teste para Pesquisar uma Venda pelo ID Inexistente - 404")
    @Test
    void testPesquisaVendaPorId_VendaNaoEncontradaException() {
        when(utilVendas.pesquisarVendaPeloId(0L)).thenThrow(new VendaNaoEncontradaException());

        assertThrows(VendaNaoEncontradaException.class, () -> vendasService.pesquisaVendaPorId(0L));
    }
}