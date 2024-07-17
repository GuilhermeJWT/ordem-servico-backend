package br.com.systemsgs.ordem_servico_backend.exception;

public class TokenInvalidoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public TokenInvalidoException() {
        super("Token inválido ou expirado!");
    }
}
