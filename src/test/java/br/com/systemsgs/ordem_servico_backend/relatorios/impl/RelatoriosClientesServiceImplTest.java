package br.com.systemsgs.ordem_servico_backend.relatorios.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.relatorios.GerarRelatorio;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "test")
@SpringBootTest
class RelatoriosClientesServiceImplTest extends ConfigDadosEstaticosEntidades {

    @InjectMocks
    private RelatoriosClientesServiceImpl relatoriosClientesService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private HttpServletResponse response;

    @Mock
    private GerarRelatorio gerarRelatorio;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        relatoriosClientesService = new RelatoriosClientesServiceImpl(clienteRepository);
    }
}