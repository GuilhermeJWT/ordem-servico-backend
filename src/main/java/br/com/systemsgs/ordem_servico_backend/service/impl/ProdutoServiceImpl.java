package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;
import br.com.systemsgs.ordem_servico_backend.repository.ProdutoRepository;
import br.com.systemsgs.ordem_servico_backend.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public ModelProdutos pesquisaPorId(Long id) {
        Optional<ModelProdutos> modelProdutos = produtoRepository.findById(id);
        return modelProdutos.orElseThrow(() -> new RecursoNaoEncontradoException());
    }

    @Override
    public List<ModelProdutos> listarProdutos() {
        return produtoRepository.findAll();
    }

    @Override
    public ModelProdutos salvarProdutos(ModelProdutos modelProdutos) {
        return produtoRepository.save(modelProdutos);
    }

    @Override
    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }
}
