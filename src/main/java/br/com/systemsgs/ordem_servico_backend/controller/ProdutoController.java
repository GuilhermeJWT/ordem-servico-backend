package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;
import br.com.systemsgs.ordem_servico_backend.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos/v1")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/listar")
    public ResponseEntity<List<ModelProdutos>> listarProdutos(){
        return ResponseEntity.ok().body(produtoService.listarProdutos());
    }

    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<ModelProdutos> pesquisarPorId(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.pesquisaPorId(id));
    }

    @PostMapping("/salvar")
    public ResponseEntity salvarProduto(@RequestBody @Valid ModelProdutos modelProdutos){
        ModelProdutos produtoSalvo = produtoService.salvarProdutos(modelProdutos);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/deletar/{id}")
    public void deletarProduto(@PathVariable Long id){
        produtoService.deletarProduto(id);
    }

}
