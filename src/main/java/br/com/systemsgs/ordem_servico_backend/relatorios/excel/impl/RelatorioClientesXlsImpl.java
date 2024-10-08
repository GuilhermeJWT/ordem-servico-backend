package br.com.systemsgs.ordem_servico_backend.relatorios.excel.impl;

import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.relatorios.excel.GerarRelatorioExcel;
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
public class RelatorioClientesXlsImpl implements GerarRelatorioExcel {

    private final ClienteRepository clienteRepository;

    @Autowired
    public RelatorioClientesXlsImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

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
}
