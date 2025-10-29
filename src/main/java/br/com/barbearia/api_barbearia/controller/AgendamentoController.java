package br.com.barbearia.api_barbearia.controller;

import br.com.barbearia.api_barbearia.dto.agendamento.AgendamentoListagemDTO;
import br.com.barbearia.api_barbearia.dto.agendamento.AgendamentosCadastroDTO;
import br.com.barbearia.api_barbearia.model.Agendamento;
import br.com.barbearia.api_barbearia.service.AgendamentoService;
import br.com.barbearia.api_barbearia.status.StatusAgendamento;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@SecurityRequirement(name = "bearerAuth")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    // Listar Todos os agendamentos
    @GetMapping
    public ResponseEntity<List<AgendamentoListagemDTO>> listarAgendamentos() {
        List<AgendamentoListagemDTO> agendamentos = agendamentoService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }

    // Buscar agendamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarAgendamentoPorId(@PathVariable Integer id) {
        AgendamentoListagemDTO agendamento = agendamentoService.buscaPorId(id);

        if (agendamento == null) {
            return ResponseEntity.badRequest().body("Agendamento não encontrado");
        }
        return ResponseEntity.ok(agendamento);
    }

    // Criar Agendamento
    @PostMapping
    public ResponseEntity<Agendamento> criarAgendamento(@RequestBody AgendamentosCadastroDTO dto) {
        Agendamento agendamentoCriado = agendamentoService.criarAgendamento(dto);
        return ResponseEntity.ok(agendamentoCriado);
    }

    // Atualizar agendamento
    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Integer id, @RequestParam StatusAgendamento status) {
        Agendamento agendamentoAtualizado = agendamentoService.atualizarStatusAgendamento(id,status);

        if (agendamentoAtualizado == null) {
            return ResponseEntity.badRequest().body("Agendamento não encontrado");
        }

        return ResponseEntity.ok(agendamentoAtualizado);
    }

    // Deletar agendamento
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarAgendamento(@PathVariable Integer id) {
        Agendamento agendamentoDeletado = agendamentoService.deletarAgendamento(id);

        if (agendamentoDeletado == null) {
            return ResponseEntity.badRequest().body("Agendamento não  encontrado");
        }
        return ResponseEntity.ok("Agendamento " + id + " foi deletado com sucesso");
    }
}
