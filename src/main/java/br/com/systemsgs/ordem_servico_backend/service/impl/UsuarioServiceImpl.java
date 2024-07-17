package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.records.JwtTokenDTO;
import br.com.systemsgs.ordem_servico_backend.dto.records.LoginUsuarioDTO;
import br.com.systemsgs.ordem_servico_backend.dto.SalvarUsuarioDTO;
import br.com.systemsgs.ordem_servico_backend.model.ModelRole;
import br.com.systemsgs.ordem_servico_backend.model.ModelUsuarios;
import br.com.systemsgs.ordem_servico_backend.model.ModelUsuariosDetailsImpl;
import br.com.systemsgs.ordem_servico_backend.repository.UsuarioRepository;
import br.com.systemsgs.ordem_servico_backend.security.EnableWebSecurityConfiguration;
import br.com.systemsgs.ordem_servico_backend.security.JwtTokenService;
import br.com.systemsgs.ordem_servico_backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EnableWebSecurityConfiguration securityConfiguration;

    @Override
    public void salvarUsuario(SalvarUsuarioDTO salvarUsuarioDTO) {
        ModelUsuarios usuarioSalvo = ModelUsuarios.builder()
                .email(salvarUsuarioDTO.getEmail())
                .senha(securityConfiguration.passwordEncoder().encode(salvarUsuarioDTO.getSenha()))
                .roles(List.of(ModelRole.builder().name(salvarUsuarioDTO.getRole()).build()))
                .build();

        usuarioRepository.save(usuarioSalvo);
    }

    @Override
    public JwtTokenDTO autenticarUsuario(LoginUsuarioDTO loginUsuarioDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUsuarioDTO.email(), loginUsuarioDTO.senha());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        ModelUsuariosDetailsImpl modelUserDetails = (ModelUsuariosDetailsImpl) authentication.getPrincipal();
        return new JwtTokenDTO(jwtTokenService.generateToken(modelUserDetails));
    }
}
