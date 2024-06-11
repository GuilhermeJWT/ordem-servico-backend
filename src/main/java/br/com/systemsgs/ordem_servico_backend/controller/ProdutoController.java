package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;
import br.com.systemsgs.ordem_servico_backend.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Api de Produtos - v1")
@RestController
@RequestMapping("/api/produtos/v1")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

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

}
