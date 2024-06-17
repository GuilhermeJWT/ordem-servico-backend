package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.dto.ModelOrdemServicoDTO;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.service.OrdemServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Api de Ordem de Serviço - v1")
@RestController
@RequestMapping("/api/os/v1")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService ordemServicoService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Listar OS - HATEOAS", description = "Api para listar todos os registro de Ordem de Serviço com Link (HATEOAS)")
    @GetMapping("/listar/v2/link")
    public CollectionModel<ModelOrdemServico> listarOsComLink(){
        List<ModelOrdemServico> listaOS = ordemServicoService.listarOS();

        for (ModelOrdemServico modelOrdemServico : listaOS) {
            Long osID = modelOrdemServico.getId();
            Link osLink = linkTo(methodOn(OrdemServicoController.class).pesquisarPorId(osID)).withRel("Pesquisa OS pelo ID");
            modelOrdemServico.add(osLink);
        }

        CollectionModel<ModelOrdemServico> result = CollectionModel.of(listaOS);
        return result;
    }

    @Operation(summary = "Listar OS", description = "Api para listar todos os registro de Ordem de Serviço")
    @GetMapping("/listar")
    public ResponseEntity<List<ModelOrdemServicoDTO>> listaTodasOS(){
        return ResponseEntity.ok().body(ordemServicoService.listarOS().
                stream().map(x -> mapper.map(x, ModelOrdemServicoDTO.class))
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Pesquisa por ID", description = "Api para listar uma OS por ID")
    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<ModelOrdemServicoDTO> pesquisarPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(ordemServicoService.pesquisaPorId(id), ModelOrdemServicoDTO.class));
    }

    @Operation(summary = "Salvar OS", description = "Api para Salvar uma OS")
    @PostMapping("/salvar")
    public ResponseEntity<ModelOrdemServicoDTO> salvarOS(@RequestBody @Valid ModelOrdemServicoDTO modelOrdemServicoDTO){
        ModelOrdemServico osSalva = ordemServicoService.salvarOS(modelOrdemServicoDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(ordemServicoService.pesquisaPorId(osSalva.getId())).toUri();

        return ResponseEntity.created(uri).body(mapper.map(osSalva, ModelOrdemServicoDTO.class));
    }

    @Operation(summary = "Atualizar OS", description = "Api para Atualizar uma OS pelo Id e Entidade")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ModelOrdemServicoDTO> atualizarOS(@PathVariable Long id, @RequestBody @Valid ModelOrdemServicoDTO modelOrdemServicoDTO){
        modelOrdemServicoDTO.setId(id);
        ModelOrdemServico osAtualizada = ordemServicoService.atualizarOS(id, modelOrdemServicoDTO);

        return ResponseEntity.ok().body(mapper.map(osAtualizada, ModelOrdemServicoDTO.class));
    }

    @Operation(summary = "Deletar OS", description = "Api para Deletar uma OS por ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/deletar/{id}")
    public void deletarOS(@PathVariable Long id){
        ordemServicoService.deletarOS(id);
    }
}
