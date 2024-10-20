package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.dto.response.ContasPagarResponse;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelContasPagarDTO;
import br.com.systemsgs.ordem_servico_backend.service.ContasPagarService;
import br.com.systemsgs.ordem_servico_backend.service.GerarRelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static br.com.systemsgs.ordem_servico_backend.config.SwaggerConfiguration.TAG_API_CONTAS_PAGAR;

@Tag(name = TAG_API_CONTAS_PAGAR)
@RestController
@RequestMapping("/api/contaspagar/v1")
public class ContasPagarController {

    private final ContasPagarService contasPagarService;
    private final GerarRelatorioService gerarRelatorioService;

    @Autowired
    public ContasPagarController(ContasPagarService contasPagarService,
                                 @Qualifier("contasPagarServiceImpl") GerarRelatorioService gerarRelatorioService) {
        this.contasPagarService = contasPagarService;
        this.gerarRelatorioService = gerarRelatorioService;
    }

    @Operation(summary = "Listar Contas a Pagar", description = "Api para listar todos os registro de Contas a Pagar")
    @GetMapping("/listar")
    public ResponseEntity<List<ContasPagarResponse>> listarContasPagar(){
        return ResponseEntity.ok().body(contasPagarService.listarContasPagar());
    }

    @Operation(summary = "Listar Contas a Pagar Paginada", description = "Api para listar Contas a Pagar Paginada - Padrão (10) Contas")
    @GetMapping(value = "/listar/v2")
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

    @Secured({"ROLE_ADMIN"})
    @Operation(summary = "Deletar Contas a Pagar", description = "Api para Deletar uma Conta a Pagar por ID")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ModelContasPagarDTO> delete(@PathVariable Long id){
        contasPagarService.deletarContasPagar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatorio/excel")
    public ResponseEntity<byte[]> gerarRelatorioExcel(HttpServletResponse response) throws IOException {
        return gerarRelatorioService.gerarRelatorioExcel(response);
    }

    @GetMapping("/relatorio/pdf")
    public ResponseEntity<byte[]> gerarRelatorioPdf(HttpServletResponse response) throws IOException {
        byte[] pdfRelatorio = gerarRelatorioService.gerarRelatorioPdf();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=relatorio-contas-pagar.pdf");

        return new ResponseEntity<>(pdfRelatorio, headers, HttpStatus.OK);
    }
}
