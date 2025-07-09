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
import tcc.conexao_alimentar.DTO.ComercioRequestDTO;
import tcc.conexao_alimentar.DTO.ComercioResponseDTO;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.ComercioMapper;
import tcc.conexao_alimentar.service.ComercioService;

@RestController
@RequestMapping("/comercio")
@RequiredArgsConstructor
@Tag(name = "Comércios", description = "Endpoints para gerenciamento de comércios")
public class ComercioController {

    
    private final ComercioService comercioService;

    @Operation(summary = "Cadastro de comércio",description = "Permite que um usuário cadastre um comércio no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comércio cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody @Valid ComercioRequestDTO dto) {
        comercioService.cadastrar(dto);
        return ResponseEntity.ok("Comércio cadastrado com sucesso! Aguarde aprovação.");
    }

    @Operation(summary = "Atualizar e-mail",description = "Permite que o usuário atualize seu endereço de e-mail.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "E-mail atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PatchMapping("/{id}/email")
    @PreAuthorize("hasRole('COMERCIO')")
    public ResponseEntity<Void> atualizarEmail(@PathVariable Long id, @RequestParam String novoEmail) {
       comercioService.atualizarEmail(id, novoEmail);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar senha",description = "Permite que o usuário atualize sua senha.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PatchMapping("/{id}/senha")
    @PreAuthorize("hasRole('COMERCIO')")
    public ResponseEntity<Void> atualizarSenha(@PathVariable Long id, @RequestParam String novaSenha) {
        comercioService.atualizarSenha(id, novaSenha);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Visualizar perfil",description = "Retorna os dados do perfil do usuário.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COMERCIO')")
    public ResponseEntity<ComercioResponseDTO> visualizarPerfil(@PathVariable Long id) {
        var comercio = comercioService.buscarPorId(id)
                          .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));
        return ResponseEntity.ok(ComercioMapper.toResponse(comercio));
    }


    



}
