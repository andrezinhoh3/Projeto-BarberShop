package br.com.barbearia.api_barbearia.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "O email não pode estar em branco.")
    @Email(message = "O formato do e-mail é invalido.")
    private String email;

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 carateres.")
    private String senha;
}
