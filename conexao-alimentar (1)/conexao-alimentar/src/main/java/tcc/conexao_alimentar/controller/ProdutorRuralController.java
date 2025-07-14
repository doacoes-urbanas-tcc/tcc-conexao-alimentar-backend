package tcc.conexao_alimentar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import tcc.conexao_alimentar.DTO.AtualizarEmailDTO;
import tcc.conexao_alimentar.DTO.AtualizarSenhaDTO;
import tcc.conexao_alimentar.DTO.ProdutorRuralRequestDTO;
import tcc.conexao_alimentar.DTO.ProdutorRuralResponseDTO;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.ProdutorRuralMapper;
import tcc.conexao_alimentar.service.ProdutorRuralService;

@RestController
@RequestMapping("/produtor-rural")
@RequiredArgsConstructor
@Tag(name = "Produtores Rurais", description = "Endpoints para gerenciamento de produtores rurais")
public class ProdutorRuralController {

    private final ProdutorRuralService produtorRuralService;

    
    @Operation(summary = "Cadastro de produtor rural",description = "Permite que um produtor rural se cadastre no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produtor rural cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody @Valid ProdutorRuralRequestDTO dto) {
        produtorRuralService.cadastrar(dto);
        return ResponseEntity.ok("Produtor Rural cadastrado com sucesso! Aguarde aprovação.");
    }
    
    @Operation(summary = "Atualizar e-mail",description = "Permite que o produtor rural atualize seu endereço de e-mail.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "E-mail atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Produtor rural não encontrado")
    })
    @PatchMapping("/{id}/email")
    @PreAuthorize("hasRole('PRODUTOR_RURAL')")
    public ResponseEntity<Void> atualizarEmail(@PathVariable Long id, @RequestBody AtualizarEmailDTO dto) {
       produtorRuralService.atualizarEmail(id, dto.getNovoEmail());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar senha",description = "Permite que o produtor rural atualize sua senha.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Produtor rural não encontrado")
    })
    @PatchMapping("/{id}/senha")
    @PreAuthorize("hasRole('PRODUTOR_RURAL')")
    public ResponseEntity<Void> atualizarSenha(@PathVariable Long id, @RequestBody AtualizarSenhaDTO dto) {
        produtorRuralService.atualizarSenha(id, dto.getNovaSenha());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Visualizar perfil",description = "Retorna os dados do perfil do produtor rural.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Proutor rural não encontrado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PRODUTOR_RURAL')")
    public ResponseEntity<ProdutorRuralResponseDTO> visualizarPerfil(@PathVariable Long id) {
        var pr = produtorRuralService.buscarPorId(id)
                          .orElseThrow(() -> new RegraDeNegocioException(" Produtor rural não encontrado."));
        return ResponseEntity.ok(ProdutorRuralMapper.toResponse(pr));
    }


}
