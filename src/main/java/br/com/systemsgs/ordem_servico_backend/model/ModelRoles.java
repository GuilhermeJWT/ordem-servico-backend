package br.com.systemsgs.ordem_servico_backend.model;

import br.com.systemsgs.ordem_servico_backend.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="roles")
public class ModelRoles implements Serializable {

    @Id
    @GeneratedValue(generator = "id_gen",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_gen", sequenceName = "roles_seq", initialValue = 2, allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role name;
}
