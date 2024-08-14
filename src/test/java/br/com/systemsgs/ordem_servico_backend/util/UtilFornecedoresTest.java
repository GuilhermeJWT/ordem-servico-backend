package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.exception.errors.FornecedorNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelFornecedor;
import br.com.systemsgs.ordem_servico_backend.repository.FornecedoresRepository;
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
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class UtilFornecedoresTest extends ConfigDadosEstaticosEntidades {

    private Optional<ModelFornecedor> modelFornecedorOptional;

    @InjectMocks
    private UtilFornecedores utilFornecedores;

    @Mock
    private FornecedoresRepository fornecedoresRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        utilFornecedores = new UtilFornecedores(fornecedoresRepository);
        startFornecedor();
    }

    @DisplayName("Teste para pesquisar um Fornecedor por ID")
    @Test
    void pesquisarFornecedorPeloId() {
        when(fornecedoresRepository.findById(dadosFornecedores().getId()))
                .thenReturn(modelFornecedorOptional);

        ModelFornecedor response = utilFornecedores.pesquisarFornecedorPeloId(dadosFornecedores().getId());

        assertNotNull(response);

        assertEquals(dadosFornecedores().getId(), response.getId());
        assertEquals(dadosFornecedores().getNome(), response.getNome());
        assertEquals(dadosFornecedores().getNomeFantasia(), response.getNomeFantasia());
        assertEquals(dadosFornecedores().getTipoPessoa(), response.getTipoPessoa());
        assertEquals(dadosFornecedores().getCnpj(), response.getCnpj());
        assertEquals(dadosFornecedores().getEndereco().getEndereco(), response.getEndereco().getEndereco());
        assertEquals(dadosFornecedores().getEndereco().getComplemento(), response.getEndereco().getComplemento());
        assertEquals(dadosFornecedores().getEndereco().getCidade(), response.getEndereco().getCidade());
        assertEquals(dadosFornecedores().getEndereco().getEstado(), response.getEndereco().getEstado());
        assertEquals(dadosFornecedores().getEndereco().getCep(), response.getEndereco().getCep());
    }

    @DisplayName("Pesquisa um Fornecedor por ID e retorna Not Found")
    @Test
    void pesquisaFornecedorInexistenteRetornaNotFound(){
        when(fornecedoresRepository.findById(dadosFornecedores().getId()))
                .thenThrow(new FornecedorNaoEncontradoException());

        try{
            utilFornecedores.pesquisarFornecedorPeloId(dadosFornecedores().getId());
        }catch (Exception exception){
            assertEquals(FornecedorNaoEncontradoException.class, exception.getClass());
            assertEquals(mensagemErro().get(6), exception.getMessage());
        }
    }

    private void startFornecedor() {
        modelFornecedorOptional = Optional.of(new ModelFornecedor(
                dadosFornecedores().getId(),
                dadosFornecedores().getNome(),
                dadosFornecedores().getNomeFantasia(),
                dadosFornecedores().getTipoPessoa(),
                dadosFornecedores().getCnpj(),
                dadosEndereco()
        ));
    }
}