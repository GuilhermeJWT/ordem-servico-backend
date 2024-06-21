package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class UtilProdutosTest {

    private Optional<ModelProdutos> modelProdutosOptional;

    private ConfigDadosEstaticosEntidades getDadosEstaticosProduto = new ConfigDadosEstaticosEntidades();

    @InjectMocks
    private UtilProdutos utilProdutos;

    @Mock
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startProdutoOptional();
    }

    @DisplayName("Pesquisa um Produto por ID e retorna a Entidade para Validação")
    @Test
    void pesquisarProdutoPeloId() {
        when(produtoRepository.findById(anyLong())).thenReturn(modelProdutosOptional);

        ModelProdutos response = utilProdutos.pesquisaProdutoPorId(getDadosEstaticosProduto.dadosProdutos().getId());

        assertNotNull(response);

        assertEquals(getDadosEstaticosProduto.dadosProdutos().getId(), response.getId());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getDescricao(), response.getDescricao());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getQuantidade(), response.getQuantidade());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getQuantidade_minima(), response.getQuantidade_minima());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getPreco_compra(), response.getPreco_compra());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getPreco_venda(), response.getPreco_venda());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getCodigo_barras(), response.getCodigo_barras());
    }

    @DisplayName("Pesquisa um Produto por ID e retorna Not Found")
    @Test
    void pesquisaProdutoInexistenteRetornaNotFound(){
        when(produtoRepository.findById(anyLong())).thenThrow(new RecursoNaoEncontradoException());

        try{
            utilProdutos.pesquisaProdutoPorId(getDadosEstaticosProduto.dadosProdutos().getId());
        }catch (Exception exception){
            assertEquals(RecursoNaoEncontradoException.class, exception.getClass());
            assertEquals(getDadosEstaticosProduto.mensagemErro().get(1), exception.getMessage());
        }
    }

    private void startProdutoOptional(){
        modelProdutosOptional = Optional.of(new ModelProdutos(
                getDadosEstaticosProduto.dadosProdutos().getId(),
                getDadosEstaticosProduto.dadosProdutos().getDescricao(),
                getDadosEstaticosProduto.dadosProdutos().getQuantidade(),
                getDadosEstaticosProduto.dadosProdutos().getQuantidade_minima(),
                getDadosEstaticosProduto.dadosProdutos().getPreco_compra(),
                getDadosEstaticosProduto.dadosProdutos().getPreco_venda(),
                getDadosEstaticosProduto.dadosProdutos().getCodigo_barras()
        ));
    }
}