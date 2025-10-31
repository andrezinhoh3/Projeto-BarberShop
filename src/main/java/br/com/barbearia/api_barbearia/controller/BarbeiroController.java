package br.com.barbearia.api_barbearia.controller;

import br.com.barbearia.api_barbearia.dto.barbeiro.BarbeiroCadastroDTO;
import br.com.barbearia.api_barbearia.dto.barbeiro.BarbeiroListagemDTO;
import br.com.barbearia.api_barbearia.model.Barbeiro;
import br.com.barbearia.api_barbearia.service.BarbeiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barbeiros")
@Tag(name = "Barbeiros", description = "Operações relacionados aos barbeiros.")
@SecurityRequirement(name = "bearerAuth")
public class BarbeiroController {

    private final BarbeiroService barbeiroService;

    public BarbeiroController(BarbeiroService barbeiroService) {
        this.barbeiroService = barbeiroService;
    }

    // Listar todos os barbeiros
    @GetMapping
    @Operation(
            summary = "Listar todos os barbeiros",
            description = "Retorna uma lista com todos os barbeiros cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
    })
    public ResponseEntity<List<BarbeiroListagemDTO>> listarBarbeiros() {
        List<BarbeiroListagemDTO> barbeiros = barbeiroService.listarTodos();
        return ResponseEntity.ok(barbeiros);
    }

    // Buscar barbeiro por ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar barbeiro por ID",
            description = "Retorna um barbeiro especifico com base no seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barbeiro encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Barbeiro não encontrado para o ID informado")
    })
    public ResponseEntity<?> buscaBarbeiroPorId(Integer id) {
        Barbeiro barbeiro = barbeiroService.buscarPorId(id);

        if (barbeiro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Barbeiro não encontrado");
        }

        return ResponseEntity.ok(barbeiro);
    }

    // Cadastrar barbeiro
    @PostMapping
    @Operation(
            summary = "Cadastrar um novo barbeiro",
            description = "Adiciona um barbeiro ao banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barbeiro cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos fornecido para o cadastro")
    })
    public ResponseEntity<Barbeiro> cadastrarBarbeiro(@RequestBody BarbeiroCadastroDTO dto) {
        Barbeiro barbeiroSalvo = barbeiroService.cadastrarBarbeiro(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(barbeiroSalvo);
    }

    // Atualizar barbeiro
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza um barbeiro existente",
            description = "Altera os dados de um usuário com base no seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barbeiro atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Barbeiro não encontrado para o ID informado")
    })
    public ResponseEntity<?> atualizarBarbeiro(@PathVariable Integer id, @RequestBody BarbeiroCadastroDTO barbeiro) {
        Barbeiro barbeiroAtualizado = barbeiroService.atualizarBarbeiro(id, barbeiro);

        if (barbeiroAtualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Barbeiro não encontrado");
        }
        return ResponseEntity.ok(barbeiroAtualizado);
    }

    // Deletar barbeiro
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Exclui barbeiro",
            description = "Remove um barbeiro do banco de dados com base no seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barbeiro excluido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Barbeiro não encontrado para o ID informado")
    })
    public ResponseEntity<?> removerBarbeiro(@PathVariable Integer id) {
        Barbeiro barbeiroDeletado = barbeiroService.deletarBarbeiro(id);

        if (barbeiroDeletado == null) {
            return ResponseEntity.badRequest().body("Barbeiro não encontrado");
        }
        return ResponseEntity.ok("Barbeiro " + id + " foi deletado com sucesso");
    }
}
