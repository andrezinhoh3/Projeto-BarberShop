package br.com.barbearia.api_barbearia.service;

import br.com.barbearia.api_barbearia.dto.agendamento.AgendamentoListagemDTO;
import br.com.barbearia.api_barbearia.dto.agendamento.AgendamentosCadastroDTO;
import br.com.barbearia.api_barbearia.model.Agendamento;
import br.com.barbearia.api_barbearia.model.Barbeiro;
import br.com.barbearia.api_barbearia.model.Servico;
import br.com.barbearia.api_barbearia.model.Usuario;
import br.com.barbearia.api_barbearia.repository.AgendamentoRepository;
import br.com.barbearia.api_barbearia.repository.BarbeiroRepository;
import br.com.barbearia.api_barbearia.repository.ServicoRepository;
import br.com.barbearia.api_barbearia.repository.UsuarioRepository;
import br.com.barbearia.api_barbearia.status.StatusAgendamento;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ServicoRepository servicoRepository;
    private final BarbeiroRepository barbeiroRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, UsuarioRepository usuarioRepository, ServicoRepository servicoRepository, BarbeiroRepository barbeiroRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.servicoRepository = servicoRepository;
        this.barbeiroRepository = barbeiroRepository;
    }

    // Listar todos os agendamentos
    public List<AgendamentoListagemDTO> listarTodos() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return agendamentos.stream()
                .map(this::converterParaListagemDTO)
                .collect(Collectors.toList());
    }

    private AgendamentoListagemDTO converterParaListagemDTO(Agendamento agendamento) {
        AgendamentoListagemDTO dto = new AgendamentoListagemDTO();

        dto.setDataHora(agendamento.getDataHora());
        dto.setStatus(String.valueOf(agendamento.getStatus()));
        dto.setNomeUsuario(agendamento.getUsuario().getNome());
        dto.setTelefoneCliente(agendamento.getUsuario().getTelefone());
        dto.setNomeBarbeiro(agendamento.getBarbeiro().getNome());
        dto.setServico(agendamento.getServico().getNome());
        dto.setPreco(agendamento.getServico().getPreco());
        dto.setId(agendamento.getId());
        return dto;
    }

    // Buscar agendamento por ID
    public AgendamentoListagemDTO buscaPorId(Integer id) {
       Agendamento agendamento = agendamentoRepository.findById(id).orElse(null);

       if (agendamento == null) {
           return null;
       }
       return converterParaListagemDTO(agendamento);
    }

    public Agendamento buscarPorId(Integer id) {
        return agendamentoRepository.findById(id).orElse(null);
    }

    // Deletar agendamento
    public Agendamento deletarAgendamento(Integer id) {
        Agendamento agendamento = buscarPorId(id);

        if (agendamento == null) {
            return null;
        }

        agendamentoRepository.delete(agendamento);
        return agendamento;
    }


    // Criar agendamento

    public Agendamento criarAgendamento(AgendamentosCadastroDTO dto) {

        Usuario usuarioAssociado = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
        Servico servicoAssociado = servicoRepository.findById(dto.getIdServico()).orElse(null);
        Barbeiro barbeiroAssociado = barbeiroRepository.findById(dto.getIdBarbeiro()).orElse(null);

        if (usuarioAssociado == null || servicoAssociado == null || barbeiroAssociado == null) {
            return null;
        }

        Agendamento novoAgendamento = new Agendamento();

        novoAgendamento.setDataHora(dto.getDataHora());
        novoAgendamento.setUsuario(usuarioAssociado);
        novoAgendamento.setServico(servicoAssociado);
        novoAgendamento.setBarbeiro(barbeiroAssociado);


        novoAgendamento.setStatus(StatusAgendamento.valueOf("CONFIRMADO"));

        return agendamentoRepository.save(novoAgendamento);
    }

    // Atualizar status do agendamento
    @Transactional
    public Agendamento atualizarStatusAgendamento(Integer id, StatusAgendamento novoStatus) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));

        // Regras de negócio:
        // PENDENTE → CONFIRMADO ou CANCELADO
        // CONFIRMADO → CANCELADO
        if (agendamento.getStatus() == StatusAgendamento.PENDENTE) {
            if (novoStatus == StatusAgendamento.CONFIRMADO || novoStatus == StatusAgendamento.CANCELADO) {
                agendamento.setStatus(novoStatus);
            } else {
                throw new IllegalArgumentException("Status inválido para alteração");
            }
        } else if (agendamento.getStatus() == StatusAgendamento.CONFIRMADO) {
            if (novoStatus == StatusAgendamento.CANCELADO) {
                agendamento.setStatus(novoStatus);
            } else {
                throw new IllegalArgumentException("Status inválido para alteração");
            }
        } else {
            throw new IllegalArgumentException("Não é possível alterar agendamentos cancelados");
        }

        return agendamentoRepository.save(agendamento);
    }



}
