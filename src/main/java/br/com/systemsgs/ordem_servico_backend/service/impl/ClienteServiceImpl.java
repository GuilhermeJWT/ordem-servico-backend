package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelClientesDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.ClienteResponse;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ClienteNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelEndereco;
import br.com.systemsgs.ordem_servico_backend.repository.ClienteRepository;
import br.com.systemsgs.ordem_servico_backend.service.ClienteService;
import br.com.systemsgs.ordem_servico_backend.service.GerarRelatorioService;
import br.com.systemsgs.ordem_servico_backend.util.BaseRelatorioUtil;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CacheConfig(cacheNames = "clientes")
@Service
public class ClienteServiceImpl extends BaseRelatorioUtil implements ClienteService, GerarRelatorioService {

    private static final String NOME_RELATORIO = "Relatório de Clientes";
    private static final String[] COLUNAS = {"ID", "Nome", "Email", "Telefone", "Cidade", "Estado", "Cep", "Ativo"};

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
        return clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException());
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

    @Override
    public Page<ClienteResponse> listarClientesPaginado(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return clienteRepository.findAll(pageable).map(clientes -> mapper.map(clientes, ClienteResponse.class));
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

    @Override
    public ResponseEntity<byte[]> gerarRelatorioExcel(HttpServletResponse response) throws IOException {
        List<ModelClientes> clientes = clienteRepository.findAll();

        byte[] excelData = configuraRelatorioExcel(NOME_RELATORIO,clientes, COLUNAS, (row, cliente) -> {
            row.createCell(0).setCellValue(cliente.getId());
            row.createCell(1).setCellValue(cliente.getNome());
            row.createCell(2).setCellValue(cliente.getEmail());
            row.createCell(3).setCellValue(cliente.getCelular());
            row.createCell(4).setCellValue(cliente.getEndereco().getCidade());
            row.createCell(5).setCellValue(cliente.getEndereco().getEndereco());
            row.createCell(6).setCellValue(cliente.getEndereco().getCep());
            row.createCell(7).setCellValue(cliente.isAtivo() ? "Ativo" : "Inativo");
        });

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=relatorio-clientes.xlsx");

        return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }

    public byte[] gerarRelatorioPdf() throws IOException {
        List<ModelClientes> clientes = clienteRepository.findAll();
        List<String[]> dados = new ArrayList<>();
        float[] tamanhoColunas = {1, 3, 3, 2, 2, 2, 2, 2};

        for (ModelClientes cliente : clientes) {
            String[] linha = {
                    String.valueOf(cliente.getId()),
                    cliente.getNome(),
                    cliente.getEmail(),
                    cliente.getCelular(),
                    cliente.getEndereco().getCidade(),
                    cliente.getEndereco().getEndereco(),
                    cliente.getEndereco().getCep(),
                    cliente.isAtivo() ? "Ativo" : "Inativo"
            };
            dados.add(linha);
        }

        return configuraRelatorioPdf(NOME_RELATORIO, COLUNAS, dados, tamanhoColunas);
    }
}
