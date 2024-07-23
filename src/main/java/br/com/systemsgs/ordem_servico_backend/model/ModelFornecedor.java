package br.com.systemsgs.ordem_servico_backend.model;

import br.com.systemsgs.ordem_servico_backend.enums.TipoPessoa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_fornecedor")
public class ModelFornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "id_gen_fornecedor",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_gen_fornecedor", sequenceName = "fornecedores_seq", initialValue = 2, allocationSize = 1)
    private Long id;

    @Column(name = "nome", length = 150)
    private String nome;

    @Column(name = "nome_fantasia", length = 150)
    private String nomeFantasia;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa", length = 20)
    private TipoPessoa tipoPessoa;

    @Column(name = "cnpj", length = 20)
    private String cnpj;

    @Embedded
    private ModelEndereco endereco;
}
