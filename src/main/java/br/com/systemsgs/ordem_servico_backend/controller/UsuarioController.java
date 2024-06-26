package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.dto.ModelUserDTO;
import br.com.systemsgs.ordem_servico_backend.model.ModelUser;
import br.com.systemsgs.ordem_servico_backend.service.UsuarioService;
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


@Tag(name = "Api de Usuarios - v1")
@RestController
@RequestMapping("/api/usuarios/v1")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ModelMapper mapper;


    @Operation(summary = "Listar Usuarios", description = "Api para listar todos os registro de Usuarios")
    @GetMapping("/listar")
    public ResponseEntity<List<ModelUserDTO>> listarUsuarios(){
       return ResponseEntity.ok().body(usuarioService.listarUsuarios().
               stream().map(x -> mapper.map(x, ModelUserDTO.class))
               .collect(Collectors.toList()));
    }

    @Operation(summary = "Pesquisa por ID", description = "Api para listar um Usuario por ID")
    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<ModelUserDTO> pesquisarPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(usuarioService.pesquisaPorId(id), ModelUserDTO.class));
    }

    @Operation(summary = "Salvar Usuarios", description = "Api para Salvar um Usuario")
    @PostMapping("/salvar")
    public ResponseEntity<ModelUserDTO> salvarUsuario(@RequestBody @Valid ModelUserDTO modelUsuariosDTO){
        ModelUser usuarioSalvo = usuarioService.salvarUsuarios(modelUsuariosDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(usuarioService.pesquisaPorId(usuarioSalvo.getId())).toUri();

        return ResponseEntity.created(uri).body(mapper.map(usuarioSalvo, ModelUserDTO.class));
    }

    @Operation(summary = "Atualizar Usuarios", description = "Api para Atualizar um Usuario pelo Id e Entidade")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ModelUserDTO> atualizarUsuarios(@PathVariable Long id, @RequestBody @Valid ModelUserDTO modelUsuariosDTO){
        modelUsuariosDTO.setId(id);
        ModelUser usuarioAtualizado = usuarioService.updateUsuarios(id, modelUsuariosDTO);

        return ResponseEntity.ok().body(mapper.map(usuarioAtualizado, ModelUserDTO.class));
    }

    @Operation(summary = "Deletar Usuarios", description = "Api para Deletar um Usuario por ID")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ModelUserDTO> delete(@PathVariable Long id){
        usuarioService.deletarUsuario(id);

        return ResponseEntity.noContent().build();
    }
}
