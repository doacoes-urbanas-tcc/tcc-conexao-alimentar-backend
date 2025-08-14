package tcc.conexao_alimentar.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.DashboardResumoDTO;
import tcc.conexao_alimentar.DTO.UsuarioResponseDTO;
import tcc.conexao_alimentar.mapper.UsuarioMapper;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.service.UsuarioService;


@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class DashboardAdminController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Visualização dos dados dos principais eixos do sistema para o administrador",description = "Permite que o administrador visualize o resumo das principais atividades do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Visualização auorizada"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @GetMapping("/resumo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DashboardResumoDTO> getResumoDashboard() {
        DashboardResumoDTO resumo = usuarioService.montarResumo();
        return ResponseEntity.ok(resumo);
    }
    
    @Operation(summary = "Visualização dos últimos 5 usuários com cadastro pendente de aprovação",description = "Permite que o administrador visualize um resumo dos dados dos últimos 5 usuários que se cadastraram no sistema ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Visualização auorizada"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @GetMapping("/usuarios/pendentes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUltimosPendentes() {
    List<UsuarioModel> pendentes = usuarioService.buscarUltimosPendentes();

    List<UsuarioResponseDTO> dtos = pendentes.stream()
        .map(UsuarioMapper::toResponse)
        .toList();

    return ResponseEntity.ok(dtos);
}


}



