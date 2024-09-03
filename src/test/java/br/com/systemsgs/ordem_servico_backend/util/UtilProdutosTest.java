package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelVendasDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;
import br.com.systemsgs.ordem_servico_backend.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class UtilProdutosTest extends ConfigDadosEstaticosEntidades{

    private ModelProdutos modelProdutos;
    private ModelVendasDTO modelVendasDTO;
    private Optional<ModelProdutos> modelProdutosOptional;
    private List<ModelProdutos> listModelProdutos;
    private ModelVendasDTO modelVendasDTOEmpty = new ModelVendasDTO();

    @InjectMocks
    private UtilProdutos utilProdutos;

    @Mock
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        utilProdutos = new UtilProdutos(produtoRepository);
        startProdutoOptional();
    }

    @DisplayName("Teste para realizar Baixa no Estoque dos produtos durante uma Venda")
    @Test
    void testBaixaEstoqueProdutosDadosValidos() {
        when(produtoRepository.findAllById(Arrays.asList(modelProdutos.getId())))
                .thenReturn(Arrays.asList(modelProdutos));

        utilProdutos.baixaEstoqueProdutos(modelVendasDTO);

        assertEquals(0, modelProdutos.getQuantidade());

        verify(produtoRepository).save(modelProdutos);
    }

    @DisplayName("Teste para não chamar o método de Salva o Produto, para baixa no Estoque")
    @Test
    void testBaixaEstoqueProdutosListaVazia() {
        when(produtoRepository.findAllById(Arrays.asList(1L, 2L)))
                .thenReturn(Collections.emptyList());

        utilProdutos.baixaEstoqueProdutos(modelVendasDTOEmpty);

        verify(produtoRepository, never()).save(modelProdutos);
    }

    @DisplayName("Pesquisa um Produto por ID e retorna a Entidade para Validação")
    @Test
    void pesquisarProdutoPeloId() {
        when(produtoRepository.findById(anyLong())).thenReturn(modelProdutosOptional);

        ModelProdutos response = utilProdutos.pesquisaProdutoPorId(dadosProdutos().getId());

        assertNotNull(response);

        assertEquals(dadosProdutos().getId(), response.getId());
        assertEquals(dadosProdutos().getDescricao(), response.getDescricao());
        assertEquals(dadosProdutos().getQuantidade(), response.getQuantidade());
        assertEquals(dadosProdutos().getQuantidadeMinima(), response.getQuantidadeMinima());
        assertEquals(dadosProdutos().getPrecoCompra(), response.getPrecoCompra());
        assertEquals(dadosProdutos().getPrecoVenda(), response.getPrecoVenda());
        assertEquals(dadosProdutos().getCodigoBarras(), response.getCodigoBarras());
    }

    @DisplayName("Pesquisa um Produto por ID e retorna Not Found")
    @Test
    void pesquisaProdutoInexistenteRetornaNotFound(){
        when(produtoRepository.findById(anyLong())).thenThrow(new RecursoNaoEncontradoException());

        try{
            utilProdutos.pesquisaProdutoPorId(dadosProdutos().getId());
        }catch (Exception exception){
            assertEquals(RecursoNaoEncontradoException.class, exception.getClass());
            assertEquals(mensagemErro().get(1), exception.getMessage());
        }
    }

    @DisplayName("Pesquisa lista de Produtos com uma lista de IDs")
    @Test
    void testPesquisaListaProdutosPorIds(){
        when(produtoRepository.findAllById(dadosIds())).thenReturn(listModelProdutos);

        List<ModelProdutos> response = utilProdutos.pesquisaListaProdutosPorIds(dadosIds());

        assertNotNull(response);

        assertEquals(dadosProdutos().getId(), response.get(0).getId());
        assertEquals(dadosProdutos().getDescricao(), response.get(0).getDescricao());
        assertEquals(dadosProdutos().getQuantidade(), response.get(0).getQuantidade());
        assertEquals(dadosProdutos().getQuantidadeMinima(), response.get(0).getQuantidadeMinima());
        assertEquals(dadosProdutos().getPrecoCompra(), response.get(0).getPrecoCompra());
        assertEquals(dadosProdutos().getPrecoVenda(), response.get(0).getPrecoVenda());
        assertEquals(dadosProdutos().getCodigoBarras(), response.get(0).getCodigoBarras());
    }

    @DisplayName("Pesquisa a descrição dos produtos por IDs")
    @Test
    void testPesquisaDescricaoProdutos(){
        when(produtoRepository.findAllById(dadosIds())).thenReturn(listModelProdutos);

        List<String> response = utilProdutos.pesquisaDescricaoProdutosPorIds(dadosIds());

        assertNotNull(response);

        assertEquals(dadosDescricaoProdutos().get(0), response.get(0));
    }

    @DisplayName("Teste para somar o Estoque atual dos Produtos para o Dashboard")
    @Test
    void testsomaEstoqueAtualProdutos(){
        when(produtoRepository.somaEstoqueAtual()).
                thenReturn(dadosDashboard().getQuantidadeProdutosEstoqueAtual());

        Optional<Integer> response = utilProdutos.somaEstoqueAtualProdutos();

        assertNotNull(response);
        assertTrue(response.isPresent());
        assertEquals(response, dadosDashboard().getQuantidadeProdutosEstoqueAtual());
    }

    @DisplayName("Teste para retornar NullPointerException caso não tenha dados")
    @Test
    void testsomaEstoqueAtualProdutosNullPointerException() {
        when(produtoRepository.somaEstoqueAtual()).thenThrow(new NullPointerException());

        try {
            var response = utilProdutos.somaEstoqueAtualProdutos();
        }catch (NullPointerException exception){
            assertEquals(NullPointerException.class, exception.getClass());
        }
    }

    private void startProdutoOptional(){
        modelProdutos = new ModelProdutos(
                dadosProdutos().getId(),
                dadosProdutos().getDescricao(),
                dadosProdutos().getQuantidade(),
                dadosProdutos().getQuantidadeMinima(),
                dadosProdutos().getPrecoCompra(),
                dadosProdutos().getPrecoVenda(),
                dadosProdutos().getCodigoBarras()
        );
        modelProdutosOptional = Optional.of(new ModelProdutos(
                dadosProdutos().getId(),
                dadosProdutos().getDescricao(),
                dadosProdutos().getQuantidade(),
                dadosProdutos().getQuantidadeMinima(),
                dadosProdutos().getPrecoCompra(),
                dadosProdutos().getPrecoVenda(),
                dadosProdutos().getCodigoBarras()
        ));
        listModelProdutos = Arrays.asList(
                dadosProdutos()
        );
        modelVendasDTO = new ModelVendasDTO(
                dadosVenda().getDesconto(),
                dadosClientes().getId(),
                dadosTecnicoResponsavel().getId(),
                dadosItensVendasDTO()
        );
    }
}