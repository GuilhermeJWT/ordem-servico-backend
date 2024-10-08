package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelProdutosDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.RecursoNaoEncontradoException;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class ProdutoServiceImplTest extends ConfigDadosEstaticosEntidades{

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
        produtoService = new ProdutoServiceImpl(produtoRepository, utilProdutos, mapper);
        startProduto();
    }

    @DisplayName("Pesquisa um Produto por ID")
    @Test
    void pesquisaProdutoPorId() {
        when(produtoRepository.findById(
                dadosProdutos().getId())).thenReturn(modelProdutosOptional);

        ModelProdutos response = produtoService.pesquisaPorId(dadosProdutos().getId());

        assertNotNull(response);

        assertEquals(dadosProdutos().getId(), response.getId());
        assertEquals(dadosProdutos().getDescricao(), response.getDescricao());
        assertEquals(dadosProdutos().getQuantidade(), response.getQuantidade());
        assertEquals(dadosProdutos().getQuantidadeMinima(), response.getQuantidadeMinima());
        assertEquals(dadosProdutos().getPrecoCompra(), response.getPrecoCompra());
        assertEquals(dadosProdutos().getPrecoVenda(), response.getPrecoVenda());
        assertEquals(dadosProdutos().getCodigoBarras(), response.getCodigoBarras());
    }

    @DisplayName("Pesquisa um Produto e retorna 404")
    @Test
    void pesquisaProdutoInexistenteRetornaNotFound(){
        when(produtoRepository.findById(anyLong())).thenThrow(new RecursoNaoEncontradoException());

        try{
            produtoService.pesquisaPorId(dadosProdutos().getId());
        }catch (Exception exception){
            assertEquals(RecursoNaoEncontradoException.class, exception.getClass());
            assertEquals(mensagemErro().get(1), exception.getMessage());
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

        assertEquals(dadosProdutos().getId(), response.get(0).getId());
        assertEquals(dadosProdutos().getDescricao(), response.get(0).getDescricao());
        assertEquals(dadosProdutos().getQuantidade(), response.get(0).getQuantidade());
        assertEquals(dadosProdutos().getQuantidadeMinima(), response.get(0).getQuantidadeMinima());
        assertEquals(dadosProdutos().getPrecoCompra(), response.get(0).getPrecoCompra());
        assertEquals(dadosProdutos().getPrecoVenda(), response.get(0).getPrecoVenda());
        assertEquals(dadosProdutos().getCodigoBarras(), response.get(0).getCodigoBarras());
    }

    @DisplayName("Deve salvar um Produto")
    @Test
    void deveSalvarProdutos() {
        when(produtoRepository.save(any())).thenReturn(modelProdutos);

        ModelProdutos response = produtoService.salvarProdutos(modelProdutosDTO);

        assertEquals(dadosProdutos().getId(), response.getId());
        assertEquals(dadosProdutos().getId(), response.getId());
        assertEquals(dadosProdutos().getDescricao(), response.getDescricao());
        assertEquals(dadosProdutos().getQuantidade(), response.getQuantidade());
        assertEquals(dadosProdutos().getQuantidadeMinima(), response.getQuantidadeMinima());
        assertEquals(dadosProdutos().getPrecoCompra(), response.getPrecoCompra());
        assertEquals(dadosProdutos().getPrecoVenda(), response.getPrecoVenda());
        assertEquals(dadosProdutos().getCodigoBarras(), response.getCodigoBarras());
    }

    @DisplayName("Deleta um Produto com Sucesso")
    @Test
    void deletarProduto() {
        doNothing().when(produtoRepository).deleteById(anyLong());

        produtoService.deletarProduto(dadosProdutos().getId());
        verify(produtoRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void atualizarProduto() {
        when(produtoRepository.save(any())).thenReturn(modelProdutos);
        when(utilProdutos.pesquisaProdutoPorId(anyLong())).thenReturn(modelProdutos);

        ModelProdutos response = produtoService.
                atualizarProduto(dadosProdutos().getId(), modelProdutosDTO);

        assertNotNull(response);
        assertEquals(dadosProdutos().getId(), response.getId());
        assertEquals(dadosProdutos().getDescricao(), response.getDescricao());
        assertEquals(dadosProdutos().getQuantidade(), response.getQuantidade());
        assertEquals(dadosProdutos().getQuantidadeMinima(), response.getQuantidadeMinima());
        assertEquals(dadosProdutos().getPrecoCompra(), response.getPrecoCompra());
        assertEquals(dadosProdutos().getPrecoVenda(), response.getPrecoVenda());
        assertEquals(dadosProdutos().getCodigoBarras(), response.getCodigoBarras());
    }

    private void startProduto(){
        modelProdutos = new ModelProdutos(
                dadosProdutos().getId(),
                dadosProdutos().getDescricao(),
                dadosProdutos().getQuantidade(),
                dadosProdutos().getQuantidadeMinima(),
                dadosProdutos().getPrecoCompra(),
                dadosProdutos().getPrecoVenda(),
                dadosProdutos().getCodigoBarras()
        );
        modelProdutosDTO = new ModelProdutosDTO(
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
    }
}