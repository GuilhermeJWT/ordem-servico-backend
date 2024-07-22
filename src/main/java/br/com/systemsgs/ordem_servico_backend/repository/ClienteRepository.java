package br.com.systemsgs.ordem_servico_backend.repository;

import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ModelClientes, Long> {

    @Query(value = "SELECT COUNT(*) FROM tbl_clientes", nativeQuery = true)
    Optional<Integer> somaClientesCadastrados();

}
