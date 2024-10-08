package br.com.systemsgs.ordem_servico_backend.relatorios.pdf;

import java.io.IOException;

public interface GerarRelatorioPdf {

    byte[] gerarRelatorioPdf() throws IOException;

}
