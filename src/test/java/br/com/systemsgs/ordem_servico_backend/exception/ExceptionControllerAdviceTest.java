package br.com.systemsgs.ordem_servico_backend.exception;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.exception.errors.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class ExceptionControllerAdviceTest extends ConfigDadosEstaticosEntidades {

    @InjectMocks
    private ExceptionControllerAdvice exceptionControllerAdvice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Retorna Exception - Recurso não Encontrado! - 404")
    @Test
    void testRecursoNaoEncontradoException() {
        ApiRestErrors apiRestErrors = exceptionControllerAdvice.
                recursoNaoEncontradoException(new RecursoNaoEncontradoException());

        retornaAssertException(apiRestErrors);
    }

    @DisplayName("Retorna Exception - Cliente não Encontrado! - 404")
    @Test
    void testClienteNaoEncontradoException() {
        ApiRestErrors apiRestErrors = exceptionControllerAdvice.
                clienteNaoEncontradoException(new ClienteNaoEncontradoException());

        retornaAssertException(apiRestErrors);
    }

    @DisplayName("Retorna Exception - Técnico Responsavel não Encontrado! - 404")
    @Test
    void testTecnicoNaoEncontradoException() {
        ApiRestErrors apiRestErrors = exceptionControllerAdvice.
                tecnicoNaoEncontradoException(new TecnicoResponsavelNaoEncontradoException());

        retornaAssertException(apiRestErrors);
    }

    @DisplayName("Retorna Exception - Venda não Encontrada! - 404")
    @Test
    void testVendaNaoEncontradaException() {
        ApiRestErrors apiRestErrors = exceptionControllerAdvice.
                vendaNaoEncontradaException(new VendaNaoEncontradaException());

        retornaAssertException(apiRestErrors);
    }

    @DisplayName("Retorna Exception - E-mail Duplicado! - 400")
    @Test
    void testEmailDuplicadoException() {
        ApiRestErrors apiRestErrors = exceptionControllerAdvice.
                emailDuplicadoException(new EmailDuplicadoException());

        retornaAssertException(apiRestErrors);
    }

    @DisplayName("Retorna Exception - Internal Server Error 500")
    @Test
    void testErroInternoServidorException() {
        ApiRestErrors apiRestErrors = exceptionControllerAdvice.
                internalServerErroException();

        retornaAssertException(apiRestErrors);
    }

    @DisplayName("Retorna Exception - Body Vazio - 400")
    @Test
    void testPayloadVazioException() {
        ApiRestErrors apiRestErrors = exceptionControllerAdvice.
                httpMessageNotReadableException();

        retornaAssertException(apiRestErrors);
    }

    @DisplayName("Retorna Exception - Campos Duplicados - 422")
    @Test
    void testCamposDuplicadosException() {
        ApiRestErrors apiRestErrors = exceptionControllerAdvice.
                camposDuplicadoException();

        retornaAssertException(apiRestErrors);
    }

    @DisplayName("Retorna Exception - Conta não Encontrada - 404")
    @Test
    void contasPagarReceberException() {
        ApiRestErrors apiRestErrors = exceptionControllerAdvice.
                contasPagarReceberNaoEncontradaException(new ContasPagarReceberNaoEncontradaException());

        retornaAssertException(apiRestErrors);
    }

    @DisplayName("Retorna Exception - Fornecedor não Encontrado - 404")
    @Test
    void fornecedorNaoEncontradoException() {
        ApiRestErrors apiRestErrors = exceptionControllerAdvice.
                fornecedorNaoEncontradoException();

        retornaAssertException(apiRestErrors);
    }

    @DisplayName("Retorna Exception - Método Http não Suportado - 400")
    @Test
    void metodoHttpNaoSuportadoException() {
        ApiRestErrors apiRestErrors = exceptionControllerAdvice.
                metodoHttpNaoSuportadoException();

        retornaAssertException(apiRestErrors);
    }

    private void retornaAssertException(ApiRestErrors apiRestErrors){
        assertNotNull(apiRestErrors);
        assertNotNull(apiRestErrors.getErros());
        assertEquals(ApiRestErrors.class, apiRestErrors.getClass());
    }
}