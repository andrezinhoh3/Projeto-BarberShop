package br.com.barbearia.api_barbearia.dto.agendamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Data
public class AgendamentosCadastroDTO {
    private OffsetDateTime dataHora;

    @NotBlank(message = "O ID do usuário é obrigatório.")
    @Positive(message = "O ID do usuário deve ser um número posistivo.")
    private Integer idUsuario;

    @NotBlank(message = "O ID do serviço é obrigatório.")
    @Positive(message = "O ID do serviço deve ser um número posistivo.")
    private Integer idServico;

    @NotBlank(message = "O ID do barbeiro é obrigatório.")
    @Positive(message = "O ID do barbeiro deve ser um número posistivo.")
    private Integer idBarbeiro;


}
