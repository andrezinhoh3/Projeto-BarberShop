package br.com.barbearia.api_barbearia.model;

import br.com.barbearia.api_barbearia.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository UsuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.UsuarioRepository = usuarioRepository;
    }

    // Lista Todos Usuarios
    public List
