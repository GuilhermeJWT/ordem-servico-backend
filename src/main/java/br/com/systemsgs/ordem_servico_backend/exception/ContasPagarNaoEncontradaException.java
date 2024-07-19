package br.com.systemsgs.ordem_servico_backend.exception;

public class ContasPagarNaoEncontradaException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ContasPagarNaoEncontradaException(){
        super("Contas a Pagar não Encontrada.");
    }
}
