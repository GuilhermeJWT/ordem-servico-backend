package br.com.systemsgs.ordem_servico_backend.security;

import br.com.systemsgs.ordem_servico_backend.model.ModelUsuarios;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class ModelUserDetailsImpl implements UserDetails {

    private ModelUsuarios modelUsuarios;

    public ModelUserDetailsImpl(ModelUsuarios modelUsuarios) {
        this.modelUsuarios = modelUsuarios;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return modelUsuarios.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(
                        role.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return modelUsuarios.getSenha();
    }

    @Override
    public String getUsername() {
        return modelUsuarios.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
