package br.com.systemsgs.ordem_servico_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produtos")
@Entity
public class ModelProdutos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "id_produto_gen",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_produto_gen", sequenceName = "produtos_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    private String descricao;

    private Integer quantidade;

    private Integer quantidade_minima;

    private BigDecimal preco_compra;

    private BigDecimal preco_venda;

    private String codigo_barras;
}
