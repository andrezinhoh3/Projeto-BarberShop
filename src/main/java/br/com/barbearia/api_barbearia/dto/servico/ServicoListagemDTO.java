package br.com.barbearia.api_barbearia.dto.servico;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;

@Data
public class ServicoListagemDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Duration duracao;
}
