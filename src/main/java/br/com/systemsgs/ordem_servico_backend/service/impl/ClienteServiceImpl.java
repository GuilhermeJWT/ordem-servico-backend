package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.ModelClientesDTO;
import br.com.systemsgs.ordem_servico_backend.exception.ClienteNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import br.com.systemsgs.ordem_servico_backend.service.ClienteService;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UtilClientes utilClientes;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ModelClientes pesquisaPorId(Long id) {
        Optional<ModelClientes> modelClientes = clienteRepository.findById(id);
        return modelClientes.orElseThrow(() -> new ClienteNaoEncontradoException());
    }

    @Override
    public List<ModelClientes> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public ModelClientes salvarClientes(ModelClientesDTO modelClientesDTO) {
        ModelClientes clienteSalvo = mapper.map(modelClientesDTO, ModelClientes.class);
        return clienteRepository.save(clienteSalvo);
    }

    @Override
    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public ModelClientes updateClientes(Long id, ModelClientesDTO modelClientesDTO) {
        ModelClientes clientePesquisado = utilClientes.pesquisarClientePeloId(id);
        mapper.map(modelClientesDTO, ModelClientes.class);
        BeanUtils.copyProperties(modelClientesDTO, clientePesquisado, "id");

        return clienteRepository.save(clientePesquisado);
    }
}
