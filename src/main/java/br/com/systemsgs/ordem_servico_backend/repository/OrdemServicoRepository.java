package br.com.systemsgs.ordem_servico_backend.repository;

import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemServicoRepository extends JpaRepository<ModelOrdemServico, Long> {

}
