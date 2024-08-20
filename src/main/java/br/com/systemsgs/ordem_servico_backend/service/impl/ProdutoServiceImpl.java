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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "produtos")
@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final UtilProdutos utilProdutos;
    private final ModelMapper mapper;

    @Autowired
    public ProdutoServiceImpl(ProdutoRepository produtoRepository, UtilProdutos utilProdutos, ModelMapper mapper) {
        this.produtoRepository = produtoRepository;
        this.utilProdutos = utilProdutos;
        this.mapper = mapper;
    }

    @Cacheable(value = "produtos", key = "#id")
    @Override
    public ModelProdutos pesquisaPorId(Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException());
    }

    @Cacheable(value = "produtos")
    @Override
    public List<ModelProdutos> listarProdutos() {
        return produtoRepository.findAll();
    }

    @Transactional
    @Override
    public ModelProdutos salvarProdutos(ModelProdutosDTO modelProdutosDTO) {
        return produtoRepository.save(mapper.map(modelProdutosDTO, ModelProdutos.class));
    }

    @CacheEvict(value = "produtos", key = "#id")
    @Override
    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    @CachePut(value = "produtos", key = "#id")
    @Override
    public ModelProdutos atualizarProduto(Long id, ModelProdutosDTO modelProdutosDTO) {
        ModelProdutos produtoPesquisado = utilProdutos.pesquisaProdutoPorId(id);
        mapper.map(modelProdutosDTO, ModelProdutos.class);
        BeanUtils.copyProperties(modelProdutosDTO, produtoPesquisado, "id");

        return produtoRepository.save(produtoPesquisado);
    }
}
