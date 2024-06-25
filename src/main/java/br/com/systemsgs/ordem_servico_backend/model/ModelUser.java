package br.com.systemsgs.ordem_servico_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @GeneratedValue(generator = "id_gen",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_gen", sequenceName = "usuarios_seq", initialValue = 2, allocationSize = 1)
    private Long id;

    @NotBlank(message = "Nome deve ser Informado!")
    private String nome;

    @Column(unique = true)
    @NotNull(message = "E-mail deve ser Informado!")
    private String email;

    @NotBlank(message = "Senha deve ser Informada!")
    private String password;

    private String endereco;

    private String cidade;

    private String estado;

    private String cep;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name="users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<ModelRoles> roles;

}
