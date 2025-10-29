package br.com.barbearia.api_barbearia.service;

import br.com.barbearia.api_barbearia.dto.servico.ServicoCadastroDTO;
import br.com.barbearia.api_barbearia.dto.servico.ServicoListagemDTO;
import br.com.barbearia.api_barbearia.model.Servico;
import br.com.barbearia.api_barbearia.repository.ServicoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    // Listar todos servicos
    public List<ServicoListagemDTO> listarTodos() {
        List<Servico> servicos = servicoRepository.findAll(Sort.by(Sort.Direction.ASC ,"id"));
        return servicos.stream()
                .map(this::converterParaListagemDTO)
                .collect(Collectors.toList());
    }

    private ServicoListagemDTO converterParaListagemDTO(Servico servico) {
        ServicoListagemDTO dto = new ServicoListagemDTO();

        dto.setId(servico.getId());
        dto.setNome(servico.getNome());
        dto.setDescricao(servico.getDescricao());
        dto.setPreco(servico.getPreco());
        dto.setDuracao(servico.getDuracao());
        return dto;
    }

    // Buscar por ID
    public ServicoListagemDTO buscaPorId(Integer id) {
        Servico servico = servicoRepository.findById(id).orElse(null);

        if (servico == null) {
            return null;
        }

        return converterParaListagemDTO(servico);
    }

    // Cadastrar Servico
    public Servico cadastrarServico(ServicoCadastroDTO dto) {
        Servico servico = new Servico();
        servico.setNome(dto.getNome());
        servico.setDescricao(dto.getDescricao());
        servico.setPreco(dto.getPreco());
        servico.setDuracao(dto.getDuracao());
        return servicoRepository.save(servico);
    }

    public Servico buscarPorId(Integer id) {
        return servicoRepository.findById(id).orElse(null);
    }

    // Atualizar Servico
    public Servico atualizarServico(Integer id, ServicoCadastroDTO novoServico) {
        Servico servicoExistente = buscarPorId(id);

        if (servicoExistente == null) {
            return null;
        }

        servicoExistente.setNome(novoServico.getNome());
        servicoExistente.setDescricao(novoServico.getDescricao());
        servicoExistente.setPreco(novoServico.getPreco());
        servicoExistente.setDuracao(novoServico.getDuracao());
        return servicoRepository.save(servicoExistente);
    }

    // Deletar Servico
    public Servico deletarServico(Integer id) {
        Servico servico = buscarPorId(id);

        if (servico == null) {
            return null;
        }

        servicoRepository.delete(servico);
        return servico;
    }
}
