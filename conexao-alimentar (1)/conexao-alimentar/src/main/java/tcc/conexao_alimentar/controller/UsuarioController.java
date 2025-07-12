package tcc.conexao_alimentar.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.repository.UsuarioRepository;

@RestController
@RequestMapping("/admin/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints de gerenciamento de usuários e permissões")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    
    @Operation(summary = "Listar os cadastros pendentes",description = "Lista todos os cadastros pendentes de ativação. Somente ADMIN pode acessar")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
    })
    @GetMapping("/pendentes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarPendentes() {
        List<UsuarioModel> pendentes = usuarioRepository.findByAtivoFalse();
        return ResponseEntity.ok(pendentes);
    }

    @Operation(summary = "Listar os cadastros pendentes por tipo",description = "Lista todos os cadastros pendentes de ativação de um determinado tipo de usuário. Somente ADMIN pode acessar")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
    })
    @GetMapping("/pendentes/{tipo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarPendentesPorTipo() {
    List<UsuarioModel> pendentes = usuarioRepository.findByAtivoFalse();
    Map<TipoUsuario, List<UsuarioModel>> agrupados = pendentes.stream()
            .collect(Collectors.groupingBy(UsuarioModel::getTipoUsuario));
    return ResponseEntity.ok(agrupados);
   }

    @Operation(summary = "Permite aprovar cadastro por id",description = "Permite aprovar um cadastro pendente pelo id. Somente ADMIN pode acessar")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário aprovado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais de autenticação inválidas"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
    })

    @PatchMapping("/aprovar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> aprovarUsuario(@PathVariable Long id) {
        UsuarioModel usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuário aprovado com sucesso!");
    }
    
    @Operation(summary = "Permite reprovar ou desativar cadastro por id",description = "Permite reprovar ou desativar um cadastro pelo id. Somente ADMIN pode acessar")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário reprovado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais de autenticação inválidas"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
    })
    @PatchMapping("/aprovar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> reprovarouDesativarUsuario(@PathVariable Long id) {
        UsuarioModel usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuário reprovado ou desativado com sucesso!");
    }

    
    @Operation(summary = "Listar os usuários ativos por tipo",description = "Lista todos os cadastros ativos de um determinado tipo de usuário. Somente ADMIN pode acessar")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
    })
    @GetMapping ("/{tipo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarUsuariosPorTipo() {
    List<UsuarioModel>  ativos = usuarioRepository.findByAtivo(true);
    Map<TipoUsuario, List<UsuarioModel>> agrupados = ativos.stream()
            .collect(Collectors.groupingBy(UsuarioModel::getTipoUsuario));
    return ResponseEntity.ok(agrupados);
   }

    @Operation(summary = "Listar todos usuários ativos ",description = "Lista todos os cadastros ativos. Somente ADMIN pode acessar")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
    })
    @GetMapping 
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarUsuarios() {
    List<UsuarioModel>  usuarios = usuarioRepository.findByAtivo(true);
    return ResponseEntity.ok(usuarios);
   }




}
