package br.com.systemsgs.ordem_servico_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produtos")
@Entity(name = "produtos")
public class ModelProdutos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "id_produto_gen",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_produto_gen", sequenceName = "produtos_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotBlank(message = "Descrição deve ser Informada.")
    private String descricao;

    @NotNull(message = "Quantidade deve ser Informada.")
    private Integer quantidade;

    private Integer quantidade_minima;

    private BigDecimal preco_compra;

    private BigDecimal preco_venda;

    private String codigo_barras;
}
