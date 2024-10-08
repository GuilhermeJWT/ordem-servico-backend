package br.com.systemsgs.ordem_servico_backend.relatorios.pdf.impl;

import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.relatorios.pdf.GerarRelatorioPdf;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RelatorioClientesPdfImpl implements GerarRelatorioPdf {

    private final ClienteRepository clienteRepository;

    @Autowired
    public RelatorioClientesPdfImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
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
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
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
            table.addHeaderCell(new Cell().add(new Paragraph(coluna)).addStyle(headerStyle));
        }

        for (ModelClientes cliente : clientes) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(cliente.getId()))).addStyle(dataStyle));
            table.addCell(new Cell().add(new Paragraph(cliente.getNome())).addStyle(dataStyle));
            table.addCell(new Cell().add(new Paragraph(cliente.getEmail())).addStyle(dataStyle));
            table.addCell(new Cell().add(new Paragraph(cliente.getCelular())).addStyle(dataStyle));
            table.addCell(new Cell().add(new Paragraph(cliente.getEndereco().getCidade())).addStyle(dataStyle));
            table.addCell(new Cell().add(new Paragraph(cliente.getEndereco().getEndereco())).addStyle(dataStyle));
            table.addCell(new Cell().add(new Paragraph(cliente.getEndereco().getCep())).addStyle(dataStyle));
            table.addCell(new Cell().add(new Paragraph(cliente.isAtivo() ? "Ativo" : "Inativo")).addStyle(dataStyle));
        }

        document.add(table);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
