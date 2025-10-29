package br.com.barbearia.api_barbearia.controller;

import br.com.barbearia.api_barbearia.dto.servico.ServicoCadastroDTO;
import br.com.barbearia.api_barbearia.dto.servico.ServicoListagemDTO;
import br.com.barbearia.api_barbearia.model.Servico;
import br.com.barbearia.api_barbearia.service.ServicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
@SecurityRequirement(name = "bearerAuth")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    // Listar todos os serviços
    @GetMapping
    public ResponseEntity<List<ServicoListagemDTO>> listarServicos() {
        List<ServicoListagemDTO> servicos = servicoService.listarTodos();
        return ResponseEntity.ok(servicos);
    }

    // Buscar serviço por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarServicoPorId(Integer id) {
        ServicoListagemDTO servico = servicoService.buscaPorId(id);

        if (servico == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado");
        }
        return ResponseEntity.ok(servico);
    }

    // Cadastrar Servicos
    @PostMapping
    public ResponseEntity<Servico> cadastrarServico(@RequestBody ServicoCadastroDTO dto) {
        Servico servicoSalvo = servicoService.cadastrarServico(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoSalvo);
    }

    // Atualizar Servicos
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarServico(@PathVariable Integer id, @RequestBody ServicoCadastroDTO servico) {
        Servico servicoAtualizado = servicoService.atualizarServico(id, servico);

        if (servicoAtualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado");
        }
        return ResponseEntity.ok(servicoAtualizado);
    }

    // Deletar Servicos
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerServico(@PathVariable Integer id) {
        Servico servicoDeletado = servicoService.deletarServico(id);

        if (servicoDeletado == null) {
            return ResponseEntity.badRequest().body("Serviço não encontrado");
        }
        return ResponseEntity.ok("Serviço " + id + " foi deletado com sucesso");
    }
}
