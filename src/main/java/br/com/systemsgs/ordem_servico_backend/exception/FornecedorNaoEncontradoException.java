package br.com.systemsgs.ordem_servico_backend.exception;

public class FornecedorNaoEncontradoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public FornecedorNaoEncontradoException() {
        super("Fornecedor não Encontrado!");
    }

}
