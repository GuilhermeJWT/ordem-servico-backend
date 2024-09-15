package br.com.systemsgs.ordem_servico_backend.security.service;

import br.com.systemsgs.ordem_servico_backend.exception.errors.UsuarioNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelUsuarios;
import br.com.systemsgs.ordem_servico_backend.repository.UsuarioRepository;
import br.com.systemsgs.ordem_servico_backend.security.ModelUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /*@Autowired
    private UsuarioRepository usuarioRepository;*/

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ModelUsuarios modelUsuarios = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());
        return new ModelUserDetailsImpl(modelUsuarios);
    }
}
