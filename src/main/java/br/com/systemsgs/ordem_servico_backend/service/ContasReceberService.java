package br.com.systemsgs.ordem_servico_backend.service;

import br.com.systemsgs.ordem_servico_backend.dto.ModelContasReceberDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.ContasReceberResponse;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasReceber;

import java.util.List;

public interface ContasReceberService {

    ContasReceberResponse pesquisaPorId(Long id);

    List<ModelContasReceber> listarContasReceber();

    ModelContasReceber cadastrarContasReceber(ModelContasReceberDTO modelContasReceberDTO);

    void deletarContasReceber(Long id);

    ModelContasReceber alterarContasReceber(Long id, ModelContasReceberDTO modelContasReceberDTO);

}
