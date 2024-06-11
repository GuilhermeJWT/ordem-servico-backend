package br.com.systemsgs.ordem_servico_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clientes")
@Entity(name = "clientes")
public class ModelClientes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
