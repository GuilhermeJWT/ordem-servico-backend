package br.com.systemsgs.ordem_servico_backend.relatorios.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.relatorios.GerarRelatorio;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class RelatoriosClientesServiceImplTest extends ConfigDadosEstaticosEntidades {

    private ModelClientes modelClientes;

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
        startCliente();
    }

    @DisplayName("Teste para gerar um relatório de Clientes")
    @Test
    void deveGerarRelatorioExcelComSucesso() throws IOException {
        when(clienteRepository.findAll()).thenReturn(List.of(modelClientes));

        ResponseEntity<byte[]> responseEntity = relatoriosClientesService.gerarRelatorioExcel(response);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("attachment; filename=relatorio-clientes.xlsx", responseEntity.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertNotNull(responseEntity.getBody());

        verify(clienteRepository, times(1)).findAll();
    }

    @DisplayName("Teste para gerar um relatório vazio")
    @Test
    void deveGerarRelatorioExcelComListaVazia() throws IOException {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList());

        ResponseEntity<byte[]> responseEntity = relatoriosClientesService.gerarRelatorioExcel(response);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("attachment; filename=relatorio-clientes.xlsx", responseEntity.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().length > 0);

        verify(clienteRepository, times(1)).findAll();
    }

    @DisplayName("Teste para configurar o cabeçalho do relatório")
    @Test
    void deveConfigurarCabecalhoCorretamente() throws IOException {
        when(clienteRepository.findAll()).thenReturn(List.of(modelClientes));

        ResponseEntity<byte[]> responseEntity = relatoriosClientesService.gerarRelatorioExcel(response);

        HttpHeaders headers = responseEntity.getHeaders();
        assertEquals("attachment; filename=relatorio-clientes.xlsx", headers.getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @DisplayName("Teste para gerar um relatório Pdf")
    @Test
    void deveGerarRelatorioPdfComSucesso() throws IOException {
        when(clienteRepository.findAll()).thenReturn(List.of(modelClientes));

        byte[] pdfData = relatoriosClientesService.gerarRelatorioPdf();

        assertNotNull(pdfData);
        assertTrue(pdfData.length > 0);

        verify(clienteRepository, times(1)).findAll();
    }

    @DisplayName("Teste oara gerar relatório com lista vazia")
    @Test
    void deveGerarRelatorioPdfComListaVazia() throws IOException {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList());

        byte[] pdfData = relatoriosClientesService.gerarRelatorioPdf();

        assertNotNull(pdfData);
        assertTrue(pdfData.length > 0);

        verify(clienteRepository, times(1)).findAll();
    }

    private void startCliente(){
        modelClientes = new ModelClientes(
                dadosClientes().getId(),
                dadosClientes().getNome(),
                dadosClientes().getCpf(),
                dadosClientes().getCelular(),
                dadosClientes().getEmail(),
                dadosClientes().isAtivo(),
                dadosClientes().getEndereco(),
                dadosClientes().getOrdemServicos()
        );
    }
}