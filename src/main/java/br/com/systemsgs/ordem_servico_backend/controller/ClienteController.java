package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelClientesDTO;
import br.com.systemsgs.ordem_servico_backend.dto.hateoas.ModelClientesHateoas;
import br.com.systemsgs.ordem_servico_backend.dto.response.ClienteResponse;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Api de Clientes - V1")
@RestController
@RequestMapping("/api/clientes/v1")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Listar Clientes - HATEOAS", description = "Api para listar todos os Clientes com Link (HATEOAS)")
    @GetMapping("/listar/v2/link")
    public CollectionModel<ModelClientesHateoas> listarCLientesComLink(){
        List<ModelClientesHateoas> listaCLientes = clienteService.listarClientes().
                stream().map(x -> mapper.map(x, ModelClientesHateoas.class)).collect(Collectors.toList());

        for (ModelClientesHateoas modelClientesHateoas : listaCLientes) {
            Long clienteID = modelClientesHateoas.getId();
            Link clienteLink = linkTo(methodOn(ClienteController.class).pesquisarPorId(clienteID)).withRel("Pesquisa Cliente pelo ID: ");
            modelClientesHateoas.add(clienteLink);
        }

        CollectionModel<ModelClientesHateoas> result = CollectionModel.of(listaCLientes);
        return result;
    }

    @Operation(summary = "Listar Clientes", description = "Api para listar todos os registro de Clientes")
    @GetMapping("/listar")
    public ResponseEntity<List<ClienteResponse>> listarClientes(){
        return ResponseEntity.ok().body(clienteService.listarClientes().
                stream().map(x -> mapper.map(x, ClienteResponse.class))
                .collect(Collectors.toList()));
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
}
