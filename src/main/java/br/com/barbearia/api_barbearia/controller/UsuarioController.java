package br.com.barbearia.api_barbearia.controller;

import br.com.barbearia.api_barbearia.dto.usuario.ResetarSenhaDTO;
import br.com.barbearia.api_barbearia.dto.usuario.UsuarioCadastroDTO;
import br.com.barbearia.api_barbearia.dto.usuario.UsuarioListagemDTO;
import br.com.barbearia.api_barbearia.model.Usuario;
import br.com.barbearia.api_barbearia.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<UsuarioListagemDTO>> listarUsuarios() {
        List<UsuarioListagemDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar Usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(Integer id) {
        UsuarioListagemDTO usuario = usuarioService.buscaPorId(id);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        return ResponseEntity.ok(usuario);
    }

    // Recuperar senha
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ResetarSenhaDTO resetarSenhaDTO) {
        usuarioService.recuperarSenha(resetarSenhaDTO.getEmail());
        return ResponseEntity.ok("Se um usuário com este e-mail existir, uma nova senha será enviada.");
    }

    // Cadastrar Usuarios
    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody UsuarioCadastroDTO dto) {
        Usuario usuarioSalvo = usuarioService.cadastrarUsuario(dto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    // Atualizar Usuarios
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioCadastroDTO usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);

        if (usuarioAtualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // Deletar Usuarios
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerUsuario(@PathVariable Integer id) {
        Usuario usuarioDeletado = usuarioService.deletarUsuario(id);

        if (usuarioDeletado == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario " + id + " deletado com sucesso");
    }


}
