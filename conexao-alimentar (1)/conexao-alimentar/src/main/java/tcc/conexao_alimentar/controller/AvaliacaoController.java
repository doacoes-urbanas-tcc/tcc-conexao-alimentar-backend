package tcc.conexao_alimentar.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.AvaliacaoRequestDTO;
import tcc.conexao_alimentar.DTO.AvaliacaoResponseDTO;
import tcc.conexao_alimentar.service.AvaliacaoService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/avaliacoes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AvaliacaoController {
    private final AvaliacaoService avaliacaoService;

    @Operation(summary = "Avaliar um usuário")
    @PostMapping("/avaliar/{avaliadoId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AvaliacaoResponseDTO> avaliar(@PathVariable Long avaliadoId,@RequestBody @Valid AvaliacaoRequestDTO dto) {
        AvaliacaoResponseDTO response = avaliacaoService.avaliar(avaliadoId, dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar avaliações de um usuário")
    @GetMapping("/{avaliadoId}")
    @PreAuthorize("hasRole('ADMIN') or isAuthenticated()")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarAvaliacoes(
            @PathVariable Long avaliadoId) {
        return ResponseEntity.ok(avaliacaoService.listarAvaliacoesDoUsuario(avaliadoId));
    }

}
