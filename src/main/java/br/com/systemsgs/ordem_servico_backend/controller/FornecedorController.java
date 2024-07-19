package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.dto.ModelFornecedorDTO;
import br.com.systemsgs.ordem_servico_backend.model.ModelFornecedor;
import br.com.systemsgs.ordem_servico_backend.service.FornecedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Api de Fornecedores - V1")
@RestController
@RequestMapping("/api/fornecedores/v1")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorServiceService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Listar Fornecedores", description = "Api para listar todos os registro de Fornecedores")
    @GetMapping("/listar")
    public ResponseEntity<List<ModelFornecedorDTO>> listarFornecedores(){
       return ResponseEntity.ok().body(fornecedorServiceService.listarFornecedores().
               stream().map(x -> mapper.map(x, ModelFornecedorDTO.class))
               .collect(Collectors.toList()));
    }

    @Operation(summary = "Pesquisa por ID", description = "Api para listar um Fornecedor por ID")
    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<ModelFornecedorDTO> pesquisarPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(fornecedorServiceService.pesquisaPorId(id), ModelFornecedorDTO.class));
    }

    @Operation(summary = "Salvar Fornecedores", description = "Api para Salvar um Fornecedor")
    @PostMapping("/salvar")
    public ResponseEntity<ModelFornecedorDTO> salvarFornecedores(@RequestBody @Valid ModelFornecedorDTO modelFornecedorDTO){
        ModelFornecedor fornecedorSalvo = fornecedorServiceService.salvarFornecedor(modelFornecedorDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(fornecedorServiceService.pesquisaPorId(fornecedorSalvo.getId())).toUri();

        return ResponseEntity.created(uri).body(mapper.map(fornecedorSalvo, ModelFornecedorDTO.class));
    }

    @Operation(summary = "Atualizar Fornecedores", description = "Api para Atualizar um Fornecedor pelo Id e Entidade")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ModelFornecedorDTO> atualizarFornecedores(@PathVariable Long id, @RequestBody @Valid ModelFornecedorDTO modelFornecedorDTO){
        modelFornecedorDTO.setId(id);
        ModelFornecedor fornecedorAtualizado = fornecedorServiceService.updateFornecedor(id, modelFornecedorDTO);

        return ResponseEntity.ok().body(mapper.map(fornecedorAtualizado, ModelFornecedorDTO.class));
    }

    @Operation(summary = "Deletar Fornecedores", description = "Api para Deletar um Fornecedor por ID")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ModelFornecedorDTO> delete(@PathVariable Long id){
        fornecedorServiceService.deletarFornecedor(id);

        return ResponseEntity.noContent().build();
    }
}
