package br.com.barbearia.api_barbearia.dto.agendamento;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data

//DTO de listagem de agendamentos
public class AgendamentoListagemDTO {
    private Integer id;
    private OffsetDateTime dataHora;
    private String status;
    private String nomeUsuario;
    private String telefoneCliente;
    private String nomeBarbeiro;
    private String servico;
    private BigDecimal preco;
}
