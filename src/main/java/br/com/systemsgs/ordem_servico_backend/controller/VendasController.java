package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelVendasDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.VendasResponse;
import br.com.systemsgs.ordem_servico_backend.model.ModelVendas;
import br.com.systemsgs.ordem_servico_backend.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static br.com.systemsgs.ordem_servico_backend.config.SwaggerConfiguration.TAG_API_VENDAS;

@Tag(name = TAG_API_VENDAS)
@RestController
@RequestMapping(value = "/api/v1/vendas")
public class VendasController {

    private final VendaService vendaService;

    @Autowired
    public VendasController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @Operation(summary = "Realizar uma Venda", description = "Api para realizar uma Venda dos Produtos da Assitência Técnica.")
    @PostMapping("/salvar")
    public ResponseEntity<ModelVendas>salvarVenda(@RequestBody @Valid ModelVendasDTO modelVendasDTO){
        ModelVendas vendaSalva = vendaService.salvarVenda(modelVendasDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(vendaService.pesquisaVendaPorId(vendaSalva.getIdVenda())).toUri();

        return ResponseEntity.created(uri).body(vendaSalva);
    }

    @Operation(summary = "Pesquisa Venda por ID", description = "Api para listar uma Venda por ID")
    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<VendasResponse> pesquisarPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(vendaService.pesquisaVendaPorId(id));
    }
}
