package br.com.systemsgs.ordem_servico_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_itens_vendas")
public class ModelItensVendas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "id_gen_itens_venda",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_gen_itens_venda", sequenceName = "itens_vendas_seq", initialValue = 2, allocationSize = 1)
    @Column(name = "id_itens_venda")
    private Long idItensVenda;

    @Column(name = "quantidade")
    private List<Integer> quantidade;

    @Column(name = "valor_produto")
    private List<BigDecimal> valorProduto;

    private List<Long> produto;

    public ModelItensVendas(List<Integer> quantidade, List<BigDecimal> valorProduto, List<Long> produto){
        this.quantidade = quantidade;
        this.valorProduto = valorProduto;
        this.produto = produto;
    }

}
