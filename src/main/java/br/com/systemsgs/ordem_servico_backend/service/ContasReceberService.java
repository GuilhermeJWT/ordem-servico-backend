package br.com.systemsgs.ordem_servico_backend.service;

import br.com.systemsgs.ordem_servico_backend.dto.ModelContasReceberDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.ContasReceberResponse;

import java.util.List;

public interface ContasReceberService {

    ContasReceberResponse pesquisaPorId(Long id);

    List<ContasReceberResponse> listarContasReceber();

    ContasReceberResponse cadastrarContasReceber(ModelContasReceberDTO modelContasReceberDTO);

    void deletarContasReceber(Long id);

    ContasReceberResponse alterarContasReceber(Long id, ModelContasReceberDTO modelContasReceberDTO);

}
