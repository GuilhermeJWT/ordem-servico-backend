package br.com.systemsgs.ordem_servico_backend.repository;

import br.com.systemsgs.ordem_servico_backend.model.ModelContasReceber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContasReceberRepository extends JpaRepository<ModelContasReceber, Long> {

    @Query(value = "SELECT SUM(v.valor) FROM tbl_contas_receber v", nativeQuery = true)
    Optional<BigDecimal> totalContasReceber();

    @Query(value = "SELECT COUNT(*) from tbl_contas_receber where status_conta_receber = 'INADIMPLENTE'", nativeQuery = true)
    Optional<Integer> quantidadeContasInadimplentes();

    @Query(value = "SELECT * FROM tbl_contas_receber WHERE data_vencimento = CURRENT_DATE AND" +
            " status_conta_receber != 'VENCIDA'", nativeQuery = true)
    List<ModelContasReceber> pesquisaContasReceberExpiradas();

    @Query(value = "SELECT COUNT(*) from tbl_contas_receber where status_conta_receber = 'VENCIDA'", nativeQuery = true)
    Optional<Integer> quantidadeContasReceberVencidas();
}
