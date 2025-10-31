package br.com.barbearia.api_barbearia.dto.usuario;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UsuarioCadastroDTO {
    // Campos que o cliente DEVE enviar para cadastrar um usuário

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 carateres.")
    private String nome;

    @NotBlank(message = "O email não pode estar em branco.")
    @Email(message = "O formato do e-mail é invalido.")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "A senha não pode estar em branco.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 carateres.")
    private String senha;

    @NotBlank(message = "O telefone não pode estar em branco.")
    @Size(min = 11, message = "O tefone tem que incluir o DDD")
    private String telefone;
}
