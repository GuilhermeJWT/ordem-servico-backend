package br.com.systemsgs.ordem_servico_backend.relatorios.excel;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface GerarRelatorioExcel {

    ResponseEntity<byte[]> gerarRelatorioExcel (HttpServletResponse response) throws IOException;
}
