package br.com.barbearia.api_barbearia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "barbeiro", schema = "barbearia")
public class Barbeiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_barbeiro", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = Integer.MAX_VALUE)
    private String nome;

    @Column(name = "especialidade", nullable = false, length = Integer.MAX_VALUE)
    private String especialidade;

    @Column(name = "telefone", nullable = false, length = 11)
    private String telefone;

}