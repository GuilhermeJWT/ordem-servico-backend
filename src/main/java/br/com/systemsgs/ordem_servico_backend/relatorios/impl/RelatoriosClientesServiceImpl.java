package br.com.systemsgs.ordem_servico_backend.relatorios.impl;

import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.relatorios.GerarRelatorio;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;

@Service
public class RelatoriosClientesServiceImpl implements GerarRelatorio {

    private final ClienteRepository clienteRepository;

    @Autowired
    public RelatoriosClientesServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ResponseEntity<byte[]> gerarRelatorioExcel(HttpServletResponse response) throws IOException {
        List<ModelClientes> clientes = clienteRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Relatório de Clientes");

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        dataStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row headerRow = sheet.createRow(0);
        String[] colunas = {"ID", "Nome", "Email", "Telefone", "Cidade", "Estado", "Cep", "Ativo"};
        for (int i = 0; i < colunas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(colunas[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (ModelClientes cliente : clientes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(cliente.getId());
            row.createCell(1).setCellValue(cliente.getNome());
            row.createCell(2).setCellValue(cliente.getEmail());
            row.createCell(3).setCellValue(cliente.getCelular());
            row.createCell(4).setCellValue(cliente.getEndereco().getCidade());
            row.createCell(5).setCellValue(cliente.getEndereco().getEndereco());
            row.createCell(6).setCellValue(cliente.getEndereco().getCep());
            Cell ativoCell = row.createCell(7);
            ativoCell.setCellValue(cliente.isAtivo() ? "Ativo" : "Inativo");

            if (rowNum % 2 == 0) {
                for (int i = 0; i < colunas.length; i++) {
                    row.getCell(i).setCellStyle(dataStyle);
                }
            }
        }

        for (int i = 0; i < colunas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=relatorio-clientes.xlsx");

        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }

    @Override
    public byte[] gerarRelatorioPdf() throws IOException {
        List<ModelClientes> clientes = clienteRepository.findAll();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(20, 20, 20, 20);

        Paragraph titulo = new Paragraph("Relatório de Clientes")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.BLUE);
        document.add(titulo);

        document.add(new Paragraph("\n"));

        float[] columnWidths = {1, 3, 3, 2, 2, 2, 2, 1.5f};
        com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        Style headerStyle = new Style()
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(12)
                .setBorder(new SolidBorder(1));

        Style dataStyle = new Style()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10)
                .setBorder(new SolidBorder(1));

        String[] colunas = {"ID", "Nome", "Email", "Telefone", "Cidade", "Estado", "Cep", "Ativo"};
        for (String coluna : colunas) {
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(coluna)).addStyle(headerStyle));
        }

        for (ModelClientes cliente : clientes) {
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(cliente.getId()))).addStyle(dataStyle));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cliente.getNome())).addStyle(dataStyle));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cliente.getEmail())).addStyle(dataStyle));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cliente.getCelular())).addStyle(dataStyle));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cliente.getEndereco().getCidade())).addStyle(dataStyle));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cliente.getEndereco().getEndereco())).addStyle(dataStyle));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cliente.getEndereco().getCep())).addStyle(dataStyle));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cliente.isAtivo() ? "Ativo" : "Inativo")).addStyle(dataStyle));
        }

        document.add(table);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
