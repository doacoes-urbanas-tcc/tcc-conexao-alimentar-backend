package tcc.conexao_alimentar.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.ComercioResponseDTO;
import tcc.conexao_alimentar.DTO.JustificativaRequestDTO;
import tcc.conexao_alimentar.DTO.OngResponseDTO;
import tcc.conexao_alimentar.DTO.PessoaFisicaResponseDTO;
import tcc.conexao_alimentar.DTO.ProdutorRuralResponseDTO;
import tcc.conexao_alimentar.DTO.VoluntarioResponseDTO;
import tcc.conexao_alimentar.enums.StatusUsuario;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.repository.UsuarioRepository;
import tcc.conexao_alimentar.service.ComercioService;
import tcc.conexao_alimentar.service.EmailService;
import tcc.conexao_alimentar.service.OngService;
import tcc.conexao_alimentar.service.PessoaFisicaService;
import tcc.conexao_alimentar.service.ProdutorRuralService;
import tcc.conexao_alimentar.service.UsuarioService;
import tcc.conexao_alimentar.service.VoluntarioService;

@RestController
@RequestMapping("/admin/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints de gerenciamento de usuários e permissões")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ComercioService comercioService;
    private final OngService ongService;
    private final PessoaFisicaService pessoaFisicaService;
    private final ProdutorRuralService produtorRuralService;
    private final VoluntarioService voluntarioService;
    private final EmailService emailService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/pendentes/{tipo}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar usuários pendentes por tipo", description = "Lista cadastros pendentes conforme tipo.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuários carregados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    public ResponseEntity<List<Object>> listarPendentesPorTipo(@PathVariable TipoUsuario tipo) {
    List<Object> pendentes = usuarioService.listarUsuariosPendentes();

    List<Object> filtrados = pendentes.stream()
        .filter(usuario -> {
            switch (tipo) {
                case COMERCIO: return usuario instanceof ComercioResponseDTO;
                case ONG: return usuario instanceof OngResponseDTO;
                case PESSOA_FISICA: return usuario instanceof PessoaFisicaResponseDTO;
                case PRODUTOR_RURAL: return usuario instanceof ProdutorRuralResponseDTO;
                case VOLUNTARIO: return usuario instanceof VoluntarioResponseDTO;
                default: return false;
            }
        })
        .collect(Collectors.toList());

    return ResponseEntity.ok(filtrados);
    }

    @GetMapping("/ativos/{tipo}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar usuários ativos por tipo", description = "Lista cadastros ativos conforme tipo.")
        @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuários carregados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
     public ResponseEntity<List<Object>> listarAtivosPorTipo(@PathVariable TipoUsuario tipo) {
       List<Object> ativos = usuarioService.listarUsuariosPorStatus(StatusUsuario.ATIVO);

       List<Object> filtrados = ativos.stream()
        .filter(usuario -> {
            switch (tipo) {
                case COMERCIO: return usuario instanceof ComercioResponseDTO;
                case ONG: return usuario instanceof OngResponseDTO;
                case PESSOA_FISICA: return usuario instanceof PessoaFisicaResponseDTO;
                case PRODUTOR_RURAL: return usuario instanceof ProdutorRuralResponseDTO;
                case VOLUNTARIO: return usuario instanceof VoluntarioResponseDTO;
                default: return false;
            }
        })
        .collect(Collectors.toList());

    return ResponseEntity.ok(filtrados);
    }


    @GetMapping("/pendentes")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos usuários pendentes", description = "Lista todos os cadastros pendentes.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuários carregados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    public ResponseEntity<List<Object>> listarUsuariosPendentes() {
        return ResponseEntity.ok(usuarioService.listarUsuariosPendentes());
    }

    @GetMapping("/ativos")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos usuários ativos", description = "Lista todos os cadastros ativos.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuários carregados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    public ResponseEntity<List<UsuarioModel>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarAtivos());
    }

    @GetMapping("/reprovados")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar usuários reprovados", description = "Lista todos os usuários reprovados.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuários carregados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    public ResponseEntity<List<Object>> listarUsuariosReprovados() {
        return ResponseEntity.ok(usuarioService.listarUsuariosReprovados());
    }


    @PatchMapping("/aprovar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Aprovar usuário por ID", description = "Aprova um cadastro pendente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário aprovado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    public ResponseEntity<String> aprovarUsuario(@PathVariable Long id) {
    UsuarioModel usuario = usuarioService.aprovarUsuario(id); 
    emailService.enviarEmailAprovacaoCadastro(usuario.getNome(), usuario.getEmail());
    return ResponseEntity.ok("Usuário aprovado com sucesso!");
   }

    @PatchMapping("/reprovar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Reprovar usuário por ID", description = "Reprova/desativa um cadastro.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário reprovado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    public ResponseEntity<Void> reprovarUsuario(@PathVariable Long id, @RequestBody JustificativaRequestDTO justificativaRequest) {
    UsuarioModel usuario = usuarioService.reprovarUsuario(id, justificativaRequest.getMotivo());
    emailService.enviarEmailReprovacaoCadastro(usuario.getNome(), usuario.getEmail());
    return ResponseEntity.ok().build();
    }

    @PatchMapping("/desativar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Desativar usuário", description = "Desativa um usuário e envia justificativa por e-mail.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário desativado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
     public ResponseEntity<Void> desativarUsuario(@PathVariable Long id, @RequestBody JustificativaRequestDTO justificativaRequest) {
    UsuarioModel usuario = usuarioService.buscarPorId(id).orElseThrow();
    usuario.setStatus(StatusUsuario.DESATIVADO);
    usuario.setJustificativaReprovacao(justificativaRequest.getMotivo());
    usuarioRepository.save(usuario);

    emailService.enviarEmailContaDesativada(usuario.getNome(), usuario.getEmail(), justificativaRequest.getMotivo());

    return ResponseEntity.ok().build();
    }



    @GetMapping("/perfil")
    @PreAuthorize("hasRole('ADMIN') or isAuthenticated()")
    @Operation(summary = "Visualizar perfil de um usuário por tipo", description = "Permite visualizar o perfil completo de um usuário.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil carregado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    public ResponseEntity<?> visualizarPerfilAdmin(@RequestParam Long id, @RequestParam TipoUsuario tipo) {
        return switch (tipo) {
            case COMERCIO -> ResponseEntity.ok(comercioService.visualizarPerfil(id));
            case ONG -> ResponseEntity.ok(ongService.visualizarPerfil(id));
            case PESSOA_FISICA -> ResponseEntity.ok(pessoaFisicaService.visualizarPerfil(id));
            case PRODUTOR_RURAL -> ResponseEntity.ok(produtorRuralService.visualizarPerfil(id));
            case VOLUNTARIO -> ResponseEntity.ok(voluntarioService.visualizarPerfilVoluntario(id));
            default -> throw new IllegalArgumentException("Unexpected value: " + tipo);
        };
    }
    @GetMapping("/usuario/localizacao")
    public ResponseEntity<Map<String, Double>> getLocalizacaoUsuarioLogado() {
    UsuarioModel usuario = usuarioService.getUsuarioLogado();
    Map<String, Double> coords = new HashMap<>();
    coords.put("latitude", usuario.getEndereco().getLatitude());
    coords.put("longitude", usuario.getEndereco().getLongitude());
    return ResponseEntity.ok(coords);
}



    

}




