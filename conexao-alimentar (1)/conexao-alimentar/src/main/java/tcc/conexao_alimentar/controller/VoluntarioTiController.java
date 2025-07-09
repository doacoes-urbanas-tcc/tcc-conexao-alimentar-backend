package tcc.conexao_alimentar.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.VoluntarioTiRequestDTO;
import tcc.conexao_alimentar.DTO.VoluntarioTiResponseDTO;
import tcc.conexao_alimentar.service.VoluntarioTiService;
@RestController
@RequestMapping("/voluntario-ti")
@RequiredArgsConstructor
public class VoluntarioTiController {
    private final VoluntarioTiService voluntarioTiService;

    @Operation(summary = "Cadastrar perfil TI",description = "Permite que o voluntário do setor TI cadastre suas informações.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @PostMapping("/{voluntarioId}")
    @PreAuthorize("hasRole('VOLUNTARIO')")
    public ResponseEntity<Void> cadastrar(@PathVariable Long voluntarioId,@RequestBody @Valid VoluntarioTiRequestDTO dto) {
        voluntarioTiService.cadastrar(dto, voluntarioId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Visualizar perfil TI",description = "Retorna o perfil TI do voluntário.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    @GetMapping("/{voluntarioId}")
    @PreAuthorize("hasRole('VOLUNTARIO')")
    public ResponseEntity<VoluntarioTiResponseDTO> visualizarPerfilTI(@PathVariable Long voluntarioId) {
        var perfil = voluntarioTiService.visualizarPerfilTI(voluntarioId);
        return ResponseEntity.ok(perfil);
    }

    @Operation(summary = "Atualizar perfil TI",description = "Permite que o voluntário de TI atualize suas informações.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    @PatchMapping("/{voluntarioId}")
    @PreAuthorize("hasRole('VOLUNTARIO')")
    public ResponseEntity<Void> atualizarPerfilTI(@PathVariable Long voluntarioId,@RequestBody @Valid VoluntarioTiRequestDTO dto) {
        voluntarioTiService.atualizarPerfilTI(voluntarioId, dto);
        return ResponseEntity.ok().build();
    }

}
