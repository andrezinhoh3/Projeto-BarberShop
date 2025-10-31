package br.com.barbearia.api_barbearia.service;

import br.com.barbearia.api_barbearia.dto.usuario.UsuarioCadastroDTO;
import br.com.barbearia.api_barbearia.dto.usuario.UsuarioListagemDTO;
import br.com.barbearia.api_barbearia.model.Usuario;
import br.com.barbearia.api_barbearia.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final EmailService emailService;
    private final UsuarioRepository usuarioRepository;

    private final  PasswordEncoder passwordEncoder;

    public UsuarioService(EmailService emailService, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.emailService = emailService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Lista Todos Usuarios
    public List<UsuarioListagemDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return usuarios.stream()
                .map(this::converterParaListagemDTO)
                .collect(Collectors.toList());
    }

    private UsuarioListagemDTO converterParaListagemDTO(Usuario usuario) {
        UsuarioListagemDTO dto = new UsuarioListagemDTO();

        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setDataCadastro(usuario.getDataCadastro());
        dto.setId(usuario.getId());
        return dto;
    }

    // Buscar por ID
    public UsuarioListagemDTO buscaPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario == null) {
            return null;
        }

        return converterParaListagemDTO(usuario);
    }

    // Recuperar senha
    public void recuperarSenha(String email) {
        usuarioRepository.findByEmail(email).ifPresent(usuario -> {
            String novaSenha = RandomStringUtils.randomAlphanumeric(10);
            String senhaCodificada = passwordEncoder.encode(novaSenha);
            usuario.setSenha(senhaCodificada);
            usuarioRepository.save(usuario);
            emailService.enviarEmail(usuario.getEmail(), novaSenha);
        });
    }

    // Cadastrar Usuario
    public Usuario cadastrarUsuario(UsuarioCadastroDTO dto) {
        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());


        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setSenha(dto.getSenha());
        usuario.setDataCadastro(OffsetDateTime.now());
        usuario.setSenha(senhaCriptografada);

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Atualizar Usuario
    public Usuario atualizarUsuario(Integer id, UsuarioCadastroDTO novoUsuario) {

        Usuario usuarioExistente =  buscarPorId(id);

        if(usuarioExistente == null) {
            return null;
        }
        usuarioExistente.setEmail(novoUsuario.getEmail());
        usuarioExistente.setNome(novoUsuario.getNome());
        usuarioExistente.setSenha(novoUsuario.getSenha());
        usuarioExistente.setTelefone(novoUsuario.getTelefone());

        return usuarioRepository.save(usuarioExistente);
    }

    // Deletar Usuario
    public Usuario deletarUsuario(Integer id) {
        Usuario usuario = buscarPorId(id);

        if (usuario == null) {
            return null;
        }

        usuarioRepository.delete(usuario);
        return usuario;
    }


    public Optional<Usuario> buscarPorEmail(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (email == null) {
            return null;
        }
        return usuario;
    }


}
