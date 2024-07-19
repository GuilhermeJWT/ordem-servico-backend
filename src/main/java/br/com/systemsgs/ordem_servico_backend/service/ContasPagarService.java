package br.com.systemsgs.ordem_servico_backend.service;

import br.com.systemsgs.ordem_servico_backend.dto.ContasPagarResponse;
import br.com.systemsgs.ordem_servico_backend.dto.ModelContasPagarDTO;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasPagar;

import java.util.List;

public interface ContasPagarService {

    ContasPagarResponse pesquisaPorId(Long id);

    List<ModelContasPagar> listarContasPagar();

    ModelContasPagar cadastrarContasPagar(ModelContasPagarDTO modelContasPagarDTO);

    void deletarContasPagar(Long id);

    ModelContasPagar alterarContasPAgar(Long id, ModelContasPagarDTO modelContasPagarDTO);

}
