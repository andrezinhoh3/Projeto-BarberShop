package br.com.barbearia.api_barbearia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "pagamento", schema = "barbearia")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento", nullable = false)
    private Integer id;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @ColumnDefault("now()")
    @Column(name = "data_pagamento", nullable = false)
    private OffsetDateTime dataPagamento;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_agendamento", nullable = false)
    private Agendamento agendamento;

/*
 TODO [Reverse Engineering] create field to map the 'status' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("'Pendente'")
    @Column(name = "status", columnDefinition = "status_pagamento_enum not null")
    private Object status;
*/
}