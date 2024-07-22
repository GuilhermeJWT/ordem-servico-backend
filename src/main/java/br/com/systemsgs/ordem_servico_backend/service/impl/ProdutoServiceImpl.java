package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelProdutosDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;
import br.com.systemsgs.ordem_servico_backend.repository.ProdutoRepository;
import br.com.systemsgs.ordem_servico_backend.service.ProdutoService;
import br.com.systemsgs.ordem_servico_backend.util.UtilProdutos;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UtilProdutos utilProdutos;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ModelProdutos pesquisaPorId(Long id) {
        Optional<ModelProdutos> modelProdutos = produtoRepository.findById(id);
        return modelProdutos.orElseThrow(() -> new RecursoNaoEncontradoException());
    }

    @Override
    public List<ModelProdutos> listarProdutos() {
        return produtoRepository.findAll();
    }

    @Transactional
    @Override
    public ModelProdutos salvarProdutos(ModelProdutosDTO modelProdutosDTO) {
        ModelProdutos produtoConvertido = mapper.map(modelProdutosDTO, ModelProdutos.class);
        return produtoRepository.save(produtoConvertido);
    }

    @Override
    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    @Override
    public ModelProdutos atualizarProduto(Long id, ModelProdutosDTO modelProdutosDTO) {
        ModelProdutos produtoPesquisado = utilProdutos.pesquisaProdutoPorId(id);
        mapper.map(modelProdutosDTO, ModelProdutos.class);
        BeanUtils.copyProperties(modelProdutosDTO, produtoPesquisado, "id");

        return produtoRepository.save(produtoPesquisado);
    }
}
