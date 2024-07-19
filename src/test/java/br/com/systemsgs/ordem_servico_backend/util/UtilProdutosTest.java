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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ActiveProfiles(value = "test")
@SpringBootTest
class UtilProdutosTest extends ConfigDadosEstaticosEntidades{

    private Optional<ModelProdutos> modelProdutosOptional;

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
    }
}