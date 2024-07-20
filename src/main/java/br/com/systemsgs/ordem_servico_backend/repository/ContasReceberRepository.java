package br.com.systemsgs.ordem_servico_backend.repository;

import br.com.systemsgs.ordem_servico_backend.model.ModelContasReceber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContasReceberRepository extends JpaRepository<ModelContasReceber, Long> {}
