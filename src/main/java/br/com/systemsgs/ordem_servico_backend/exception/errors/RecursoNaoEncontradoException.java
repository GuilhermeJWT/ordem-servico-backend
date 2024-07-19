package br.com.systemsgs.ordem_servico_backend.exception.errors;

public class RecursoNaoEncontradoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public RecursoNaoEncontradoException() {
        super("Recurso não Encontrado!");
    }

}
