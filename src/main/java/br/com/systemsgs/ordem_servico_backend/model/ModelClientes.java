package br.com.systemsgs.ordem_servico_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class ModelClientes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "id_gen",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_gen", sequenceName = "clientes_seq", initialValue = 2, allocationSize = 1)
    private Long id;

    @NotBlank(message = "Nome deve ser Informado.")
    private String nome;

    @CPF(message = "Informe um CPF Válido.")
    @NotBlank(message = "CPF deve ser Informado.")
    private String cpf;

    private String celular;

    @Email(message = "Informe um E-mail Válido.")
    private String email;

    private String endereco;

    private String cidade;

    private String estado;

    private String cep;
}
