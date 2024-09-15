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
@Table(name = "tbl_usuarios")
@Entity
public class ModelUsuarios implements Serializable {

    @Id
    @GeneratedValue(generator = "id_gen_usuarios",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_gen_usuarios", sequenceName = "usuarios_seq", initialValue = 2, allocationSize = 1)
    private Long id;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name = "senha", length = 100)
    private String senha;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name="usuarios_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<ModelRole> roles;
}
