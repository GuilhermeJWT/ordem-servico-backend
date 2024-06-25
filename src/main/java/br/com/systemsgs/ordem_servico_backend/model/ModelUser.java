package br.com.systemsgs.ordem_servico_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "usuarios")
@Entity
public class ModelUser implements Serializable {

    @Id
    @GeneratedValue(generator = "id_gen_user",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_gen_user", sequenceName = "usuarios_seq", initialValue = 2, allocationSize = 1)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String password;

    private String endereco;

    private String cidade;

    private String estado;

    private String cep;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name="users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<ModelRoles> roles;

}
