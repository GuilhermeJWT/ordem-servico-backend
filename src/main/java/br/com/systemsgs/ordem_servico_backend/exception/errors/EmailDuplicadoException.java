package br.com.systemsgs.ordem_servico_backend.exception.errors;

public class EmailDuplicadoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public EmailDuplicadoException() {
        super("E-mail já existente! Informe outro.");
    }

}
