package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Api de Clientes - v1")
@RestController
@RequestMapping("/api/clientes/v1")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Listar Clientes - HATEOAS", description = "Api para listar todos os Clientes com Link (HATEOAS)")
    @GetMapping("/listar/v2/link")
    public CollectionModel<ModelClientes> listarCLientesComLink(){
        List<ModelClientes> listaCLientes = clienteService.listarClientes();

        for (ModelClientes modelClientes : listaCLientes) {
            Long clienteID = modelClientes.getId();
            Link clienteLink = linkTo(methodOn(ClienteController.class).pesquisarPorId(clienteID)).withRel("Pesquisa Cliente pelo ID");
            modelClientes.add(clienteLink);
        }

        CollectionModel<ModelClientes> result = CollectionModel.of(listaCLientes);
        return result;
    }

    @Operation(summary = "Listar Clientes", description = "Api para listar todos os registro de Clientes")
    @GetMapping("/listar")
    public ResponseEntity<List<ModelClientes>> listarClientes(){
       return ResponseEntity.ok().body(clienteService.listarClientes());
    }

    @Operation(summary = "Pesquisa por ID", description = "Api para listar um Cliente por ID")
    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<ModelClientes> pesquisarPorId(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.pesquisaPorId(id));
    }

    @Operation(summary = "Salvar Clientes", description = "Api para Salvar um Cliente")
    @PostMapping("/salvar")
    public ResponseEntity salvarCliente(@RequestBody @Valid ModelClientes modelClientes){
        ModelClientes clienteSalvo = clienteService.salvarClientes(modelClientes);

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @Operation(summary = "Deletar Clientes", description = "Api para Deletar um Cliente por ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/deletar/{id}")
    public void deletarCliente(@PathVariable Long id){
        clienteService.deletarCliente(id);
    }

    @Operation(summary = "Atualizar Clientes", description = "Api para Atualizar um Cliente pelo Id e Entidade")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ModelClientes> atualizarClientes(@PathVariable Long id, @RequestBody @Valid ModelClientes modelClientes){
        ModelClientes clienteAtualizado = clienteService.updateClientes(id, modelClientes);

        return ResponseEntity.ok(clienteAtualizado);
    }
}
