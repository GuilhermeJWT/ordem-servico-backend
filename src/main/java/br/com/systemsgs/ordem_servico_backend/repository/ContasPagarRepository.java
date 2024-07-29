package br.com.systemsgs.ordem_servico_backend.repository;

import br.com.systemsgs.ordem_servico_backend.model.ModelContasPagar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContasPagarRepository extends JpaRepository<ModelContasPagar, Long> {

    @Query(value = "SELECT SUM(v.valor) FROM tbl_contas_pagar v", nativeQuery = true)
    Optional<BigDecimal> totalContasPagar();

    @Query(value = "SELECT * FROM tbl_contas_pagar WHERE data_vencimento = CURRENT_DATE", nativeQuery = true)
    List<ModelContasPagar> pesquisaContasPagarExpiradas();

    @Query(value = "SELECT COUNT(*) from tbl_contas_pagar where status_conta_pagar = 'VENCIDA'", nativeQuery = true)
    Optional<Integer> quantidadeContasPagarVencidas();
}
