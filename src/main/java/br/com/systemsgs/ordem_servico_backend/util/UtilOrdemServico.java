package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilOrdemServico {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    public ModelOrdemServico pesquisaOsPorId(Long id){
        ModelOrdemServico pesquisarOS = ordemServicoRepository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException());

        return pesquisarOS;
    }

}
