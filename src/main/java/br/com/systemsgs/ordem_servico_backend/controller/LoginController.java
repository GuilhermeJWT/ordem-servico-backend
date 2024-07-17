package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.dto.records.JwtTokenDTO;
import br.com.systemsgs.ordem_servico_backend.dto.records.LoginUsuarioDTO;
import br.com.systemsgs.ordem_servico_backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/autenticar")
    public ResponseEntity<JwtTokenDTO> loginUsuario(@RequestBody LoginUsuarioDTO loginUsuarioDTO) {
        JwtTokenDTO token = usuarioService.autenticarUsuario(loginUsuarioDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
