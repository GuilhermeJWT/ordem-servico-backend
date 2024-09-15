package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.dto.request.LoginUsuarioDTO;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelUsuariosDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.JwtTokenResponse;
import br.com.systemsgs.ordem_servico_backend.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios/v1")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponse> loginUsuario(@RequestBody LoginUsuarioDTO loginUsuarioDTO) {
        JwtTokenResponse token = usuarioService.autenticarUsuario(loginUsuarioDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/salvar")
    public ResponseEntity<Void> salvarUsuario(@RequestBody ModelUsuariosDTO createUserDto) {
        usuarioService.salvarUsuario(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
