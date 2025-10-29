package br.com.barbearia.api_barbearia.dto.login;

import br.com.barbearia.api_barbearia.dto.usuario.UsuarioListagemDTO;

public record LoginResponseDTO (String token, UsuarioListagemDTO usuarioDTO){

}
