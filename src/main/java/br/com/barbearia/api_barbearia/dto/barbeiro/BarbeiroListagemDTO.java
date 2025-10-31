package br.com.barbearia.api_barbearia.dto.barbeiro;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BarbeiroListagemDTO {
    private Integer id;
    private String nome;
    private String especialidade;
    private String telefone;
}
