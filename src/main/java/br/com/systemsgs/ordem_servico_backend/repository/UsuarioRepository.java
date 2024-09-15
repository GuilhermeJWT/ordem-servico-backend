package br.com.systemsgs.ordem_servico_backend.repository;

import br.com.systemsgs.ordem_servico_backend.model.ModelUsuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<ModelUsuarios, Long> {

    Optional<ModelUsuarios> findByEmail(String email);

}
