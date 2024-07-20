package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.dto.ModelProdutosDTO;
import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;
import br.com.systemsgs.ordem_servico_backend.service.ProdutoService;
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

@Tag(name = "Api de Produtos - V1")
@RestController
@RequestMapping("/api/produtos/v1")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Listar Produtos - HATEOAS", description = "Api para listar todos os Produtos com Link (HATEOAS)")
    @GetMapping("/listar/v2/link")
    public CollectionModel<ModelProdutosDTO> listarProdutosComLink(){
        List<ModelProdutosDTO> listaProdutos = produtoService.listarProdutos()
                .stream().map(x -> mapper.map(x, ModelProdutosDTO.class)).collect(Collectors.toList());

        for (ModelProdutosDTO modelProdutos : listaProdutos) {
            Long produtoID = modelProdutos.getId();
            Link produtoLink = linkTo(methodOn(ProdutoController.class).pesquisarPorId(produtoID)).withRel("Pesquisa Produto pelo ID");
            modelProdutos.add(produtoLink);
        }

        CollectionModel<ModelProdutosDTO> result = CollectionModel.of(listaProdutos);
        return result;
    }

    @Operation(summary = "Listar Produtos", description = "Api para listar todos os registro de Produtos")
    @GetMapping("/listar")
    public ResponseEntity<List<ModelProdutosDTO>> listarProdutos(){
        return ResponseEntity.ok().body(produtoService.listarProdutos().
                stream().map(x -> mapper.map(x, ModelProdutosDTO.class))
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Pesquisa por ID", description = "Api para listar um Produto por ID")
    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<ModelProdutosDTO> pesquisarPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(produtoService.pesquisaPorId(id), ModelProdutosDTO.class));
    }

    @Operation(summary = "Salvar Produtos", description = "Api para Salvar um Produto")
    @PostMapping("/salvar")
    public ResponseEntity salvarProduto(@RequestBody @Valid ModelProdutosDTO modelProdutosDTO){
        ModelProdutos produtoSalvo = produtoService.salvarProdutos(modelProdutosDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(produtoService.pesquisaPorId(produtoSalvo.getId())).toUri();

        return ResponseEntity.created(uri).body(mapper.map(produtoSalvo, ModelProdutosDTO.class));
    }

    @Operation(summary = "Atualizar Produto", description = "Api para Atualizar um Produto por ID e Entidade")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ModelProdutosDTO> atualizarProdutos(@PathVariable Long id, @RequestBody @Valid ModelProdutosDTO modelProdutosDTO){
        modelProdutosDTO.setId(id);
        ModelProdutos produtoAtualizado = produtoService.atualizarProduto(id, modelProdutosDTO);

        return ResponseEntity.ok().body(mapper.map(produtoAtualizado, ModelProdutosDTO.class));
    }

    @Operation(summary = "Deletar Produto", description = "Api para Deletar um Produto por ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ModelProdutosDTO> delete(@PathVariable Long id){
        produtoService.deletarProduto(id);

        return ResponseEntity.noContent().build();
    }

}
