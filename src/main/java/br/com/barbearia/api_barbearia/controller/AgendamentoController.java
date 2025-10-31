package br.com.barbearia.api_barbearia.controller;

import br.com.barbearia.api_barbearia.dto.agendamento.AgendamentoListagemDTO;
import br.com.barbearia.api_barbearia.dto.agendamento.AgendamentosCadastroDTO;
import br.com.barbearia.api_barbearia.model.Agendamento;
import br.com.barbearia.api_barbearia.service.AgendamentoService;
import br.com.barbearia.api_barbearia.service.EmailService;
import br.com.barbearia.api_barbearia.status.StatusAgendamento;
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
@RequestMapping("/agendamentos")
@Tag(name = "Agendamentos", description = "Operações relacionados aos agendamentos.")
@SecurityRequirement(name = "bearerAuth")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;
    private final EmailService emailService;

    public AgendamentoController(AgendamentoService agendamentoService, EmailService emailService) {
        this.agendamentoService = agendamentoService;
        this.emailService = emailService;
    }

    // Listar Todos os agendamentos
    @GetMapping
    @Operation(
            summary = "Listar todos os agendamentos",
            description = "Retorna uma lista com todos os agendamentos cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
    })
    public ResponseEntity<List<AgendamentoListagemDTO>> listarAgendamentos() {
        List<AgendamentoListagemDTO> agendamentos = agendamentoService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }

    // Buscar agendamento por ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar agendamento por ID",
            description = "Retorna um agendamento especifico com base no seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado para o ID informado")
    })
    public ResponseEntity<?> buscarAgendamentoPorId(@PathVariable Integer id) {
        AgendamentoListagemDTO agendamento = agendamentoService.buscaPorId(id);

        if (agendamento == null) {
            return ResponseEntity.badRequest().body("Agendamento não encontrado");
        }
        return ResponseEntity.ok(agendamento);
    }

    // Criar Agendamento
    @PostMapping
    @Operation(
            summary = "Cadastrar um novo agendamento",
            description = "Adiciona um agendamento ao banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos fornecido para o agendamento")
    })
    public ResponseEntity<Agendamento> criarAgendamento(@RequestBody AgendamentosCadastroDTO dto) {
        Agendamento agendamentoCriado = agendamentoService.criarAgendamento(dto);

        if (agendamentoCriado.getStatus() == StatusAgendamento.CONFIRMADO) {
            emailService.enviarEmailConfirmacao(agendamentoCriado.getUsuario().getEmail(), agendamentoCriado.getUsuario().getNome());
            return  ResponseEntity.status(HttpStatus.CREATED).body(agendamentoCriado);
        }
        return ResponseEntity.badRequest().build();
    }

    // Atualizar agendamento
    @PutMapping("/{id}/status")
    @Operation(
            summary = "Atualiza um agendamento existente",
            description = "Altera os dados de um usuário com base no seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado para o ID informado")
    })
    public ResponseEntity<?> atualizarStatus(@PathVariable Integer id, @RequestParam StatusAgendamento status) {
        Agendamento agendamentoAtualizado = agendamentoService.atualizarStatusAgendamento(id,status);

        if (agendamentoAtualizado == null) {
            return ResponseEntity.badRequest().body("Agendamento não encontrado");
        }

        return ResponseEntity.ok(agendamentoAtualizado);
    }

    // Deletar agendamento
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Exclui agendamento",
            description = "Remove um agendamento do banco de dados com base no seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento excluido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado para o ID informado")
    })
    public ResponseEntity<?> deletarAgendamento(@PathVariable Integer id) {
        Agendamento agendamentoDeletado = agendamentoService.deletarAgendamento(id);

        if (agendamentoDeletado == null) {
            return ResponseEntity.badRequest().body("Agendamento não  encontrado");
        }
        return ResponseEntity.ok("Agendamento " + id + " foi deletado com sucesso");
    }
}
