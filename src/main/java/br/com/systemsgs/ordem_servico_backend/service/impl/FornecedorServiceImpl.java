package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelFornecedorDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.FornecedorNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelFornecedor;
import br.com.systemsgs.ordem_servico_backend.repository.FornecedoresRepository;
import br.com.systemsgs.ordem_servico_backend.service.FornecedorService;
import br.com.systemsgs.ordem_servico_backend.util.UtilFornecedores;
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
import java.util.Optional;

@CacheConfig(cacheNames = "fornecedores")
@Service
public class FornecedorServiceImpl implements FornecedorService {

    private final FornecedoresRepository fornecedoresRepository;
    private final UtilFornecedores utilFornecedores;
    private final ModelMapper mapper;

    @Autowired
    public FornecedorServiceImpl(FornecedoresRepository fornecedoresRepository,
                                 UtilFornecedores utilFornecedores,
                                 ModelMapper mapper) {
        this.fornecedoresRepository = fornecedoresRepository;
        this.utilFornecedores = utilFornecedores;
        this.mapper = mapper;
    }

    @Cacheable(value = "fornecedores", key = "#id")
    @Override
    public ModelFornecedor pesquisaPorId(Long id) {
        Optional<ModelFornecedor> modelFornecedor = fornecedoresRepository.findById(id);
        return modelFornecedor.orElseThrow(() -> new FornecedorNaoEncontradoException());
    }

    @Cacheable(value = "fornecedores")
    @Override
    public List<ModelFornecedor> listarFornecedores() {
        return fornecedoresRepository.findAll();
    }

    @Transactional
    @Override
    public ModelFornecedor salvarFornecedor(ModelFornecedorDTO modelFornecedorDTO) {
        ModelFornecedor fornecedorConvertido = mapper.map(modelFornecedorDTO, ModelFornecedor.class);
        return fornecedoresRepository.save(fornecedorConvertido);
    }

    @CacheEvict(value = "fornecedores", key = "#id")
    @Override
    public void deletarFornecedor(Long id) {
        fornecedoresRepository.deleteById(id);
    }

    @CachePut(value = "fornecedores", key = "#id")
    @Override
    public ModelFornecedor updateFornecedor(Long id, ModelFornecedorDTO modelFornecedorDTO) {
        ModelFornecedor fornecedorPesquisado = utilFornecedores.pesquisarFornecedorPeloId(id);
        mapper.map(modelFornecedorDTO, ModelFornecedor.class);
        BeanUtils.copyProperties(modelFornecedorDTO, fornecedorPesquisado, "id");

        return fornecedoresRepository.save(fornecedorPesquisado);
    }
}
