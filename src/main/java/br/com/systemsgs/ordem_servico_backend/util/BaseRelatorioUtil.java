package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.enums.TipoRelatorio;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;

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

public abstract class BaseRelatorioUtil {

    protected static <T> byte[] configuraRelatorioExcel(String titulo,List<T> data, String[] headers, BiConsumer<Row, T> rowMapper)
                                                throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(titulo);

        CellStyle headerStyle = createHeaderStyle(workbook);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (T item : data) {
            Row row = sheet.createRow(rowNum++);
            rowMapper.accept(row, item);
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

    protected static byte[] configuraRelatorioPdf(String tituloRelatorio,
                                                  String[] colunas,
                                                  List<String[]> dados,
                                                  float[] tamanhoColunas,
                                                  TipoRelatorio tipoRelatorio) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        
        Document document = null;

        if(tipoRelatorio.equals(TipoRelatorio.RETRATO)){
            document = new Document(pdf, PageSize.A4);
        }else if(tipoRelatorio.equals(TipoRelatorio.PAISAGEM)){
            document = new Document(pdf, PageSize.A4.rotate());
        }

        document.setMargins(20, 20, 20, 20);

        Paragraph titulo = new Paragraph(tituloRelatorio)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.BLUE);
        document.add(titulo);

        document.add(new Paragraph("\n"));

        com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(UnitValue.createPercentArray(tamanhoColunas));
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

        for (String coluna : colunas) {
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(coluna)).addStyle(headerStyle));
        }

        for (String[] linha : dados) {
            for (String valor : linha) {
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(valor)).addStyle(dataStyle));
            }
        }

        document.add(table);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }
}
