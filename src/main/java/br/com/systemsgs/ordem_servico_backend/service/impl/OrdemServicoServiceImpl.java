package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.ModelOrdemServicoDTO;
import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.repository.OrdemServicoRepository;
import br.com.systemsgs.ordem_servico_backend.service.OrdemServicoService;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import br.com.systemsgs.ordem_servico_backend.util.UtilOrdemServico;
import br.com.systemsgs.ordem_servico_backend.util.UtilTecnicoResponsavel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdemServicoServiceImpl implements OrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private UtilOrdemServico utilOrdemServico;

    @Autowired
    private UtilClientes utilClientes;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UtilTecnicoResponsavel utilTecnicoResponsavel;

    @Override
    public ModelOrdemServico pesquisaPorId(Long id) {
        Optional<ModelOrdemServico> modelOrdemServico = ordemServicoRepository.findById(id);
        return modelOrdemServico.orElseThrow(() -> new RecursoNaoEncontradoException());
    }

    @Override
    public List<ModelOrdemServico> listarOS() {
        return ordemServicoRepository.findAll();
    }

    @Override
    public ModelOrdemServico salvarOS(ModelOrdemServicoDTO modelOrdemServicoDTO) {
         utilClientes.validaCliente(modelOrdemServicoDTO);
         utilTecnicoResponsavel.validaTecnicoExistente(modelOrdemServicoDTO);
         ModelOrdemServico osConvertida = mapper.map(modelOrdemServicoDTO, ModelOrdemServico.class);

        return ordemServicoRepository.save(osConvertida);
    }

    @Override
    public void deletarOS(Long id) {
        ordemServicoRepository.deleteById(id);
    }

    @Override
    public ModelOrdemServico atualizarOS(Long id, ModelOrdemServicoDTO modelOrdemServicoDTO) {
        ModelOrdemServico osPesquisada = utilOrdemServico.pesquisaOsPorId(id);
        utilClientes.validaCliente(modelOrdemServicoDTO);
        utilTecnicoResponsavel.validaTecnicoExistente(modelOrdemServicoDTO);

        mapper.map(modelOrdemServicoDTO, ModelClientes.class);

        return ordemServicoRepository.save(osPesquisada);
    }
}
