package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelClientesDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ClienteNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelEndereco;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import br.com.systemsgs.ordem_servico_backend.service.ClienteService;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = "clientes")
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final UtilClientes utilClientes;
    private final ModelMapper mapper;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository, UtilClientes utilClientes, ModelMapper mapper) {
        this.clienteRepository = clienteRepository;
        this.utilClientes = utilClientes;
        this.mapper = mapper;
    }

    @Cacheable(value = "clientes", key = "#id")
    @Override
    public ModelClientes pesquisaPorId(Long id) {
        Optional<ModelClientes> modelClientes = clienteRepository.findById(id);
        return modelClientes.orElseThrow(() -> new ClienteNaoEncontradoException());
    }

    @Cacheable(value = "clientes")
    @Override
    public List<ModelClientes> listarClientes() {
        return clienteRepository.findAll();
    }

    @Transactional
    @Override
    public ModelClientes salvarClientes(ModelClientesDTO modelClientesDTO) {
        ModelClientes modelClientes = new ModelClientes();
        ModelEndereco modelEndereco = new ModelEndereco();

        modelEndereco.setEndereco(modelClientesDTO.getEndereco());
        modelEndereco.setComplemento(modelClientesDTO.getComplemento());
        modelEndereco.setCidade(modelClientesDTO.getCidade());
        modelEndereco.setEstado(modelClientesDTO.getEstado());
        modelEndereco.setCep(modelClientesDTO.getCep());
        modelClientes.setNome(modelClientesDTO.getNome());
        modelClientes.setCpf(modelClientesDTO.getCpf());
        modelClientes.setCelular(modelClientesDTO.getCelular());
        modelClientes.setEmail(modelClientesDTO.getEmail());
        modelClientes.setEndereco(modelEndereco);

        return clienteRepository.save(modelClientes);
    }

    @CacheEvict(value = "clientes", key = "#id")
    @Override
    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @CachePut(value = "clientes", key = "#id")
    @Override
    public ModelClientes updateClientes(Long id, ModelClientesDTO modelClientesDTO) {
        ModelClientes clientePesquisado = utilClientes.pesquisarClientePeloId(id);
        mapper.map(modelClientesDTO, ModelClientes.class);
        BeanUtils.copyProperties(modelClientesDTO, clientePesquisado, "id");

        return clienteRepository.save(clientePesquisado);
    }
}
