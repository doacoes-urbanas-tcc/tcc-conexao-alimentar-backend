package tcc.conexao_alimentar.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.ComercioResponseDTO;
import tcc.conexao_alimentar.DTO.OngResponseDTO;
import tcc.conexao_alimentar.DTO.PessoaFisicaResponseDTO;
import tcc.conexao_alimentar.DTO.ProdutorRuralResponseDTO;
import tcc.conexao_alimentar.DTO.VoluntarioResponseDTO;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.service.UsuarioService;

@RestController
@RequestMapping("/admin/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints de gerenciamento de usuários e permissões")
public class UsuarioController {

    private final UsuarioService usuarioService;

    
    @Operation(summary = "Listar Comércios pendentes", description = "Lista todos os comércios pendentes de aprovação. Somente ADMIN pode acessar.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @GetMapping("/pendentes/comercios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ComercioResponseDTO>> listarComerciosPendentes() {
        return ResponseEntity.ok(usuarioService.listarComerciosPendentes());
    }

    @Operation(summary = "Listar ONGs pendentes", description = "Lista todas as ONGs pendentes de aprovação. Somente ADMIN pode acessar.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @GetMapping("/pendentes/ongs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OngResponseDTO>> listarOngsPendentes() {
        return ResponseEntity.ok(usuarioService.listarOngsPendentes());
    }

    @Operation(summary = "Listar Pessoas Físicas pendentes", description = "Lista todas as pessoas físicas pendentes de aprovação. Somente ADMIN pode acessar.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @GetMapping("/pendentes/pessoas-fisicas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PessoaFisicaResponseDTO>> listarPessoasFisicasPendentes() {
        return ResponseEntity.ok(usuarioService.listarPessoasFisicasPendentes());
    }

    @Operation(summary = "Listar Produtores Rurais pendentes", description = "Lista todos os produtores rurais pendentes de aprovação. Somente ADMIN pode acessar.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @GetMapping("/pendentes/produtores-rurais")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProdutorRuralResponseDTO>> listarProdutoresRuraisPendentes() {
        return ResponseEntity.ok(usuarioService.listarProdutoresRuraisPendentes());
    }

    @Operation(summary = "Listar Voluntários pendentes", description = "Lista todos os voluntários pendentes de aprovação. Somente ADMIN pode acessar.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @GetMapping("/pendentes/voluntarios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<VoluntarioResponseDTO>> listarVoluntariosPendentes() {
        return ResponseEntity.ok(usuarioService.listarVoluntariosPendentes());
    }

    @Operation(summary = "Listar Comércios ativos", description = "Lista todos os comércios ativos. Somente ADMIN pode acessar.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @GetMapping("/ativos/comercios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ComercioResponseDTO>> listarComerciosAtivos() {
        return ResponseEntity.ok(usuarioService.listarComerciosAtivos());
    }

    @Operation(summary = "Listar ONGs ativas", description = "Lista todas as ONGs ativas. Somente ADMIN pode acessar.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @GetMapping("/ativos/ongs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OngResponseDTO>> listarOngsAtivas() {
        return ResponseEntity.ok(usuarioService.listarOngsAtivas());
    }

    @Operation(summary = "Listar Pessoas Físicas ativas", description = "Lista todas as pessoas físicas ativas. Somente ADMIN pode acessar.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @GetMapping("/ativos/pessoas-fisicas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PessoaFisicaResponseDTO>> listarPessoasFisicasAtivas() {
        return ResponseEntity.ok(usuarioService.listarPessoasFisicasAtivas());
    }

    @Operation(summary = "Listar Produtores Rurais ativos", description = "Lista todos os produtores rurais ativos. Somente ADMIN pode acessar.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @GetMapping("/ativos/produtores-rurais")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProdutorRuralResponseDTO>> listarProdutoresRuraisAtivos() {
        return ResponseEntity.ok(usuarioService.listarProdutoresRuraisAtivos());
    }

    @Operation(summary = "Listar Voluntários ativos", description = "Lista todos os voluntários ativos. Somente ADMIN pode acessar.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @GetMapping("/ativos/voluntarios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<VoluntarioResponseDTO>> listarVoluntariosAtivos() {
        return ResponseEntity.ok(usuarioService.listarVoluntariosAtivos());
    }


    

    @Operation(summary = "Listar os cadastros pendentes", description = "Lista todos os cadastros pendentes de ativação. Somente ADMIN pode acessar.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @GetMapping("/pendentes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioModel>> listarPendentes() {
        return ResponseEntity.ok(usuarioService.listarPendentes());
    }

    @Operation(summary = "Listar todos usuários ativos", description = "Lista todos os cadastros ativos. Somente ADMIN pode acessar.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @GetMapping("/ativos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioModel>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarAtivos());
    }

    @Operation(summary = "Aprovar usuário por id", description = "Permite aprovar um cadastro pendente. Somente ADMIN pode acessar.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário aprovado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @PatchMapping("/aprovar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> aprovarUsuario(@PathVariable Long id) {
        usuarioService.aprovarUsuario(id);
        return ResponseEntity.ok("Usuário aprovado com sucesso!");
    }

     @Operation(summary = "Reprovar ou desativar usuário por id", description = "Permite reprovar ou desativar um cadastro. Somente ADMIN pode acessar.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário reprovado/desativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @PatchMapping("/reprovar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> reprovarOuDesativarUsuario(@PathVariable Long id) {
        usuarioService.reprovarOuDesativarUsuario(id);
        return ResponseEntity.ok("Usuário reprovado ou desativado com sucesso!");
    }
}




