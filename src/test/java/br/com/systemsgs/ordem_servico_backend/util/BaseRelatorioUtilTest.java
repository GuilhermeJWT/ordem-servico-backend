package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class BaseRelatorioUtilTest extends ConfigDadosEstaticosEntidades {

    @Mock
    private BaseRelatorioUtil baseRelatorioUtil;

    @DisplayName("Teste para configurar os relatórios corretamente")
    @Test
    void deveGerarRelatorioExcelComSucesso() throws IOException {
        List<String[]> data = Arrays.asList(
                new String[]{"1", "Guilherme", "gui@mail.com"},
                new String[]{"2", "Guilherme Santos", "guisantos@mail.com"}
        );

        String[] headers = {"ID", "Nome", "Email"};

        BiConsumer<Row, String[]> rowMapper = (row, item) -> {
            row.createCell(0).setCellValue(item[0]);
            row.createCell(1).setCellValue(item[1]);
            row.createCell(2).setCellValue(item[2]);
        };

        byte[] excelBytes = baseRelatorioUtil.configuraRelatorioExcel("Relatório Teste - Guilherme", data, headers, rowMapper);

        assertNotNull(excelBytes);
        assertTrue(excelBytes.length > 0);

        Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelBytes));
        Sheet sheet = workbook.getSheetAt(0);

        Row headerRow = sheet.getRow(0);
        assertEquals("ID", headerRow.getCell(0).getStringCellValue());
        assertEquals("Nome", headerRow.getCell(1).getStringCellValue());
        assertEquals("Email", headerRow.getCell(2).getStringCellValue());

        Row dataRow = sheet.getRow(1);
        assertEquals("1", dataRow.getCell(0).getStringCellValue());
        assertEquals("Guilherme", dataRow.getCell(1).getStringCellValue());
        assertEquals("gui@mail.com", dataRow.getCell(2).getStringCellValue());

        dataRow = sheet.getRow(2);
        assertEquals("2", dataRow.getCell(0).getStringCellValue());
        assertEquals("Guilherme Santos", dataRow.getCell(1).getStringCellValue());
        assertEquals("guisantos@mail.com", dataRow.getCell(2).getStringCellValue());

        workbook.close();
    }

    @DisplayName("Teste para gerar um relatório de teste com dados diferentes")
    @Test
    public void deveGerarRelatorioExcelComDadosGenericosDiferentes() throws IOException {
        List<Integer> data = Arrays.asList(100, 200, 300);
        String[] headers = {"Número"};

        BiConsumer<Row, Integer> rowMapper = (row, item) -> row.createCell(0).setCellValue(item);

        byte[] excelBytes = baseRelatorioUtil.configuraRelatorioExcel("Relatório Genérico", data, headers, rowMapper);

        assertNotNull(excelBytes);
        assertTrue(excelBytes.length > 0);

        Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelBytes));
        Sheet sheet = workbook.getSheetAt(0);

        assertEquals(100, (int) sheet.getRow(1).getCell(0).getNumericCellValue());
        assertEquals(200, (int) sheet.getRow(2).getCell(0).getNumericCellValue());
        assertEquals(300, (int) sheet.getRow(3).getCell(0).getNumericCellValue());

        workbook.close();
    }
}