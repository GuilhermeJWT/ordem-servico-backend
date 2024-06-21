package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;
import br.com.systemsgs.ordem_servico_backend.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class UtilProdutosTest {

    public static final Long ID = 1L;
    public static final String DESCRICAO = "Notebook Gamer";
    public static final Integer QUANTIDADE = 5;
    public static final Integer QUANTIDADE_MINIMA = 1;
    public static final BigDecimal PRECO_COMPRA = BigDecimal.valueOf(1000L);
    public static final BigDecimal PRECO_VENDA = BigDecimal.valueOf(2000L);
    public static final String CODIGO_BARRAS = "789835741123";
    public static final String RECURSO_NAO_ENCONTRADO = "Recurso não Encontrado!";

    private ModelProdutos modelProdutos;
    private Optional<ModelProdutos> modelProdutosOptional;

    @InjectMocks
    private UtilProdutos utilProdutos;

    @Mock
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startProduto();
    }

    @DisplayName("Pesquisa um Produto por ID e retorna a Entidade para Validação")
    @Test
    void pesquisarProdutoPeloId() {
        when(produtoRepository.findById(anyLong())).thenReturn(modelProdutosOptional);

        ModelProdutos response = utilProdutos.pesquisaProdutoPorId(ID);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(DESCRICAO, response.getDescricao());
        assertEquals(QUANTIDADE, response.getQuantidade());
        assertEquals(QUANTIDADE_MINIMA, response.getQuantidade_minima());
        assertEquals(PRECO_COMPRA, response.getPreco_compra());
        assertEquals(PRECO_VENDA, response.getPreco_venda());
        assertEquals(CODIGO_BARRAS, response.getCodigo_barras());
    }

    @DisplayName("Pesquisa um Produto por ID e retorna Not Found")
    @Test
    void pesquisaProdutoInexistenteRetornaNotFound(){
        when(produtoRepository.findById(anyLong())).thenThrow(new RecursoNaoEncontradoException());

        try{
            utilProdutos.pesquisaProdutoPorId(ID);
        }catch (Exception exception){
            assertEquals(RecursoNaoEncontradoException.class, exception.getClass());
            assertEquals(RECURSO_NAO_ENCONTRADO, exception.getMessage());
        }
    }

    private void startProduto(){
        modelProdutos = new ModelProdutos(ID, DESCRICAO, QUANTIDADE, QUANTIDADE_MINIMA, PRECO_COMPRA, PRECO_VENDA, CODIGO_BARRAS);
        modelProdutosOptional = Optional.of(new ModelProdutos(ID, DESCRICAO, QUANTIDADE, QUANTIDADE_MINIMA, PRECO_COMPRA, PRECO_VENDA, CODIGO_BARRAS));
    }

}