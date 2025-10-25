package br.com.barbearia.api_barbearia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "agendamento", schema = "barbearia")
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agendamento", nullable = false)
    private Integer id;

    @Column(name = "data_hora", nullable = false)
    private OffsetDateTime dataHora;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_barbeiro", nullable = false)
    private Barbeiro barbeiro;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_servico", nullable = false)
    private Servico servico;

/*
 TODO [Reverse Engineering] create field to map the 'status' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("'Pendente'")
    @Column(name = "status", columnDefinition = "status_agendamento_enum not null")
    private Object status;
*/
}