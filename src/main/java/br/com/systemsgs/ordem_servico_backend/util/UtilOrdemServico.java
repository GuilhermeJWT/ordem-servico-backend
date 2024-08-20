package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.exception.errors.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UtilOrdemServico {

    private final OrdemServicoRepository ordemServicoRepository;

    @Autowired
    public UtilOrdemServico(OrdemServicoRepository ordemServicoRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
    }

    public ModelOrdemServico pesquisaOsPorId(Long id){
        return ordemServicoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException());
    }

    public Optional<Integer> quantidadeOsRealizadas(){
        return ordemServicoRepository.quantidadeOsRealizadas();
    }

    public Optional<Integer> quantidadeOsStatusEmAndamento(){
        return ordemServicoRepository.quantidadeOsEmAndamento();
    }

}
