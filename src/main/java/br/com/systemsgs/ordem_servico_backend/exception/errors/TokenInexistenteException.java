package br.com.systemsgs.ordem_servico_backend.exception.errors;

public class TokenInexistenteException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public TokenInexistenteException() {
        super("Token Inexistente!");
    }
}
