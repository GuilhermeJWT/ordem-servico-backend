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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return fornecedoresRepository.findById(id).orElseThrow(() -> new FornecedorNaoEncontradoException());
    }

    @Cacheable(value = "fornecedores")
    @Override
    public List<ModelFornecedor> listarFornecedores() {
        return fornecedoresRepository.findAll();
    }

    @Override
    public Page<ModelFornecedorDTO> listarFornecedoresPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return fornecedoresRepository.findAll(pageable).map(fornecedores -> mapper.map(fornecedores, ModelFornecedorDTO.class));
    }

    @Transactional
    @Override
    public ModelFornecedor salvarFornecedor(ModelFornecedorDTO modelFornecedorDTO) {
        return fornecedoresRepository.save(mapper.map(modelFornecedorDTO, ModelFornecedor.class));
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
