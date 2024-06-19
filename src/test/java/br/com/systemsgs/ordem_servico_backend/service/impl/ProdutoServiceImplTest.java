package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.ModelProdutosDTO;
import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;
import br.com.systemsgs.ordem_servico_backend.repository.ProdutoRepository;
import br.com.systemsgs.ordem_servico_backend.util.UtilProdutos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProdutoServiceImplTest {

    public static final Long ID = 1L;
    public static final String DESCRICAO = "Notebook Gamer";
    public static final Integer QUANTIDADE = 5;
    public static final Integer QUANTIDADE_MINIMA = 1;
    public static final BigDecimal PRECO_COMPRA = BigDecimal.valueOf(1000L);
    public static final BigDecimal PRECO_VENDA = BigDecimal.valueOf(2000L);
    public static final String CODIGO_BARRAS = "789835741123";
    public static final Integer INDEX_0 = 0;
    public static final String RECURSO_NAO_ENCONTRADO = "Recurso não Encontrado!";

    private ModelProdutos modelProdutos;
    private ModelProdutosDTO modelProdutosDTO;
    private Optional<ModelProdutos> modelProdutosOptional;

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private UtilProdutos utilProdutos;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startProduto();
    }

    @DisplayName("Pesquisa um Produto por ID")
    @Test
    void pesquisaProdutoPorId() {
        when(produtoRepository.findById(ID)).thenReturn(modelProdutosOptional);

        ModelProdutos response = produtoService.pesquisaPorId(ID);

        assertNotNull(response);

        assertEquals(ID, response.getId());
        assertEquals(DESCRICAO, response.getDescricao());
        assertEquals(QUANTIDADE, response.getQuantidade());
        assertEquals(QUANTIDADE_MINIMA, response.getQuantidade_minima());
        assertEquals(PRECO_COMPRA, response.getPreco_compra());
        assertEquals(PRECO_VENDA, response.getPreco_venda());
        assertEquals(CODIGO_BARRAS, response.getCodigo_barras());
    }

    @DisplayName("Pesquisa um Produto e retorna 404")
    @Test
    void pesquisaProdutoInexistenteRetornaNotFound(){
        when(produtoRepository.findById(anyLong())).thenThrow(new RecursoNaoEncontradoException());

        try{
            produtoService.pesquisaPorId(ID);
        }catch (Exception exception){
            assertEquals(RecursoNaoEncontradoException.class, exception.getClass());
            assertEquals(RECURSO_NAO_ENCONTRADO, exception.getMessage());
        }
    }

    @DisplayName("Lista todos os Produtos")
    @Test
    void listarProdutos() {
        when(produtoRepository.findAll()).thenReturn(List.of(modelProdutos));

        List<ModelProdutos> response = produtoService.listarProdutos();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(ModelProdutos.class, response.get(INDEX_0).getClass());

        assertEquals(ID, response.get(INDEX_0).getId());
        assertEquals(DESCRICAO, response.get(INDEX_0).getDescricao());
        assertEquals(QUANTIDADE, response.get(INDEX_0).getQuantidade());
        assertEquals(QUANTIDADE_MINIMA, response.get(INDEX_0).getQuantidade_minima());
        assertEquals(PRECO_COMPRA, response.get(INDEX_0).getPreco_compra());
        assertEquals(PRECO_VENDA, response.get(INDEX_0).getPreco_venda());
        assertEquals(CODIGO_BARRAS, response.get(INDEX_0).getCodigo_barras());
    }

    @DisplayName("Deve salvar um Produto")
    @Test
    void deveSalvarProdutos() {
        when(produtoRepository.save(any())).thenReturn(modelProdutos);

        ModelProdutos response = produtoService.salvarProdutos(modelProdutosDTO);

        assertEquals(ID, response.getId());
        assertEquals(DESCRICAO, response.getDescricao());
        assertEquals(QUANTIDADE, response.getQuantidade());
        assertEquals(QUANTIDADE_MINIMA, response.getQuantidade_minima());
        assertEquals(PRECO_COMPRA, response.getPreco_compra());
        assertEquals(PRECO_VENDA, response.getPreco_venda());
        assertEquals(CODIGO_BARRAS, response.getCodigo_barras());
    }

    @DisplayName("Deleta um Produto com Sucesso")
    @Test
    void deletarProduto() {
        doNothing().when(produtoRepository).deleteById(anyLong());

        produtoService.deletarProduto(ID);
        verify(produtoRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void atualizarProduto() {
        when(produtoRepository.save(any())).thenReturn(modelProdutos);
        when(utilProdutos.pesquisaProdutoPorId(anyLong())).thenReturn(modelProdutos);

        ModelProdutos response = produtoService.atualizarProduto(ID, modelProdutosDTO);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(DESCRICAO, response.getDescricao());
        assertEquals(QUANTIDADE, response.getQuantidade());
        assertEquals(QUANTIDADE_MINIMA, response.getQuantidade_minima());
        assertEquals(PRECO_COMPRA, response.getPreco_compra());
        assertEquals(PRECO_VENDA, response.getPreco_venda());
        assertEquals(CODIGO_BARRAS, response.getCodigo_barras());
    }

    private void startProduto(){
        modelProdutos = new ModelProdutos(ID, DESCRICAO, QUANTIDADE, QUANTIDADE_MINIMA, PRECO_COMPRA, PRECO_VENDA, CODIGO_BARRAS);
        modelProdutosDTO = new ModelProdutosDTO(ID, DESCRICAO, QUANTIDADE, QUANTIDADE_MINIMA, PRECO_COMPRA, PRECO_VENDA, CODIGO_BARRAS);
        modelProdutosOptional = Optional.of(new ModelProdutos(ID, DESCRICAO, QUANTIDADE, QUANTIDADE_MINIMA, PRECO_COMPRA, PRECO_VENDA, CODIGO_BARRAS));
    }
}