package br.com.systemsgs.ordem_servico_backend.relatorios.excel.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.relatorios.excel.GerarRelatorioExcel;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import static org.mockito.Mockito.when;

@ActiveProfiles(value = "test")
@SpringBootTest
class RelatorioClientesXlsImplTest extends ConfigDadosEstaticosEntidades {

    @InjectMocks
    private RelatorioClientesXlsImpl relatorioClientesXls;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private HttpServletResponse response;

    @Mock
    private GerarRelatorioExcel gerarRelatorioExcel;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        relatorioClientesXls = new RelatorioClientesXlsImpl(clienteRepository);
    }

    @DisplayName("Deve gerar um Relatório de Clientes para Excel - 200")
    @Test
    void deveGerarRelatorioExcelComClientes() throws IOException {
        ModelClientes cliente1 = dadosClientes();
        ModelClientes cliente2 = dadosClientes();

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        ResponseEntity<byte[]> responseEntity = relatorioClientesXls.gerarRelatorioExcel(response);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        HttpHeaders headers = responseEntity.getHeaders();
        assertEquals("attachment; filename=relatorio-clientes.xlsx", headers.getFirst("Content-Disposition"));
    }
}