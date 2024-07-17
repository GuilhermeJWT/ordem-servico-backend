package br.com.systemsgs.ordem_servico_backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "usuarios")
public class ModelUsuarios implements Serializable {

    @Id
    @GeneratedValue(generator = "id_gen_usuarios",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_gen_usuarios", sequenceName = "usuarios_seq", initialValue = 2, allocationSize = 1)
    private Long id;

    @Column(name = "nome_completo", length = 150)
    private String nomeCompleto;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "senha")
    private String senha;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private Date criadoEm;

    @UpdateTimestamp
    @Column(name = "alterado_em")
    private Date alteradoEm;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name="users_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<ModelRole> roles;
}
