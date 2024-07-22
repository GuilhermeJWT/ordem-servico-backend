package br.com.systemsgs.ordem_servico_backend.repository;

import br.com.systemsgs.ordem_servico_backend.model.ModelVendas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface VendasRepository extends JpaRepository<ModelVendas, Long> {

    @Query(value = "SELECT SUM(tv.total_venda) FROM tbl_vendas tv", nativeQuery = true)
    Optional<BigDecimal> calculaTotalVendasTodoPeriodo();

    @Query(value = "SELECT SUM(ti.total_itens) FROM tbl_vendas ti", nativeQuery = true)
    Optional<Integer> calculaTotalItensVendidosTodoPeriodo();

}
