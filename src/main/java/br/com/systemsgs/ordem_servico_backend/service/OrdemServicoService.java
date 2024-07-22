package br.com.systemsgs.ordem_servico_backend.service;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelOrdemServicoDTO;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;

import java.util.List;

public interface OrdemServicoService {

    ModelOrdemServico pesquisaPorId(Long id);

    List<ModelOrdemServico> listarOS();

    ModelOrdemServico salvarOS(ModelOrdemServicoDTO modelOrdemServicoDTO);

    void deletarOS(Long id);

    ModelOrdemServico atualizarOS(Long id, ModelOrdemServicoDTO modelOrdemServicoDTO);

}
