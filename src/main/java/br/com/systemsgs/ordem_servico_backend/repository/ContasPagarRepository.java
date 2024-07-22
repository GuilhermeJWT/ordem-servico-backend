package br.com.systemsgs.ordem_servico_backend.repository;

import br.com.systemsgs.ordem_servico_backend.model.ModelContasPagar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ContasPagarRepository extends JpaRepository<ModelContasPagar, Long> {

    @Query(value = "SELECT SUM(v.valor) FROM tbl_contas_pagar v", nativeQuery = true)
    Optional<BigDecimal> totalContasPagar();

}
