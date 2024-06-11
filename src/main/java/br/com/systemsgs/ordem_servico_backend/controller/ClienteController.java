package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/v1")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/listar")
    public ResponseEntity<List<ModelClientes>> listarClientes(){
       return ResponseEntity.ok().body(clienteService.listarClientes());
    }

    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<ModelClientes> pesquisarPorId(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.pesquisaPorId(id));
    }

    @PostMapping("/salvar")
    public ResponseEntity salvarCliente(@RequestBody @Valid ModelClientes modelClientes){
        ModelClientes clienteSalvo = clienteService.salvarClientes(modelClientes);

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/deletar/{id}")
    public void deletarCliente(@PathVariable Long id){
        clienteService.deletarCliente(id);
    }
}
