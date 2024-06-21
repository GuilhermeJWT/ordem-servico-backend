package br.com.systemsgs.ordem_servico_backend.exception;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExceptionControllerAdviceTest {

    @InjectMocks
    private ExceptionControllerAdvice exceptionControllerAdvice;

    private ConfigDadosEstaticosEntidades getErros = new ConfigDadosEstaticosEntidades();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Retorna Exception - Recurso não Encontrado!")
    @Test
    void recursoNaoEncontradoException() {
        ApiRestErrors apiRestErrors = exceptionControllerAdvice.
                recursoNaoEncontradoException(new RecursoNaoEncontradoException());

        assertNotNull(apiRestErrors);
        assertNotNull(apiRestErrors.getErros());

        assertEquals(ApiRestErrors.class, apiRestErrors.getClass());
    }

    @DisplayName("Retorna Exception - Cliente não Encontrado!")
    @Test
    void clienteNaoEncontradoException() {
        ApiRestErrors apiRestErrors = exceptionControllerAdvice.
                clienteNaoEncontradoException(new ClienteNaoEncontradoException());

        assertNotNull(apiRestErrors);
        assertNotNull(apiRestErrors.getErros());

        assertEquals(ApiRestErrors.class, apiRestErrors.getClass());
    }
}