package br.com.systemsgs.ordem_servico_backend.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface GerarRelatorioService {

    ResponseEntity<byte[]> gerarRelatorioExcel (HttpServletResponse response) throws IOException;

    byte[] gerarRelatorioPdf() throws IOException;
}
