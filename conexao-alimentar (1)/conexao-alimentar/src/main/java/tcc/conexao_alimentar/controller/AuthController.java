package tcc.conexao_alimentar.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tcc.conexao_alimentar.DTO.LoginRequestDTO;
import tcc.conexao_alimentar.DTO.LoginResponseDTO;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.security.UserDetailsImpl;
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

    @GetMapping("/test-encode")
    public String encodePassword() {
    return passwordEncoder.encode("123456");
}


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

}


