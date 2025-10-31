package br.com.barbearia.api_barbearia.dto.barbeiro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BarbeiroCadastroDTO {

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 carateres.")
    private String nome;

    @NotBlank(message = "Especialidade não pode estar em branco.")
    private String especialidade;

    @NotBlank(message = "O telefone não pode estar em branco.")
    @Size(min = 11, message = "O telefone tem que incluir o DDD.")
    private String telefone;
}
