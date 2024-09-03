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

    @Size(max = 250, message = "{defeito.maximo.caracteres}")
    private String defeito;

    @Size(max = 250, message = "{descricao.maximo.caracteres}")
    private String descricao;

    @Size(max = 250, message = "{laudo.tecnico.maximo.caracteres}")
    private String laudoTecnico;

    @NotNull(message = "{status.obrigatorio}")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "{data.inicial.obrigatoria}")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd/MM/yyyy")
    private Date dataInicial;

    @NotNull(message = "{data.final.obrigatoria}")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd/MM/yyyy")
    private Date dataFinal;

    @NotNull(message = "{cliente.obrigatorio}")
    private ModelClientes cliente;

    @NotNull(message = "{tecnico.responsavel.obrigatorio}")
    @JsonProperty("tecnico_responsavel")
    private ModelTecnicoResponsavel tecnicoResponsavel;
}
