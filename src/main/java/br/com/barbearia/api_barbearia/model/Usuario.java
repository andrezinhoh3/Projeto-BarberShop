package br.com.barbearia.api_barbearia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "usuario", schema = "barbearia")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = Integer.MAX_VALUE)
    private String nome;

    @Column(name = "email", nullable = false, length = Integer.MAX_VALUE)
    private String email;

    @Column(name = "senha", nullable = false, length = Integer.MAX_VALUE)
    private String senha;

    @Column(name = "telefone", nullable = false, length = 11)
    private String telefone;

    @ColumnDefault("now()")
    @Column(name = "data_cadastro", nullable = false)
    private OffsetDateTime dataCadastro;

}