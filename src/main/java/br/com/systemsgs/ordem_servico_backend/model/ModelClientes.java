package br.com.systemsgs.ordem_servico_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_clientes")
public class ModelClientes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "id_gen",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_gen", sequenceName = "clientes_seq", initialValue = 2, allocationSize = 1)
    private Long id;

    @Column(name = "nome", length = 150)
    private String nome;

    @Column(name = "cpf", length = 20)
    private String cpf;

    @Column(name = "celular", length = 25)
    private String celular;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "ativo")
    private boolean ativo = true;

    @Embedded
    private ModelEndereco endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<ModelOrdemServico> ordemServicos = new ArrayList<>();
}
