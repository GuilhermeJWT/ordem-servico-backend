package br.com.systemsgs.ordem_servico_backend.dto.request;

import br.com.systemsgs.ordem_servico_backend.enums.Status;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelTecnicoResponsavel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelOrdemServicoDTO extends RepresentationModel<ModelOrdemServicoDTO> {

    private Long id;

    @Size(max = 250, message = "Defeito deve ter no máximo 250 Caracteres.")
    private String defeito;

    @Size(max = 250, message = "Descricao deve ter no máximo 250 Caracteres.")
    private String descricao;

    @Size(max = 250, message = "Laudo Técnico deve ter no máximo 250 Caracteres.")
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

    @NotNull(message = "Informe um Cliente.")
    private ModelClientes cliente;

    @NotNull(message = "Informe o Técnico Responsavel")
    @JsonProperty("tecnico_responsavel")
    private ModelTecnicoResponsavel tecnicoResponsavel;
}
