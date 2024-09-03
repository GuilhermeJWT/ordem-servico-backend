package br.com.systemsgs.ordem_servico_backend.model;

import br.com.systemsgs.ordem_servico_backend.enums.FormaPagamento;
import br.com.systemsgs.ordem_servico_backend.enums.StatusContas;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_contas_receber")
public class ModelContasReceber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "id_gen_contas_receber",strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id_gen_contas_receber", sequenceName = "contas_receber_seq", initialValue = 2, allocationSize = 1)
    private Long id;

    @Column(name = "data_vencimento", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd/MM/yyyy")
    private Date dataVencimento;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor = BigDecimal.ZERO;

    @Column(name = "data_emissao")
    private Date dataEmissao = new Date();

    @Column(name = "observacao", length = 250)
    private String observacao;

    @Column(name = "forma_pagamento", length = 35)
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @Column(name = "status_conta_receber", nullable = false, length = 35)
    @Enumerated(EnumType.STRING)
    private StatusContas statusContasReceber;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ModelClientes cliente;

}
