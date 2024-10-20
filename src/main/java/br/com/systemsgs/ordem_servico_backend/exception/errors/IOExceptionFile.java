package br.com.systemsgs.ordem_servico_backend.exception.errors;

public class IOExceptionFile extends RuntimeException{

    public IOExceptionFile(){
        super("Erro ao tentar gerar o Arquivo/Relatório.");
    }
}
