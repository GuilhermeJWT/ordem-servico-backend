package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.dto.ModelProdutosDTO;
import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;
import br.com.systemsgs.ordem_servico_backend.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProdutoControllerTest {

    public static final Long ID = 1L;
    public static final String DESCRICAO = "Notebook Gamer";
    public static final Integer QUANTIDADE = 5;
    public static final Integer QUANTIDADE_MINIMA = 1;
    public static final BigDecimal PRECO_COMPRA = BigDecimal.valueOf(1000L);
    public static final BigDecimal PRECO_VENDA = BigDecimal.valueOf(2000L);
    public static final String CODIGO_BARRAS = "789835741123";
    public static final Integer INDEX_0 = 0;

    private ModelProdutos modelProdutos;
    private ModelProdutosDTO modelProdutosDTO;

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startProduto();
    }

    @DisplayName("Teste para retornar a lista de Produtos - retorna 200")
    @Test
    void retornaListaProdutos200() {
        when(produtoService.listarProdutos()).thenReturn(List.of(modelProdutos));
        when(mapper.map(any(), any())).thenReturn(modelProdutosDTO);

        ResponseEntity<List<ModelProdutosDTO>> response = produtoController.listarProdutos();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(ModelProdutosDTO.class, response.getBody().get(INDEX_0).getClass());

        assertEquals(ID, response.getBody().get(INDEX_0).getId());
        assertEquals(DESCRICAO, response.getBody().get(INDEX_0).getDescricao());
        assertEquals(QUANTIDADE, response.getBody().get(INDEX_0).getQuantidade());
        assertEquals(QUANTIDADE_MINIMA, response.getBody().get(INDEX_0).getQuantidade_minima());
        assertEquals(PRECO_COMPRA, response.getBody().get(INDEX_0).getPreco_compra());
        assertEquals(PRECO_VENDA, response.getBody().get(INDEX_0).getPreco_venda());
        assertEquals(CODIGO_BARRAS, response.getBody().get(INDEX_0).getCodigo_barras());
    }

    @DisplayName("Pesquisa um Produto pelo ID e testa o Retorno do Body - retorna 200")
    @Test
    void pesquisaProdutoPorId200() {
        when(produtoService.listarProdutos()).thenReturn(List.of(modelProdutos));
        when(mapper.map(any(), any())).thenReturn(modelProdutosDTO);

        ResponseEntity<ModelProdutosDTO> response = produtoController.pesquisarPorId(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ModelProdutosDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(DESCRICAO, response.getBody().getDescricao());
        assertEquals(QUANTIDADE, response.getBody().getQuantidade());
        assertEquals(QUANTIDADE_MINIMA, response.getBody().getQuantidade_minima());
        assertEquals(PRECO_COMPRA, response.getBody().getPreco_compra());
        assertEquals(PRECO_VENDA, response.getBody().getPreco_venda());
        assertEquals(CODIGO_BARRAS, response.getBody().getCodigo_barras());
    }

    private void startProduto(){
        modelProdutos = new ModelProdutos(ID, DESCRICAO, QUANTIDADE, QUANTIDADE_MINIMA, PRECO_COMPRA, PRECO_VENDA, CODIGO_BARRAS);
        modelProdutosDTO = new ModelProdutosDTO(ID, DESCRICAO, QUANTIDADE, QUANTIDADE_MINIMA, PRECO_COMPRA, PRECO_VENDA, CODIGO_BARRAS);
    }

}