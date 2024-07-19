package br.com.systemsgs.ordem_servico_backend.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ModelEndereco implements Serializable {

    private static final long serialVersionUID = 1L;

    private String endereco;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;

}
