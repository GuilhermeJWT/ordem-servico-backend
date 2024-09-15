package br.com.systemsgs.ordem_servico_backend.security.service;

import br.com.systemsgs.ordem_servico_backend.security.EnableWebSecurityConfiguration;
import br.com.systemsgs.ordem_servico_backend.dto.request.LoginUsuarioDTO;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelUsuariosDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.JwtTokenResponse;
import br.com.systemsgs.ordem_servico_backend.model.ModelRole;
import br.com.systemsgs.ordem_servico_backend.model.ModelUsuarios;
import br.com.systemsgs.ordem_servico_backend.repository.UsuarioRepository;
import br.com.systemsgs.ordem_servico_backend.security.JwtTokenService;
import br.com.systemsgs.ordem_servico_backend.security.ModelUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final EnableWebSecurityConfiguration enableWebSecurityConfiguration;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, AuthenticationManager authenticationManager,
                          JwtTokenService jwtTokenService, EnableWebSecurityConfiguration enableWebSecurityConfiguration) {
        this.usuarioRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.enableWebSecurityConfiguration = enableWebSecurityConfiguration;
    }

    public void salvarUsuario(ModelUsuariosDTO modelUsuariosDTO) {
        ModelUsuarios modelUsuarios = ModelUsuarios.builder()
                .email(modelUsuariosDTO.email())
                .senha(enableWebSecurityConfiguration.passwordEncoder().encode(modelUsuariosDTO.senha()))
                .roles(List.of(ModelRole.builder().name(modelUsuariosDTO.role()).build()))
                .build();

        usuarioRepository.save(modelUsuarios);
    }

    public JwtTokenResponse autenticarUsuario(LoginUsuarioDTO loginUsuarioDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUsuarioDTO.email(), loginUsuarioDTO.senha());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        ModelUserDetailsImpl modelUserDetails = (ModelUserDetailsImpl) authentication.getPrincipal();
        return new JwtTokenResponse(jwtTokenService.generateToken(modelUserDetails));
    }
}
