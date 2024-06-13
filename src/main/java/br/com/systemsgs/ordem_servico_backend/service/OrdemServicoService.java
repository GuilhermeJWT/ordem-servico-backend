package br.com.systemsgs.ordem_servico_backend.service;

import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;

import java.util.List;

public interface OrdemServicoService {

    ModelOrdemServico pesquisaPorId(Long id);

    List<ModelOrdemServico> listarOS();

    ModelOrdemServico salvarOS(ModelOrdemServico modelOrdemServico);

    void deletarOS(Long id);

    ModelOrdemServico atualizarOS(Long id, ModelOrdemServico modelOrdemServico);

}
