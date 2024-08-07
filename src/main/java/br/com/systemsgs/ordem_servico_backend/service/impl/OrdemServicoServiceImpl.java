package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelOrdemServicoDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ClienteNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.exception.errors.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.repository.OrdemServicoRepository;
import br.com.systemsgs.ordem_servico_backend.service.OrdemServicoService;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import br.com.systemsgs.ordem_servico_backend.util.UtilOrdemServico;
import br.com.systemsgs.ordem_servico_backend.util.UtilTecnicoResponsavel;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = "os")
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

    @Cacheable(value = "os", key = "#id")
    @Override
    public ModelOrdemServico pesquisaPorId(Long id) {
        Optional<ModelOrdemServico> modelOrdemServico = ordemServicoRepository.findById(id);
        return modelOrdemServico.orElseThrow(() -> new RecursoNaoEncontradoException());
    }

    @Cacheable(value = "os")
    @Override
    public List<ModelOrdemServico> listarOS() {
        return ordemServicoRepository.findAll();
    }

    @Transactional
    @Override
    public ModelOrdemServico salvarOS(ModelOrdemServicoDTO modelOrdemServicoDTO) {
         validaCliente(modelOrdemServicoDTO);
         utilTecnicoResponsavel.validaTecnicoExistente(modelOrdemServicoDTO);
         ModelOrdemServico osConvertida = mapper.map(modelOrdemServicoDTO, ModelOrdemServico.class);

        return ordemServicoRepository.save(osConvertida);
    }

    @CacheEvict(value = "os", key = "#id")
    @Override
    public void deletarOS(Long id) {
        ordemServicoRepository.deleteById(id);
    }

    @CachePut(value = "os", key = "#id")
    @Override
    public ModelOrdemServico atualizarOS(Long id, ModelOrdemServicoDTO modelOrdemServicoDTO) {
        ModelOrdemServico osPesquisada = utilOrdemServico.pesquisaOsPorId(id);
        validaCliente(modelOrdemServicoDTO);
        utilTecnicoResponsavel.validaTecnicoExistente(modelOrdemServicoDTO);

        mapper.map(modelOrdemServicoDTO, ModelClientes.class);

        return ordemServicoRepository.save(osPesquisada);
    }

    public ModelOrdemServicoDTO validaCliente(ModelOrdemServicoDTO modelOrdemServicoDTO){
        ModelClientes pesquisaCliente = utilClientes.pesquisarClientePeloId(modelOrdemServicoDTO.getCliente().getId());

        if(pesquisaCliente == null){
            throw new ClienteNaoEncontradoException();
        }
        modelOrdemServicoDTO.getCliente().setNome(pesquisaCliente.getNome());
        modelOrdemServicoDTO.getCliente().setCpf(pesquisaCliente.getCpf());
        modelOrdemServicoDTO.getCliente().setCelular(pesquisaCliente.getCelular());
        modelOrdemServicoDTO.getCliente().setEmail(pesquisaCliente.getEmail());
        modelOrdemServicoDTO.getCliente().setEndereco(pesquisaCliente.getEndereco());

        return modelOrdemServicoDTO;
    }
}
