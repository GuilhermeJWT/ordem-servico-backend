package br.com.systemsgs.ordem_servico_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produtos")
@Entity(name = "produtos")
public class ModelProdutos extends RepresentationModel<ModelProdutos> implements Serializable {

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
