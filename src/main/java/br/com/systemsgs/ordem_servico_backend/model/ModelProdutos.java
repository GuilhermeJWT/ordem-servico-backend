package br.com.systemsgs.ordem_servico_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_produtos")
public class ModelProdutos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "id_produto_gen",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_produto_gen", sequenceName = "produtos_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "quantidade_minima")
    private Integer quantidade_minima;

    @Column(name = "preco_compra")
    private BigDecimal preco_compra;

    @Column(name = "preco_venda")
    private BigDecimal preco_venda;

    @Column(name = "codigo_barras")
    private String codigo_barras;
}
