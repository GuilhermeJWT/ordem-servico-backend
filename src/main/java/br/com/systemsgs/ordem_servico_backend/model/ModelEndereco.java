package br.com.systemsgs.ordem_servico_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
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

    @Size(max = 150, message = "Endereço deve ter no máximo 150 Caracteres.")
    @Column(name = "endereco")
    private String endereco;

    @Size(max = 150, message = "Complemento do Endereço deve ter no máximo 150 Caracteres.")
    @Column(name = "complemento")
    private String complemento;

    @Size(max = 40, message = "Cidade deve ter no máximo 40 Caracteres.")
    @Column(name = "cidade")
    private String cidade;

    @Size(max = 25, message = "Estado deve ter no máximo 25 Caracteres.")
    @Column(name = "estado")
    private String estado;

    @Size(max = 10, message = "Cep deve ter no máximo 10 Caracteres.")
    @Column(name = "cep")
    private String cep;

}
