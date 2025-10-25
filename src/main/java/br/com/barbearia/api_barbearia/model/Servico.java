package br.com.barbearia.api_barbearia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "servico", schema = "barbearia")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servico", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = Integer.MAX_VALUE)
    private String nome;

    @Column(name = "descricao", nullable = false, length = Integer.MAX_VALUE)
    private String descricao;

    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

/*
 TODO [Reverse Engineering] create field to map the 'duracao' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "duracao", columnDefinition = "interval not null")
    private Object duracao;
*/
}