package br.com.barbearia.api_barbearia.dto.usuario;

import br.com.barbearia.api_barbearia.model.Usuario;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
//DTO de listagem de usuários
public class UsuarioListagemDTO {

    // Campos que queremos expor na API
    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private OffsetDateTime dataCadastro;

    // 🔄 Construtor que converte a entidade Usuario em DTO
    public UsuarioListagemDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.telefone = usuario.getTelefone();
        this.dataCadastro = usuario.getDataCadastro();

    }

    // Construtor vazio (necessário se quiser usar Jackson, ModelMapper, etc.)
    public UsuarioListagemDTO() {}
}


