package br.com.barbearia.api_barbearia.service;

import br.com.barbearia.api_barbearia.dto.barbeiro.BarbeiroCadastroDTO;
import br.com.barbearia.api_barbearia.dto.barbeiro.BarbeiroListagemDTO;
import br.com.barbearia.api_barbearia.model.Barbeiro;
import br.com.barbearia.api_barbearia.repository.BarbeiroRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BarbeiroService {

    private final BarbeiroRepository barbeiroRepository;

    public BarbeiroService(BarbeiroRepository barbeiroRepository) {
        this.barbeiroRepository = barbeiroRepository;
    }

    // Listar todos os barbeiros
    public List<BarbeiroListagemDTO> listarTodos() {
        List<Barbeiro> barbeiros = barbeiroRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        return barbeiros.stream()
                .map(this::converterParaListagemDTO)
                .collect(Collectors.toList());
    }

    private BarbeiroListagemDTO converterParaListagemDTO(Barbeiro barbeiro) {
        BarbeiroListagemDTO dto = new BarbeiroListagemDTO();

        dto.setId(barbeiro.getId());
        dto.setNome(barbeiro.getNome());
        dto.setEspecialidade(barbeiro.getEspecialidade());
        dto.setTelefone(barbeiro.getTelefone());
        return dto;
    }

    // Buscar barbeiro por ID
    public BarbeiroListagemDTO buscaPorId(Integer id) {
        Barbeiro barbeiro = barbeiroRepository.findById(id).orElse(null);

        if (barbeiro == null) {
            return null;
        }
        return converterParaListagemDTO(barbeiro);

    }

    // Cadastrar barbeiro
    public Barbeiro cadastrarBarbeiro(BarbeiroCadastroDTO dto) {
        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setNome(dto.getNome());
        barbeiro.setEspecialidade(dto.getEspecialidade());
        barbeiro.setTelefone(dto.getTelefone());
        return barbeiroRepository.save(barbeiro);
    }

    public Barbeiro buscarPorId(Integer id) {
        return barbeiroRepository.findById(id).orElse(null);
    }

    // Atualizar barbeiro
    public Barbeiro atualizarBarbeiro(Integer id, BarbeiroCadastroDTO novoBarbeiro) {
        Barbeiro barbeiroExistente = buscarPorId(id);

        if (barbeiroExistente == null) {
            return null;
        }
        barbeiroExistente.setNome(novoBarbeiro.getNome());
        barbeiroExistente.setEspecialidade(novoBarbeiro.getEspecialidade());
        barbeiroExistente.setTelefone(novoBarbeiro.getTelefone());
        return barbeiroRepository.save(barbeiroExistente);
    }

    // Deletar barbeiro
    public Barbeiro deletarBarbeiro(Integer id) {
        Barbeiro barbeiro = buscarPorId(id);

        if (barbeiro == null) {
            return null;
        }
        barbeiroRepository.delete(barbeiro);
        return barbeiro;
    }
}
