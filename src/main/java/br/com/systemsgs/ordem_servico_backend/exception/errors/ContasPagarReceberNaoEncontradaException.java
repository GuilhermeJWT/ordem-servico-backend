package br.com.systemsgs.ordem_servico_backend.exception.errors;

public class ContasPagarReceberNaoEncontradaException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ContasPagarReceberNaoEncontradaException(){
        super("Conta não Encontrada!");
    }
}
