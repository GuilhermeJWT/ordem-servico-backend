package br.com.systemsgs.ordem_servico_backend.dto.hateoas;

import br.com.systemsgs.ordem_servico_backend.enums.Status;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelTecnicoResponsavel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelOrdemServicoHateoas extends RepresentationModel<ModelOrdemServicoHateoas> {

    @JsonProperty("id_ordem_servico")
    private Long id;

    @JsonProperty("defeito")
    private String defeito;

    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("laudo_tecnico")
    private String laudoTecnico;

    @JsonProperty("status_os")
    private Status status;

    @JsonProperty("data_inicial")
    private Date dataInicial;

    @JsonProperty("data_final")
    private Date dataFinal;

    @JsonProperty("cliente")
    private ModelClientes cliente;

    @JsonProperty("tecnico_responsavel")
    private ModelTecnicoResponsavel tecnicoResponsavel;
}
