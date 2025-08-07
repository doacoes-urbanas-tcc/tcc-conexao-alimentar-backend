package tcc.conexao_alimentar.controller;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import tcc.conexao_alimentar.DTO.LoginRequestDTO;
import tcc.conexao_alimentar.DTO.LoginResponseDTO;
import tcc.conexao_alimentar.model.RecuperarSenhaToken;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.repository.RecuperarSenhaTokenRepository;
import tcc.conexao_alimentar.repository.UsuarioRepository;
import tcc.conexao_alimentar.security.UserDetailsImpl;
import tcc.conexao_alimentar.service.EmailService;
import tcc.conexao_alimentar.service.JwtService;
import tcc.conexao_alimentar.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final RecuperarSenhaTokenRepository tokenRepository;
    private final EmailService emailService;


    @GetMapping("/test-encode")
    public String encodePassword() {
    return passwordEncoder.encode("123456");

    
    }

    
    @Operation(summary = "Login de usuários",description = "Permite que um usuário faça login no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {
    try {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
        );

        UserDetails user = userDetailsService.loadUserByUsername(dto.getEmail());
        String token = jwtService.generateToken(user);
        UsuarioModel usuario = ((UserDetailsImpl) user).getUsuario();

        return ResponseEntity.ok(new LoginResponseDTO(
            token,
            usuario.getTipoUsuario().name(),
            usuario.getId()
        ));
    } catch (Exception e) {
        return ResponseEntity.status(401).body("Falha na autenticação: " + e.getMessage());
    }
    }

    
    @Operation(summary = "Recuperação de senha",description = "Permite que um usuário solicite um email para a recupeção de senha")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Senha recuperada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })

    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> solicitarRecuperacao(@RequestBody Map<String, String> body) {
    String email = body.get("email");

    Optional<UsuarioModel> usuario = usuarioRepository.findByEmail(email);
    if (usuario.isEmpty()) {
        return ResponseEntity.badRequest().body("E-mail não encontrado");
    }

    String token = UUID.randomUUID().toString();
    LocalDateTime expiracao = LocalDateTime.now().plusHours(2);

    RecuperarSenhaToken recuperacao = new RecuperarSenhaToken();
    recuperacao.setEmail(email);
    recuperacao.setToken(token);
    recuperacao.setExpiracao(expiracao);
    tokenRepository.save(recuperacao);

    String link = "https://conexao-alimentar.onrender.com/pages/perfil/redefinir-senha.html?token=" + token;

   String conteudoHtml = """
  <!DOCTYPE html>
  <html lang="pt-BR">
  <head>
    <meta charset="UTF-8">
  </head>
  <body style="margin: 0; padding: 0; background-color: #fef2f2; font-family: Arial, sans-serif;">
    <div style="max-width: 600px; margin: 40px auto; background-color: #ffffff; padding: 30px; border-radius: 12px; box-shadow: 0 0 10px rgba(0,0,0,0.1); text-align: center;">
        <img src="https://res.cloudinary.com/du9zmknbe/image/upload/logo.png" alt="Conexão Alimentar" style="max-width: 150px; margin-bottom: 25px;" />
        <h2 style="font-size: 24px; color: #b91c1c; margin-bottom: 10px;">Redefinição de Senha</h2>
        <p style="font-size: 16px; color: #333;">Olá! Recebemos uma solicitação para redefinir sua senha.</p>
        <p style="font-size: 16px; color: #333;">Clique no botão abaixo para continuar:</p>
        <a href="%s"
           style="display: inline-block; padding: 14px 28px; background-color: #b91c1c; color: #ffffff !important; text-decoration: none; border-radius: 8px; font-weight: bold; font-size: 16px; margin-top: 20px;">
           Redefinir Senha
        </a>
        <p style="font-size: 16px; color: #333; margin-top: 20px;">Se você não solicitou isso, apenas ignore este e-mail.</p>
        <div style="font-size: 12px; color: #888; margin-top: 30px;">
            Este link é válido por 2 horas.<br>
            © 2025 Conexão Alimentar
        </div>
    </div>
  </body>
   </html>
  """.formatted(link);


    emailService.enviarEmailHtml(email, "Redefinição de Senha", conteudoHtml);

    return ResponseEntity.ok("E-mail de redefinição enviado.");
   }

   
    @Operation(summary = "Redefinição de senha",description = "Permite que um usuário redefina sua senha atual, estando logado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Senha recuperada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String novaSenha = body.get("novaSenha");

        Optional<RecuperarSenhaToken> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token inválido.");
        }

        RecuperarSenhaToken recuperar = optionalToken.get();
        if (recuperar.getExpiracao().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token expirado.");
        }

        Optional<UsuarioModel> optionalUsuario = usuarioRepository.findByEmail(recuperar.getEmail());
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        UsuarioModel usuario = optionalUsuario.get();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        tokenRepository.delete(recuperar);

        return ResponseEntity.ok("Senha redefinida com sucesso!");
    }

}


