package br.com.barbearia.api_barbearia.controller;

import br.com.barbearia.api_barbearia.dto.usuario.ResetarSenhaDTO;
import br.com.barbearia.api_barbearia.dto.usuario.UsuarioCadastroDTO;
import br.com.barbearia.api_barbearia.dto.usuario.UsuarioListagemDTO;
import br.com.barbearia.api_barbearia.model.Usuario;
import br.com.barbearia.api_barbearia.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Operações relacionados aos usuarios.")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Listar todos os usuários
    @GetMapping
    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna uma lista com todos os usuários cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
    })
    public ResponseEntity<List<UsuarioListagemDTO>> listarUsuarios() {
        List<UsuarioListagemDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar Usuario por ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna um usuário especifico com base no seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado para o ID informado")
    })
    public ResponseEntity<?> buscarUsuarioPorId(Integer id) {
        UsuarioListagemDTO usuario = usuarioService.buscaPorId(id);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        return ResponseEntity.ok(usuario);
    }

    // Recuperar senha
    @PostMapping("/forgot-password")
    @Operation(
            summary = "Recuperar a senha por email",
            description = "Retorna uma senha provisoria no email cadastrado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado para o email informado")
    })
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ResetarSenhaDTO resetarSenhaDTO) {
        usuarioService.recuperarSenha(resetarSenhaDTO.getEmail());
        return ResponseEntity.ok("Se um usuário com este e-mail existir, uma nova senha será enviada.");
    }

    // Cadastrar Usuarios
    @PostMapping
    @Operation(
            summary = "Cadastrar um novo usuário",
            description = "Adiciona um usuário ao banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos fornecido para o cadastro")
    })
    public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody UsuarioCadastroDTO dto) {
        Usuario usuarioSalvo = usuarioService.cadastrarUsuario(dto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    // Atualizar Usuarios
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza um usuário existente",
            description = "Altera os dados de um usuário com base no seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado para o ID informado")
    })
    public ResponseEntity<?> atualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioCadastroDTO usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);

        if (usuarioAtualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // Deletar Usuarios
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Exclui usuário",
            description = "Remove um usuário do banco de dados com base no seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário excluido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado para o ID informado")
    })
    public ResponseEntity<?> removerUsuario(@PathVariable Integer id) {
        Usuario usuarioDeletado = usuarioService.deletarUsuario(id);

        if (usuarioDeletado == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        return ResponseEntity.ok("Usuario " + id + " deletado com sucesso");
    }


}
