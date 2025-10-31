package br.com.barbearia.api_barbearia.model;

import br.com.barbearia.api_barbearia.status.StatusAgendamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "agendamento", schema = "barbearia")
@Check(constraints = "status IN ('PENDENTE', 'CONFIRMADO', 'CANCELADO')")
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



    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PENDENTE'")
    private StatusAgendamento status;


}