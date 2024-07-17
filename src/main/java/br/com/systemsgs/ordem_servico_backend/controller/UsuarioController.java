package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.dto.SalvarUsuarioDTO;
import br.com.systemsgs.ordem_servico_backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/salvar")
    public ResponseEntity<Void> salvarUsuario(@RequestBody @Valid SalvarUsuarioDTO salvarUsuarioDTO) {
        usuarioService.salvarUsuario(salvarUsuarioDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
