package br.com.systemsgs.ordem_servico_backend.exception.errors;

public class UsuarioNaoEncontradoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException() {
        super("E-mail ou Senha Inválido, Informe os dados Corretos!");
    }
}
