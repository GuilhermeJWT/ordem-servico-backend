package br.com.systemsgs.ordem_servico_backend.service;

import br.com.systemsgs.ordem_servico_backend.dto.response.ContasPagarResponse;
import br.com.systemsgs.ordem_servico_backend.dto.ModelContasPagarDTO;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasPagar;

import java.util.List;

public interface ContasPagarService {

    ContasPagarResponse pesquisaPorId(Long id);

    List<ContasPagarResponse> listarContasPagar();

    ContasPagarResponse cadastrarContasPagar(ModelContasPagarDTO modelContasPagarDTO);

    void deletarContasPagar(Long id);

    ContasPagarResponse alterarContasPagar(Long id, ModelContasPagarDTO modelContasPagarDTO);

}
