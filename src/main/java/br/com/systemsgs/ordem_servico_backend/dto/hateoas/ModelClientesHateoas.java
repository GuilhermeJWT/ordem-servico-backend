package br.com.systemsgs.ordem_servico_backend.dto.hateoas;

import br.com.systemsgs.ordem_servico_backend.model.ModelEndereco;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelClientesHateoas extends RepresentationModel<ModelClientesHateoas> {

    @JsonProperty("codigo_cliente")
    private Long id;

    @JsonProperty("nome_cliente")
    private String nome;

    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("celular")
    private String celular;

    @JsonProperty("email")
    private String email;

    @JsonProperty("endereco_cliente")
    private ModelEndereco endereco;
}
