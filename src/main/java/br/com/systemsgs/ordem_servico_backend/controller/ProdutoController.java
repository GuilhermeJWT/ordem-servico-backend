package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;
import br.com.systemsgs.ordem_servico_backend.service.ProdutoService;
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

@Tag(name = "Api de Produtos - v1")
@RestController
@RequestMapping("/api/produtos/v1")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Listar Produtos - HATEOAS", description = "Api para listar todos os Produtos com Link (HATEOAS)")
    @GetMapping("/listar/v2/link")
    public CollectionModel<ModelProdutos> listarProdutosComLink(){
        List<ModelProdutos> listaProdutos = produtoService.listarProdutos();

        for (ModelProdutos modelProdutos : listaProdutos) {
            Long produtoID = modelProdutos.getId();
            Link produtoLink = linkTo(methodOn(ProdutoController.class).pesquisarPorId(produtoID)).withRel("Pesquisa Produto pelo ID");
            modelProdutos.add(produtoLink);
        }

        CollectionModel<ModelProdutos> result = CollectionModel.of(listaProdutos);
        return result;
    }

    @Operation(summary = "Listar Produtos", description = "Api para listar todos os registro de Produtos")
    @GetMapping("/listar")
    public ResponseEntity<List<ModelProdutos>> listarProdutos(){
        return ResponseEntity.ok().body(produtoService.listarProdutos());
    }

    @Operation(summary = "Pesquisa por ID", description = "Api para listar um Produto por ID")
    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<ModelProdutos> pesquisarPorId(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.pesquisaPorId(id));
    }

    @Operation(summary = "Salvar Produtos", description = "Api para Salvar um Produto")
    @PostMapping("/salvar")
    public ResponseEntity salvarProduto(@RequestBody @Valid ModelProdutos modelProdutos){
        ModelProdutos produtoSalvo = produtoService.salvarProdutos(modelProdutos);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @Operation(summary = "Deletar Produto", description = "Api para Deletar um Produto por ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/deletar/{id}")
    public void deletarProduto(@PathVariable Long id){
        produtoService.deletarProduto(id);
    }

    @Operation(summary = "Atualizar Produto", description = "Api para Atualizar um Produto por ID e Entidade")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ModelProdutos> atualizarProdutos(@PathVariable Long id, @RequestBody @Valid ModelProdutos modelProdutos){
        ModelProdutos produtoAtualizado = produtoService.atualizarProduto(id, modelProdutos);

        return ResponseEntity.ok(produtoAtualizado);
    }

}
