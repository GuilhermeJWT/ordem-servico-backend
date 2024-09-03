package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelVendasDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;
import br.com.systemsgs.ordem_servico_backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UtilProdutos {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public UtilProdutos(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void baixaEstoqueProdutos(ModelVendasDTO modelVendasDTO){
        var produtosVenda = produtoRepository.findAllById(modelVendasDTO.getItens().stream()
                .map(idsProdutos -> idsProdutos.getIdProduto()).collect(Collectors.toList()));
        var quantidades = modelVendasDTO.getItens().stream()
                .map(quantidadeProdutos -> quantidadeProdutos.getQuantidade()).toList();

        ListIterator<ModelProdutos> iterator = produtosVenda.listIterator();
        while (iterator.hasNext()) {
            int index = iterator.nextIndex();
            ModelProdutos produto = iterator.next();
            Integer quantidade = quantidades.get(index);

            produto.setQuantidade(produto.getQuantidade() - quantidade);
            produtoRepository.save(produto);
        }
    }

    public ModelProdutos pesquisaProdutoPorId(Long id){
        return produtoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException());
    }

    public List<ModelProdutos> pesquisaListaProdutosPorIds(List<Long> id){
        var pesquisaProduto = produtoRepository.findAllById(id);

        if(pesquisaProduto.isEmpty()){
            throw new RecursoNaoEncontradoException();
        }

        return pesquisaProduto.stream().toList();
    }

    public List<String> pesquisaDescricaoProdutosPorIds(List<Long> id){

        var pesquisaProdutos = produtoRepository.findAllById(id);

        if(pesquisaProdutos.isEmpty()){
            throw new RecursoNaoEncontradoException();
        }

        return pesquisaProdutos.stream().map(p -> p.getDescricao()).toList();
    }

    public Optional<Integer> somaEstoqueAtualProdutos(){
        return produtoRepository.somaEstoqueAtual();
    }
}
