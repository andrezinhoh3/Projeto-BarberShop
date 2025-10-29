package br.com.barbearia.api_barbearia.controller;

import br.com.barbearia.api_barbearia.dto.barbeiro.BarbeiroCadastroDTO;
import br.com.barbearia.api_barbearia.dto.barbeiro.BarbeiroListagemDTO;
import br.com.barbearia.api_barbearia.model.Barbeiro;
import br.com.barbearia.api_barbearia.service.BarbeiroService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barbeiros")
@SecurityRequirement(name = "bearerAuth")
public class BarbeiroController {

    private final BarbeiroService barbeiroService;

    public BarbeiroController(BarbeiroService barbeiroService) {
        this.barbeiroService = barbeiroService;
    }

    // Listar todos os barbeiros
    @GetMapping
    public ResponseEntity<List<BarbeiroListagemDTO>> listarBarbeiros() {
        List<BarbeiroListagemDTO> barbeiros = barbeiroService.listarTodos();
        return ResponseEntity.ok(barbeiros);
    }

    // Buscar barbeiro por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscaBarbeiroPorId(Integer id) {
        Barbeiro barbeiro = barbeiroService.buscarPorId(id);

        if (barbeiro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Barbeiro não encontrado");
        }

        return ResponseEntity.ok(barbeiro);
    }

    // Cadastrar barbeiro
    @PostMapping
    public ResponseEntity<Barbeiro> cadastrarBarbeiro(@RequestBody BarbeiroCadastroDTO dto) {
        Barbeiro barbeiroSalvo = barbeiroService.cadastrarBarbeiro(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(barbeiroSalvo);
    }

    // Atualizar barbeiro
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarBarbeiro(@PathVariable Integer id, @RequestBody BarbeiroCadastroDTO barbeiro) {
        Barbeiro barbeiroAtualizado = barbeiroService.atualizarBarbeiro(id, barbeiro);

        if (barbeiroAtualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Barbeiro não encontrado");
        }
        return ResponseEntity.ok(barbeiroAtualizado);
    }

    // Deletar barbeiro
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerBarbeiro(@PathVariable Integer id) {
        Barbeiro barbeiroDeletado = barbeiroService.deletarBarbeiro(id);

        if (barbeiroDeletado == null) {
            return ResponseEntity.badRequest().body("Barbeiro não encontrado");
        }
        return ResponseEntity.ok("Barbeiro " + id + " foi deletado com sucesso");
    }
}
