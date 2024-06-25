package br.com.systemsgs.ordem_servico_backend.exception;

public class UsuarioNaoEncontradoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException() {
        super("Usuario não Encontrado, Forneca um Login e Senha Válidos.");
    }

}
