package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.ModelOrdemServicoDTO;
import br.com.systemsgs.ordem_servico_backend.exception.ClienteNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.exception.RecursoNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.repository.OrdemServicoRepository;
import br.com.systemsgs.ordem_servico_backend.service.OrdemServicoService;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import br.com.systemsgs.ordem_servico_backend.util.UtilOrdemServico;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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
         validaCliente(modelOrdemServicoDTO);
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
        validaCliente(modelOrdemServicoDTO);

        mapper.map(modelOrdemServicoDTO, ModelClientes.class);
        BeanUtils.copyProperties(modelOrdemServicoDTO, osPesquisada, "id");

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
        modelOrdemServicoDTO.getCliente().setCidade(pesquisaCliente.getCidade());
        modelOrdemServicoDTO.getCliente().setEstado(pesquisaCliente.getEstado());
        modelOrdemServicoDTO.getCliente().setCep(pesquisaCliente.getCep());

        return modelOrdemServicoDTO;
    }
}
