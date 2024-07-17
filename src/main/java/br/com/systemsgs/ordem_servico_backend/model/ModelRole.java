package br.com.systemsgs.ordem_servico_backend.model;

import br.com.systemsgs.ordem_servico_backend.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class ModelRole implements Serializable {

    @Id
    @GeneratedValue(generator = "id_gen_roles",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_gen_roles", sequenceName = "roles_seq", initialValue = 2, allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role name;

}
