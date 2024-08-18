package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.dto.response.ClienteResponse;
import br.com.systemsgs.ordem_servico_backend.dto.response.ContasPagarResponse;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelContasPagarDTO;
import br.com.systemsgs.ordem_servico_backend.service.ContasPagarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static br.com.systemsgs.ordem_servico_backend.config.SwaggerConfiguration.TAG_API_CONTAS_PAGAR;

@Tag(name = TAG_API_CONTAS_PAGAR)
@RestController
@RequestMapping("/api/contaspagar/v1")
public class ContasPagarController {

    private final ContasPagarService contasPagarService;

    @Autowired
    public ContasPagarController(ContasPagarService contasPagarService) {
        this.contasPagarService = contasPagarService;
    }

    @Operation(summary = "Listar Contas a Pagar", description = "Api para listar todos os registro de Contas a Pagar")
    @GetMapping("/listar")
    public ResponseEntity<List<ContasPagarResponse>> listarContasPagar(){
        return ResponseEntity.ok().body(contasPagarService.listarContasPagar());
    }

    @Operation(summary = "Listar Contas a Pagar Paginada", description = "Api para listar Contas a Pagadar Paginados - Padrão (10) Contas")
    @GetMapping(value = "/listar/v2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ContasPagarResponse> listarContasPagarPaginada(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return contasPagarService.listarContasPagarPaginada(page, size);
    }

    @Operation(summary = "Pesquisa por ID", description = "Api para listar uma Conta a Pagar por ID")
    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<ContasPagarResponse> pesquisarPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(contasPagarService.pesquisaPorId(id));
    }

    @Operation(summary = "Cadastrar Contas a Pagar", description = "Api para Salvar uma Conta a Pagar")
    @PostMapping("/salvar")
    public ResponseEntity<ContasPagarResponse> salvarContasPagar(@RequestBody @Valid ModelContasPagarDTO modelContasPagarDTO){
        var contaPagarSalva = contasPagarService.cadastrarContasPagar(modelContasPagarDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(contasPagarService.pesquisaPorId(contaPagarSalva.getId())).toUri();

        return ResponseEntity.created(uri).body(contaPagarSalva);
    }

    @Operation(summary = "Atualizar uma Conta a Pagar", description = "Api para Atualizar uma Conta a Pagar pelo Id e Entidade")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ContasPagarResponse> atualizarContasPagar(@PathVariable Long id, @RequestBody @Valid ModelContasPagarDTO modelContasPagarDTO){
        modelContasPagarDTO.setId(id);

        return ResponseEntity.ok().body(contasPagarService.alterarContasPagar(id, modelContasPagarDTO));
    }

    @Operation(summary = "Deletar Contas a Pagar", description = "Api para Deletar uma Conta a Pagar por ID")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ModelContasPagarDTO> delete(@PathVariable Long id){
        contasPagarService.deletarContasPagar(id);

        return ResponseEntity.noContent().build();
    }
}
