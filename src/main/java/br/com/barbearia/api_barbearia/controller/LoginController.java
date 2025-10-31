package br.com.barbearia.api_barbearia.controller;

import br.com.barbearia.api_barbearia.dto.login.LoginRequest;
import br.com.barbearia.api_barbearia.dto.login.LoginResponseDTO;
import br.com.barbearia.api_barbearia.dto.usuario.UsuarioListagemDTO;
import br.com.barbearia.api_barbearia.model.Usuario;
import br.com.barbearia.api_barbearia.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/auth/login")
@Tag(name = "Login de usuarios", description = "Operações relacionados aos login.")
public class LoginController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    public LoginController(UsuarioService usuarioService, AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping()
    @Operation(
            summary = "Login de usuário",
            description = "Faz o login de usuário."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "401", description = "Dados invalidos fornecido para o login")
    })
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getSenha()
            );
            Authentication auth = authenticationManager.authenticate(authToken);

            Optional<Usuario> usuario = usuarioService.buscarPorEmail(loginRequest.getEmail());
            if (usuario.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
            }

            Instant now = Instant.now();
            long validade = 3600L;

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("Barbearia")
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(validade))
                    .subject(auth.getName())
                    .claim("roles", auth.getAuthorities())
                    .build();

            JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
            String token = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

            // 👇 Converte a entidade em DTO
            UsuarioListagemDTO usuarioDTO = new UsuarioListagemDTO(usuario.get());

            return ResponseEntity.ok(new LoginResponseDTO(token, usuarioDTO));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }
}
