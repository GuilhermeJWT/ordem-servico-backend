package br.com.systemsgs.ordem_servico_backend.exception.errors;

public class ClienteNaoEncontradoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ClienteNaoEncontradoException() {
        super("Cliente não Encontrado!");
    }

}
