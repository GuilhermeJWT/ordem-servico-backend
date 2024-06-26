package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.ModelProdutosDTO;
import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;
import br.com.systemsgs.ordem_servico_backend.repository.ProdutoRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class ProdutoControllerTest {

    private ModelProdutos modelProdutos;
    private ModelProdutosDTO modelProdutosDTO;

    private ConfigDadosEstaticosEntidades getDadosEstaticosProduto = new ConfigDadosEstaticosEntidades();

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

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
        assertEquals(ModelProdutosDTO.class, response.getBody().get(0).getClass());

        assertEquals(getDadosEstaticosProduto.dadosProdutos().getId(), response.getBody().get(0).getId());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getDescricao(), response.getBody().get(0).getDescricao());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getQuantidade(), response.getBody().get(0).getQuantidade());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getQuantidade_minima(), response.getBody().get(0).getQuantidade_minima());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getPreco_compra(), response.getBody().get(0).getPreco_compra());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getPreco_venda(), response.getBody().get(0).getPreco_venda());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getCodigo_barras(), response.getBody().get(0).getCodigo_barras());
    }

    @DisplayName("Pesquisa um Produto pelo ID e testa o Retorno do Body - retorna 200")
    @Test
    void pesquisaProdutoPorId200() {
        when(produtoService.listarProdutos()).thenReturn(List.of(modelProdutos));
        when(mapper.map(any(), any())).thenReturn(modelProdutosDTO);

        ResponseEntity<ModelProdutosDTO> response = produtoController.
                pesquisarPorId(getDadosEstaticosProduto.dadosProdutos().getId());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ModelProdutosDTO.class, response.getBody().getClass());

        assertEquals(getDadosEstaticosProduto.dadosProdutos().getId(), response.getBody().getId());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getDescricao(), response.getBody().getDescricao());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getQuantidade(), response.getBody().getQuantidade());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getQuantidade_minima(), response.getBody().getQuantidade_minima());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getPreco_compra(), response.getBody().getPreco_compra());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getPreco_venda(), response.getBody().getPreco_venda());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getCodigo_barras(), response.getBody().getCodigo_barras());
    }

    @DisplayName("Pesquisa um Produto inexistente e retorna 404")
    @Test
    void pesquisaProdutoRetorna404(){
        when(produtoRepository.findById(anyLong())).thenThrow(new RecursoNaoEncontradoException());

        try{
            produtoService.pesquisaPorId(getDadosEstaticosProduto.dadosProdutos().getId());
        }catch (Exception exception){
            assertEquals(RecursoNaoEncontradoException.class, exception.getClass());
            assertEquals("Recurso não Encontrado!", exception.getMessage());
        }
    }

    @DisplayName("Salva um Produto e retorna 201")
    @Test
    void salvaProdutoRetorna201(){
        when(produtoService.salvarProdutos(any())).thenReturn(modelProdutos);

        ResponseEntity<ModelProdutosDTO> response = produtoController.salvarProduto(modelProdutosDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @DisplayName("Atualiza um Produto e retorna 200")
    @Test
    void atualizaProdutoRetorna200(){
        when(produtoService.atualizarProduto(getDadosEstaticosProduto.
                dadosProdutos().getId(),modelProdutosDTO)).thenReturn(modelProdutos);
        when(mapper.map(any(), any())).thenReturn(modelProdutosDTO);

        ResponseEntity<ModelProdutosDTO> response = produtoController.
                atualizarProdutos(getDadosEstaticosProduto.dadosProdutos().getId(), modelProdutosDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ModelProdutosDTO.class, response.getBody().getClass());

        assertEquals(getDadosEstaticosProduto.dadosProdutos().getId(), response.getBody().getId());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getDescricao(), response.getBody().getDescricao());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getQuantidade(), response.getBody().getQuantidade());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getQuantidade_minima(), response.getBody().getQuantidade_minima());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getPreco_compra(), response.getBody().getPreco_compra());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getPreco_venda(), response.getBody().getPreco_venda());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getCodigo_barras(), response.getBody().getCodigo_barras());
    }

    @DisplayName("Deleta um Produto e retorna 204")
    @Test
    void deletaProdutoRetorna204(){
        doNothing().when(produtoService).deletarProduto(anyLong());

        ResponseEntity<ModelProdutosDTO> response = produtoController.
                delete(getDadosEstaticosProduto.dadosProdutos().getId());

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(produtoService, times(1)).deletarProduto(anyLong());
    }

    private void startProduto(){
        modelProdutos = new ModelProdutos(
                getDadosEstaticosProduto.dadosProdutos().getId(),
                getDadosEstaticosProduto.dadosProdutos().getDescricao(),
                getDadosEstaticosProduto.dadosProdutos().getQuantidade(),
                getDadosEstaticosProduto.dadosProdutos().getQuantidade_minima(),
                getDadosEstaticosProduto.dadosProdutos().getPreco_compra(),
                getDadosEstaticosProduto.dadosProdutos().getPreco_venda(),
                getDadosEstaticosProduto.dadosProdutos().getCodigo_barras()
        );
        modelProdutosDTO = new ModelProdutosDTO(
                getDadosEstaticosProduto.dadosProdutos().getId(),
                getDadosEstaticosProduto.dadosProdutos().getDescricao(),
                getDadosEstaticosProduto.dadosProdutos().getQuantidade(),
                getDadosEstaticosProduto.dadosProdutos().getQuantidade_minima(),
                getDadosEstaticosProduto.dadosProdutos().getPreco_compra(),
                getDadosEstaticosProduto.dadosProdutos().getPreco_venda(),
                getDadosEstaticosProduto.dadosProdutos().getCodigo_barras()
        );
    }
}