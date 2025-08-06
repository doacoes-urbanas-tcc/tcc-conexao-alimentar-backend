package tcc.conexao_alimentar.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.AvaliacaoRequestDTO;
import tcc.conexao_alimentar.DTO.AvaliacaoResponseDTO;
import tcc.conexao_alimentar.service.AvaliacaoService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/avaliacoes")
@Tag(name = "Avaliações", description = "Endpoints para gerenciamento de avaliações")
public class AvaliacaoController {
    private final AvaliacaoService avaliacaoService;

    
    @Operation(summary = "Envio de avaliação",description = "Permite que um usuário avalie outro após alguma ação como por exempo retirada de uma doação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliação efetuada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })

    @PostMapping("/{reservaId}/avaliar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AvaliacaoResponseDTO> avaliarPorReserva(@PathVariable Long reservaId,@RequestBody @Valid AvaliacaoRequestDTO dto) {
    AvaliacaoResponseDTO response = avaliacaoService.avaliarPorReserva(reservaId, dto);
    return ResponseEntity.ok(response);
   }

   @Operation(summary = "Listar avaliações de um usuário",description = "Permite que um usuário visualize as avaliações de outro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliação efetuada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @GetMapping("/{avaliadoId}")
    @PreAuthorize("hasRole('ADMIN') or isAuthenticated()")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarAvaliacoes(@PathVariable Long avaliadoId) {
        return ResponseEntity.ok(avaliacaoService.listarAvaliacoesDoUsuario(avaliadoId));
    }


}
