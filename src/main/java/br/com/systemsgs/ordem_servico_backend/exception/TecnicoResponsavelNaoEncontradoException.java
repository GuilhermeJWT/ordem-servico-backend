package br.com.systemsgs.ordem_servico_backend.exception;

public class TecnicoResponsavelNaoEncontradoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public TecnicoResponsavelNaoEncontradoException() {
        super("Técnico Responsavel não Encontrado!");
    }

}
