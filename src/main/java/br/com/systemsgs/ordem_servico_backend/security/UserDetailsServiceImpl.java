package br.com.systemsgs.ordem_servico_backend.security;

import br.com.systemsgs.ordem_servico_backend.exception.UsuarioNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelUsuarios;
import br.com.systemsgs.ordem_servico_backend.model.ModelUsuariosDetailsImpl;
import br.com.systemsgs.ordem_servico_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ModelUsuarios modelUsuarios = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());
        return new ModelUsuariosDetailsImpl(modelUsuarios);
    }
}
