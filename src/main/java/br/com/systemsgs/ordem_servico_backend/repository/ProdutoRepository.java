package br.com.systemsgs.ordem_servico_backend.repository;

import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ModelProdutos, Long> {

    @Query(value = "SELECT SUM(q.quantidade) FROM tbl_produtos q", nativeQuery = true)
    Optional<Integer> somaEstoqueAtual();
}
