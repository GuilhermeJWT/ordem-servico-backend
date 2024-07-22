package br.com.systemsgs.ordem_servico_backend.repository;

import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdemServicoRepository extends JpaRepository<ModelOrdemServico, Long> {

    @Query(value = "SELECT COUNT(*) FROM tbl_ordemservico", nativeQuery = true)
    Optional<Integer> quantidadeOsRealizadas();

    @Query(value = "SELECT COUNT(*) from tbl_ordemservico where status = 'EM_ANDAMENTO'", nativeQuery = true)
    Optional<Integer> quantidadeOsEmAndamento();

}
