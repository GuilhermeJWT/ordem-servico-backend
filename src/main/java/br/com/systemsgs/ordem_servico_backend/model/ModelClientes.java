package br.com.systemsgs.ordem_servico_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clientes")
@Entity
public class ModelClientes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String cpf;

    private String celular;

    private String email;

    private String endereco;

    private String cidade;

    private String estado;

    private String cep;
}
