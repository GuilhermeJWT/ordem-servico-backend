package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.service.OrdemServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Api de Ordem de Serviço - v1")
@RestController
@RequestMapping("/api/os/v1")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService ordemServicoService;

    @Operation(summary = "Listar OS", description = "Api para listar todos os registro de Ordem de Serviço")
    @GetMapping("/listar")
    public ResponseEntity<List<ModelOrdemServico>> listarOS(){
        return ResponseEntity.ok().body(ordemServicoService.listarOS());
    }

    @Operation(summary = "Pesquisa por ID", description = "Api para listar uma OS por ID")
    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<ModelOrdemServico> pesquisarPorId(@PathVariable Long id){
        return ResponseEntity.ok(ordemServicoService.pesquisaPorId(id));
    }

    @Operation(summary = "Salvar OS", description = "Api para Salvar uma OS")
    @PostMapping("/salvar")
    public ResponseEntity<ModelOrdemServico> salvarOS(@RequestBody @Valid ModelOrdemServico modelOrdemServico){
        ModelOrdemServico osSalva = ordemServicoService.salvarOS(modelOrdemServico);

        return ResponseEntity.status(HttpStatus.CREATED).body(osSalva);
    }

    @Operation(summary = "Deletar OS", description = "Api para Deletar uma OS por ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/deletar/{id}")
    public void deletarOS(@PathVariable Long id){
        ordemServicoService.deletarOS(id);
    }

    @Operation(summary = "Atualizar OS", description = "Api para Atualizar uma OS pelo Id e Entidade")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ModelOrdemServico> atualizarOS(@PathVariable Long id, @RequestBody @Valid ModelOrdemServico modelOrdemServico){
        ModelOrdemServico osAtualizada = ordemServicoService.atualizarOS(id, modelOrdemServico);

        return ResponseEntity.ok(osAtualizada);
    }

}
