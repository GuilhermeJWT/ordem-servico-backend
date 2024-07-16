package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.ModelVendasDTO;
import br.com.systemsgs.ordem_servico_backend.dto.VendasResponseDTO;
import br.com.systemsgs.ordem_servico_backend.exception.VendaNaoEncontradaException;
import br.com.systemsgs.ordem_servico_backend.model.ModelVendas;
import br.com.systemsgs.ordem_servico_backend.repository.VendasRepository;
import br.com.systemsgs.ordem_servico_backend.service.VendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ActiveProfiles(value = "test")
@SpringBootTest
class VendasControllerTest extends ConfigDadosEstaticosEntidades {

    private ModelVendas modelVendas;
    private ModelVendasDTO modelVendasDTO;
    private VendasResponseDTO vendasResponseDTO;

    @InjectMocks
    private VendasController vendasController;

    @Mock
    private VendaService vendaService;

    @Mock
    private VendasRepository vendasRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startVenda();
    }

    @DisplayName("Teste para salva uma Venda - retorna 201")
    @Test
    void salvarVenda() {
        when(vendaService.salvarVenda(any())).thenReturn(modelVendas);

        ResponseEntity<ModelVendas> response = vendasController.salvarVenda(modelVendasDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @DisplayName("Pesquisa uma Venda pelo ID - retorna 200")
    @Test
    void pesquisaVendaPorId(){
        when(vendaService.pesquisaVendaPorId(modelVendas.getIdVenda())).thenReturn(vendasResponseDTO);

        ResponseEntity<VendasResponseDTO> response = vendasController.pesquisarPorId(dadosVenda().getIdVenda());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(VendasResponseDTO.class, response.getBody().getClass());

        assertEquals(dadosVenda().getIdVenda(), response.getBody().getIdVenda());
        assertEquals(dadosVenda().getTotalItens(), response.getBody().getTotalItens());
        assertEquals(dadosVenda().getTotalVenda(), response.getBody().getTotalVenda());
        assertEquals(dadosVenda().getDataVenda(), response.getBody().getDataVenda());
        assertEquals(dadosVenda().getDesconto(), response.getBody().getDesconto());
        assertEquals(dadosVenda().getCliente().getNome(), response.getBody().getNomeCliente());
        assertEquals(dadosVenda().getTecnicoResponsavel().getNome(), response.getBody().getNomeTecnicoResponsavel());
        assertEquals(dadosDescricaoProdutos(), response.getBody().getDescricaoProdutos());

    }

    @DisplayName("Pesquisa uma Venda pelo ID inexistente - retorna 404")
    @Test
    void pesquisarPorIdInexistenteRetornaNotFound() {
        when(vendasRepository.findById(anyLong())).thenThrow(new VendaNaoEncontradaException());

        try{
            vendaService.pesquisaVendaPorId(dadosVenda().getIdVenda());
        }catch (Exception exception){
            assertEquals(VendaNaoEncontradaException.class, exception.getClass());
            assertEquals(mensagemErro().get(2), exception.getMessage());
        }
    }

    private void startVenda(){
        modelVendas = new ModelVendas(
           dadosVenda().getIdVenda(),
           dadosVenda().getDataVenda(),
           dadosVenda().getTotalVenda(),
           dadosVenda().getDesconto(),
           dadosVenda().getTotalItens(),
           dadosVenda().getCliente(),
           dadosVenda().getTecnicoResponsavel(),
           dadosVenda().getItens()
        );
        modelVendasDTO = new ModelVendasDTO(
           dadosVenda().getDesconto(),
           dadosVenda().getCliente().getId(),
           dadosVenda().getTecnicoResponsavel().getId(),
           dadosItensVendasDTO()
        );
        vendasResponseDTO = new VendasResponseDTO(
            dadosVenda().getIdVenda(),
            dadosVenda().getTotalItens(),
            dadosVenda().getTotalVenda(),
            dadosVenda().getDataVenda(),
            dadosVenda().getDesconto(),
            dadosVenda().getCliente().getNome(),
            dadosVenda().getTecnicoResponsavel().getNome(),
            dadosDescricaoProdutos()
        );
    }
}