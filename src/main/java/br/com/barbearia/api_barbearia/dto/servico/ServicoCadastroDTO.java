package br.com.barbearia.api_barbearia.dto.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;

@Data
public class ServicoCadastroDTO {
    // Campos que o ADMIN DEVE enviar para criar um servico

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 carateres.")
    private String nome;

    private String descricao;

    @NotBlank(message = "O preço não pode estar em branco.")
    @Positive(message = "O preço deve ser um número positivo.")
    private BigDecimal preco;

    @NotBlank(message = "A duração não pode estar em branco.")
    private Duration duracao;
}
