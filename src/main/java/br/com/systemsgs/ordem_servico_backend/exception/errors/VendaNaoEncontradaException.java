package br.com.systemsgs.ordem_servico_backend.exception.errors;

public class VendaNaoEncontradaException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public VendaNaoEncontradaException() {
        super("Venda não Encontrada!");
    }

}
