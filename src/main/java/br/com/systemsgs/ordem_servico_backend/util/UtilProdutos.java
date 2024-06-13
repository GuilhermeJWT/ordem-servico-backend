package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;
import br.com.systemsgs.ordem_servico_backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilProdutos {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ModelProdutos pesquisaProdutoPorId(Long id){
        ModelProdutos pesquisaProduto = produtoRepository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException());

        return pesquisaProduto;
    }
}
