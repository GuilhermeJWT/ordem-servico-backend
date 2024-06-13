package br.com.systemsgs.ordem_servico_backend.model;

import br.com.systemsgs.ordem_servico_backend.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "ordemservico")
@Entity
public class ModelOrdemServico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "id_ordem_servico_gen",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_ordem_servico_gen", sequenceName = "ordemservico_seq", initialValue = 2, allocationSize = 1)
    private Long id;

    private String defeito;

    private String descricao;

    private String laudo_tecnico;

    @NotNull(message = "Status deve ser Informado.")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "A Data Inicial deve ser Informada.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd/MM/yyyy")
    private Date data_inicial;

    @NotNull(message = "A Data Final deve ser Informada.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd/MM/yyyy")
    private Date data_final;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ModelClientes cliente;

}
