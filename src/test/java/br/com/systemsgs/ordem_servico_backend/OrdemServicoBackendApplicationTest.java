package br.com.systemsgs.ordem_servico_backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "test")
@SpringBootTest
class OrdemServicoBackendApplicationTest {

    @DisplayName("Testa se o método main executa sem exceções")
    @Test
    void main() {
        OrdemServicoBackendApplication.main(new String[]{});
    }
}