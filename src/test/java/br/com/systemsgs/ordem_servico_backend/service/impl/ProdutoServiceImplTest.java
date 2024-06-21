package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProdutoServiceImplTest {

    private ModelProdutos modelProdutos;
    private ModelProdutosDTO modelProdutosDTO;
    private Optional<ModelProdutos> modelProdutosOptional;

    private ConfigDadosEstaticosEntidades getDadosEstaticosProduto = new ConfigDadosEstaticosEntidades();

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
        when(produtoRepository.findById(getDadosEstaticosProduto.
                dadosProdutos().getId())).thenReturn(modelProdutosOptional);

        ModelProdutos response = produtoService.pesquisaPorId(getDadosEstaticosProduto.dadosProdutos().getId());

        assertNotNull(response);

        assertEquals(getDadosEstaticosProduto.dadosProdutos().getId(), response.getId());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getDescricao(), response.getDescricao());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getQuantidade(), response.getQuantidade());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getQuantidade_minima(), response.getQuantidade_minima());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getPreco_compra(), response.getPreco_compra());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getPreco_venda(), response.getPreco_venda());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getCodigo_barras(), response.getCodigo_barras());
    }

    @DisplayName("Pesquisa um Produto e retorna 404")
    @Test
    void pesquisaProdutoInexistenteRetornaNotFound(){
        when(produtoRepository.findById(anyLong())).thenThrow(new RecursoNaoEncontradoException());

        try{
            produtoService.pesquisaPorId(getDadosEstaticosProduto.dadosProdutos().getId());
        }catch (Exception exception){
            assertEquals(RecursoNaoEncontradoException.class, exception.getClass());
            assertEquals(getDadosEstaticosProduto.mensagemErro().get(1), exception.getMessage());
        }
    }

    @DisplayName("Lista todos os Produtos")
    @Test
    void listarProdutos() {
        when(produtoRepository.findAll()).thenReturn(List.of(modelProdutos));

        List<ModelProdutos> response = produtoService.listarProdutos();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(ModelProdutos.class, response.get(0).getClass());

        assertEquals(getDadosEstaticosProduto.dadosProdutos().getId(), response.get(0).getId());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getDescricao(), response.get(0).getDescricao());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getQuantidade(), response.get(0).getQuantidade());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getQuantidade_minima(), response.get(0).getQuantidade_minima());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getPreco_compra(), response.get(0).getPreco_compra());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getPreco_venda(), response.get(0).getPreco_venda());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getCodigo_barras(), response.get(0).getCodigo_barras());
    }

    @DisplayName("Deve salvar um Produto")
    @Test
    void deveSalvarProdutos() {
        when(produtoRepository.save(any())).thenReturn(modelProdutos);

        ModelProdutos response = produtoService.salvarProdutos(modelProdutosDTO);

        assertEquals(getDadosEstaticosProduto.dadosProdutos().getId(), response.getId());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getId(), response.getId());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getDescricao(), response.getDescricao());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getQuantidade(), response.getQuantidade());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getQuantidade_minima(), response.getQuantidade_minima());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getPreco_compra(), response.getPreco_compra());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getPreco_venda(), response.getPreco_venda());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getCodigo_barras(), response.getCodigo_barras());
    }

    @DisplayName("Deleta um Produto com Sucesso")
    @Test
    void deletarProduto() {
        doNothing().when(produtoRepository).deleteById(anyLong());

        produtoService.deletarProduto(getDadosEstaticosProduto.dadosProdutos().getId());
        verify(produtoRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void atualizarProduto() {
        when(produtoRepository.save(any())).thenReturn(modelProdutos);
        when(utilProdutos.pesquisaProdutoPorId(anyLong())).thenReturn(modelProdutos);

        ModelProdutos response = produtoService.
                atualizarProduto(getDadosEstaticosProduto.dadosProdutos().getId(), modelProdutosDTO);

        assertNotNull(response);
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getId(), response.getId());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getDescricao(), response.getDescricao());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getQuantidade(), response.getQuantidade());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getQuantidade_minima(), response.getQuantidade_minima());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getPreco_compra(), response.getPreco_compra());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getPreco_venda(), response.getPreco_venda());
        assertEquals(getDadosEstaticosProduto.dadosProdutos().getCodigo_barras(), response.getCodigo_barras());
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