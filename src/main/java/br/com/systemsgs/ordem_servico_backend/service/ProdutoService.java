package br.com.systemsgs.ordem_servico_backend.service;

import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;

import java.util.List;

public interface ProdutoService {

    ModelProdutos pesquisaPorId(Long id);

    List<ModelProdutos> listarProdutos();

    ModelProdutos salvarProdutos(ModelProdutos modelProdutos);

    void deletarProduto(Long id);

    ModelProdutos atualizarProduto(Long id, ModelProdutos modelProdutos);

}
