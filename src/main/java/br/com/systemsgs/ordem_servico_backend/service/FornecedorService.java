package br.com.systemsgs.ordem_servico_backend.service;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelFornecedorDTO;
import br.com.systemsgs.ordem_servico_backend.model.ModelFornecedor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FornecedorService {

    ModelFornecedor pesquisaPorId(Long id);

    List<ModelFornecedor> listarFornecedores();

    ModelFornecedor salvarFornecedor(ModelFornecedorDTO modelFornecedorDTO);

    void deletarFornecedor(Long id);

    ModelFornecedor updateFornecedor(Long id, ModelFornecedorDTO modelFornecedorDTO);

    Page<ModelFornecedorDTO> listarFornecedoresPaginados(int page, int size);
}
