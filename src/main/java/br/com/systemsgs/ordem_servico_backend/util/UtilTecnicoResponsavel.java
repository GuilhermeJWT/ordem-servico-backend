package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.dto.ModelOrdemServicoDTO;
import br.com.systemsgs.ordem_servico_backend.exception.TecnicoResponsavelNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelTecnicoResponsavel;
import br.com.systemsgs.ordem_servico_backend.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilTecnicoResponsavel {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    public ModelTecnicoResponsavel pesquisarTecnicoPeloId(Long id){
        ModelTecnicoResponsavel pesquisaTecnico = tecnicoRepository.findById(id).
                orElseThrow(() -> new TecnicoResponsavelNaoEncontradoException());

        return pesquisaTecnico;
    }

    public ModelOrdemServicoDTO validaTecnicoExistente(ModelOrdemServicoDTO modelOrdemServicoDTO){
        ModelTecnicoResponsavel pesquisaTecnico = pesquisarTecnicoPeloId(modelOrdemServicoDTO.getTecnicoResponsavel().getId());
        modelOrdemServicoDTO.getTecnicoResponsavel().setNome(pesquisaTecnico.getNome());

        return modelOrdemServicoDTO;
    }
}
