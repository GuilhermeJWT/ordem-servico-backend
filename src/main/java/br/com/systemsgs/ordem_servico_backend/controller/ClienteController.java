package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelClientesDTO;
import br.com.systemsgs.ordem_servico_backend.dto.hateoas.ModelClientesHateoas;
import br.com.systemsgs.ordem_servico_backend.dto.response.ClienteResponse;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.relatorios.GerarRelatorio;
import br.com.systemsgs.ordem_servico_backend.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.systemsgs.ordem_servico_backend.config.SwaggerConfiguration.TAG_API_CLIENTES;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = TAG_API_CLIENTES)
@RestController
@RequestMapping("/api/clientes/v1")
public class ClienteController {

    private final ClienteService clienteService;
    private final ModelMapper mapper;
    private final GerarRelatorio gerarRelatorio;

    @Autowired
    public ClienteController(ClienteService clienteService, ModelMapper mapper, GerarRelatorio gerarRelatorio) {
        this.clienteService = clienteService;
        this.mapper = mapper;
        this.gerarRelatorio = gerarRelatorio;
    }

    @Operation(summary = "Listar Clientes", description = "Api para listar todos os registro de Clientes")
    @GetMapping("/listar/v1")
    public ResponseEntity<List<ClienteResponse>> listarClientes(){
        return ResponseEntity.ok().body(clienteService.listarClientes().
                stream().map(x -> mapper.map(x, ClienteResponse.class))
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Listar Clientes Paginado", description = "Api para listar Clientes Paginados - Padrão (10) Clientes")
    @GetMapping(value = "/listar/v2")
    public Page<ClienteResponse> listarClientesPaginados(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return clienteService.listarClientesPaginado(page, size);
    }

    @Operation(summary = "Listar Clientes - HATEOAS", description = "Api para listar todos os Clientes com Link (HATEOAS)")
    @GetMapping("/listar/v3/link")
    public CollectionModel<ModelClientesHateoas> listarCLientesComLink(){
        List<ModelClientesHateoas> listaCLientes = clienteService.listarClientes().
                stream().map(x -> mapper.map(x, ModelClientesHateoas.class)).toList();

        for (ModelClientesHateoas modelClientesHateoas : listaCLientes) {
            Long clienteID = modelClientesHateoas.getId();
            Link clienteLink = linkTo(methodOn(ClienteController.class).pesquisarPorId(clienteID)).withRel("Pesquisa Cliente pelo ID: ");
            modelClientesHateoas.add(clienteLink);
        }

        return CollectionModel.of(listaCLientes);
    }

    @Operation(summary = "Pesquisa por ID", description = "Api para listar um Cliente por ID")
    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<ClienteResponse> pesquisarPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(clienteService.pesquisaPorId(id), ClienteResponse.class));
    }

    @Operation(summary = "Salvar Clientes", description = "Api para Salvar um Cliente")
    @PostMapping("/salvar")
    public ResponseEntity<ClienteResponse> salvarCliente(@RequestBody @Valid ModelClientesDTO modelClientesDTO){
        ModelClientes clienteSalvo = clienteService.salvarClientes(modelClientesDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(clienteService.pesquisaPorId(clienteSalvo.getId())).toUri();

        return ResponseEntity.created(uri).body(mapper.map(clienteSalvo, ClienteResponse.class));
    }

    @Operation(summary = "Atualizar Clientes", description = "Api para Atualizar um Cliente pelo Id e Entidade")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ClienteResponse> atualizarClientes(@PathVariable Long id, @RequestBody @Valid ModelClientesDTO modelClientesDTO){
        modelClientesDTO.setId(id);
        ModelClientes clienteAtualizado = clienteService.updateClientes(id, modelClientesDTO);

        return ResponseEntity.ok().body(mapper.map(clienteAtualizado, ClienteResponse.class));
    }

    @Operation(summary = "Deletar Clientes", description = "Api para Deletar um Cliente por ID")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ModelClientesDTO> delete(@PathVariable Long id){
        clienteService.deletarCliente(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatorio/excel")
    public ResponseEntity<byte[]> gerarRelatorioExcel(HttpServletResponse response) throws IOException {
        return gerarRelatorio.gerarRelatorioExcel(response);
    }

    @GetMapping("/relatorio/pdf")
    public ResponseEntity<byte[]> gerarRelatorioPdf(HttpServletResponse response) throws IOException {
        byte[] pdfRelatorio = gerarRelatorio.gerarRelatorioPdf();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=relatorio-clientes.pdf");

        return new ResponseEntity<>(pdfRelatorio, headers, HttpStatus.OK);
    }
}
