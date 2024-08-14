package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ActiveProfiles(value = "test")
@SpringBootTest
class UtilProdutosTest extends ConfigDadosEstaticosEntidades{

    private Optional<ModelProdutos> modelProdutosOptional;
    private List<ModelProdutos> listModelProdutos;

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

    @DisplayName("Pesquisa um Produto por ID e retorna a Entidade para Validação")
    @Test
    void pesquisarProdutoPeloId() {
        when(produtoRepository.findById(anyLong())).thenReturn(modelProdutosOptional);

        ModelProdutos response = utilProdutos.pesquisaProdutoPorId(dadosProdutos().getId());

        assertNotNull(response);

        assertEquals(dadosProdutos().getId(), response.getId());
        assertEquals(dadosProdutos().getDescricao(), response.getDescricao());
        assertEquals(dadosProdutos().getQuantidade(), response.getQuantidade());
        assertEquals(dadosProdutos().getQuantidade_minima(), response.getQuantidade_minima());
        assertEquals(dadosProdutos().getPreco_compra(), response.getPreco_compra());
        assertEquals(dadosProdutos().getPreco_venda(), response.getPreco_venda());
        assertEquals(dadosProdutos().getCodigo_barras(), response.getCodigo_barras());
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
        assertEquals(dadosProdutos().getQuantidade_minima(), response.get(0).getQuantidade_minima());
        assertEquals(dadosProdutos().getPreco_compra(), response.get(0).getPreco_compra());
        assertEquals(dadosProdutos().getPreco_venda(), response.get(0).getPreco_venda());
        assertEquals(dadosProdutos().getCodigo_barras(), response.get(0).getCodigo_barras());
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
        modelProdutosOptional = Optional.of(new ModelProdutos(
                dadosProdutos().getId(),
                dadosProdutos().getDescricao(),
                dadosProdutos().getQuantidade(),
                dadosProdutos().getQuantidade_minima(),
                dadosProdutos().getPreco_compra(),
                dadosProdutos().getPreco_venda(),
                dadosProdutos().getCodigo_barras()
        ));
        listModelProdutos = Arrays.asList(
                dadosProdutos()
        );
    }
}