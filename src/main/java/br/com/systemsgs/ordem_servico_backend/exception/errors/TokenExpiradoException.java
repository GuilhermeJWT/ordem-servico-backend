package br.com.systemsgs.ordem_servico_backend.exception.errors;

public class TokenExpiradoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public TokenExpiradoException() {
        super("Token inválido ou expirado!");
    }
}
