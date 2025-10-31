package br.com.barbearia.api_barbearia.controller;

import br.com.barbearia.api_barbearia.dto.servico.ServicoCadastroDTO;
import br.com.barbearia.api_barbearia.dto.servico.ServicoListagemDTO;
import br.com.barbearia.api_barbearia.model.Servico;
import br.com.barbearia.api_barbearia.service.ServicoService;
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
@RequestMapping("/servicos")
@Tag(name = "Serviços", description = "Operações relacionados ao serviços.")
@SecurityRequirement(name = "bearerAuth")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    // Listar todos os serviços
    @GetMapping
    @Operation(
            summary = "Listar todos os serviços",
            description = "Retorna uma lista com todos os serviços cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
    })
    public ResponseEntity<List<ServicoListagemDTO>> listarServicos() {
        List<ServicoListagemDTO> servicos = servicoService.listarTodos();
        return ResponseEntity.ok(servicos);
    }

    // Buscar serviço por ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar serviço por ID",
            description = "Retorna um serviço especifico com base no seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado para o ID informado")
    })
    public ResponseEntity<?> buscarServicoPorId(Integer id) {
        ServicoListagemDTO servico = servicoService.buscaPorId(id);

        if (servico == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado");
        }
        return ResponseEntity.ok(servico);
    }

    // Cadastrar Servicos
    @PostMapping
    @Operation(
            summary = "Cadastrar um novo serviço",
            description = "Adiciona um serviço ao banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos fornecido para o cadastro")
    })
    public ResponseEntity<Servico> cadastrarServico(@RequestBody ServicoCadastroDTO dto) {
        Servico servicoSalvo = servicoService.cadastrarServico(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoSalvo);
    }

    // Atualizar Servicos
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza um serviço existente",
            description = "Altera os dados de um serviço com base no seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado para o ID informado")
    })
    public ResponseEntity<?> atualizarServico(@PathVariable Integer id, @RequestBody ServicoCadastroDTO servico) {
        Servico servicoAtualizado = servicoService.atualizarServico(id, servico);

        if (servicoAtualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado");
        }
        return ResponseEntity.ok(servicoAtualizado);
    }

    // Deletar Servicos
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Exclui serviço",
            description = "Remove um serviço do banco de dados com base no seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço excluido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado para o ID informado")
    })
    public ResponseEntity<?> removerServico(@PathVariable Integer id) {
        Servico servicoDeletado = servicoService.deletarServico(id);

        if (servicoDeletado == null) {
            return ResponseEntity.badRequest().body("Serviço não encontrado");
        }
        return ResponseEntity.ok("Serviço " + id + " foi deletado com sucesso");
    }
}
