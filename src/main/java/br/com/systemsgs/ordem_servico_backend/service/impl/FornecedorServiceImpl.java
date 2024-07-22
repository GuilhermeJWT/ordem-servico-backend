package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelFornecedorDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.FornecedorNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelFornecedor;
import br.com.systemsgs.ordem_servico_backend.repository.FornecedoresRepository;
import br.com.systemsgs.ordem_servico_backend.service.FornecedorService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorServiceImpl implements FornecedorService {

    @Autowired
    private FornecedoresRepository fornecedoresRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ModelFornecedor pesquisaPorId(Long id) {
        Optional<ModelFornecedor> modelFornecedor = fornecedoresRepository.findById(id);
        return modelFornecedor.orElseThrow(() -> new FornecedorNaoEncontradoException());
    }

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

    @Override
    public void deletarFornecedor(Long id) {
        fornecedoresRepository.deleteById(id);
    }

    @Override
    public ModelFornecedor updateFornecedor(Long id, ModelFornecedorDTO modelFornecedorDTO) {
        ModelFornecedor fornecedorPesquisado = pesquisaFornecedorPeloId(id);
        mapper.map(modelFornecedorDTO, ModelFornecedor.class);
        BeanUtils.copyProperties(modelFornecedorDTO, fornecedorPesquisado, "id");

        return fornecedoresRepository.save(fornecedorPesquisado);
    }

    public ModelFornecedor pesquisaFornecedorPeloId(Long id){
       ModelFornecedor pesquisaFornecedor = fornecedoresRepository.findById(id)
               .orElseThrow(() -> new FornecedorNaoEncontradoException());

       return pesquisaFornecedor;
    }
}
