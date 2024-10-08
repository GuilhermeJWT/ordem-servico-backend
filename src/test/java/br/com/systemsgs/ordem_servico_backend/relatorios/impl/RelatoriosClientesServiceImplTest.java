package br.com.systemsgs.ordem_servico_backend.relatorios.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.relatorios.GerarRelatorio;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

    @DisplayName("Deve gerar um Relatório de Clientes para Excel - 200")
    @Test
    void deveGerarRelatorioExcelComClientes() throws IOException {
        ModelClientes cliente1 = dadosClientes();
        ModelClientes cliente2 = dadosClientes();

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        ResponseEntity<byte[]> responseEntity = relatoriosClientesService.gerarRelatorioExcel(response);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        HttpHeaders headers = responseEntity.getHeaders();
        assertEquals("attachment; filename=relatorio-clientes.xlsx", headers.getFirst("Content-Disposition"));
    }

    @DisplayName("Teste para alternar as cores da linha para cada Cliente")
    @Test
    void deveAlternarCorDasLinhasNoRelatorio() throws IOException {
        ModelClientes cliente1 = dadosClientes();
        ModelClientes cliente2 = dadosClientes();

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        ResponseEntity<byte[]> responseEntity = relatoriosClientesService.gerarRelatorioExcel(response);

        Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(responseEntity.getBody()));
        Sheet sheet = workbook.getSheetAt(0);

        Row row1 = sheet.getRow(1);
        Row row2 = sheet.getRow(2);

        assertNotEquals(row1.getCell(0).getCellStyle(), row2.getCell(0).getCellStyle());
    }

    @DisplayName("Teste para gerar os Cabeçalhos do relatório")
    @Test
    void deveGerarCabecalhoCorretamente() throws IOException {
        when(clienteRepository.findAll()).thenReturn(List.of());

        ResponseEntity<byte[]> responseEntity = relatoriosClientesService.gerarRelatorioExcel(response);

        Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(responseEntity.getBody()));
        Sheet sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.getRow(0);

        assertEquals("ID", headerRow.getCell(0).getStringCellValue());
        assertEquals("Nome", headerRow.getCell(1).getStringCellValue());
        assertEquals("Email", headerRow.getCell(2).getStringCellValue());
        assertEquals("Telefone", headerRow.getCell(3).getStringCellValue());
        assertEquals("Cidade", headerRow.getCell(4).getStringCellValue());
        assertEquals("Estado", headerRow.getCell(5).getStringCellValue());
        assertEquals("Cep", headerRow.getCell(6).getStringCellValue());
        assertEquals("Ativo", headerRow.getCell(7).getStringCellValue());
    }

    @DisplayName("Teste para gerar com sucesso o relatório de PDF de Clientes")
    @Test
    void gerarRelatorioPdfSucesso() throws IOException {
        ModelClientes cliente1 = dadosClientes();
        ModelClientes cliente2 = dadosClientes();

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        byte[] pdfBytes = relatoriosClientesService.gerarRelatorioPdf();

        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }

    @DisplayName("Teste para gerar um relatório vazio")
    @Test
    void gerarRelatorioPdfListaVazia() throws IOException {
        List<ModelClientes> clientesMock = Collections.emptyList();

        when(clienteRepository.findAll()).thenReturn(clientesMock);

        byte[] pdfBytes = relatoriosClientesService.gerarRelatorioPdf();

        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }

    @DisplayName("Teste para gerar o relatório com nome, id e nome dos clientes")
    @Test
    void gerarRelatorioPdfVerificarConteudoGerado() throws IOException {
        ModelClientes cliente1 = dadosClientes();
        ModelClientes cliente2 = dadosClientes();

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        byte[] pdfBytes = relatoriosClientesService.gerarRelatorioPdf();

        PdfReader reader = new PdfReader(new ByteArrayInputStream(pdfBytes));
        PdfDocument pdfDoc = new PdfDocument(reader);
        String pdfText = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1));

        assertTrue(pdfText.contains("Relatório de Clientes"));
        assertTrue(pdfText.contains(String.valueOf(cliente1.getId())));
        assertTrue(pdfText.contains(cliente1.getNome()));
    }
}