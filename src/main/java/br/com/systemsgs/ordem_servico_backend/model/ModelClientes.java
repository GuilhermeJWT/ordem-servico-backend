package br.com.systemsgs.ordem_servico_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class ModelClientes extends RepresentationModel<ModelClientes> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "id_gen",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_gen", sequenceName = "clientes_seq", initialValue = 2, allocationSize = 1)
    private Long id;

    private String nome;

    private String cpf;

    private String celular;

    private String email;

    private String endereco;

    private String cidade;

    private String estado;

    private String cep;

    @OneToMany(mappedBy = "cliente",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               orphanRemoval = true
    )
    @JsonIgnore
    private List<ModelOrdemServico> ordemServicos = new ArrayList<>();
}
