package tcc.conexao_alimentar.controller;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import tcc.conexao_alimentar.DTO.AtualizarEmailDTO;
import tcc.conexao_alimentar.DTO.AtualizarSenhaDTO;
import tcc.conexao_alimentar.DTO.ComercioRequestDTO;
import tcc.conexao_alimentar.DTO.ComercioResponseDTO;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.ComercioMapper;
import tcc.conexao_alimentar.service.ComercioService;
import tcc.conexao_alimentar.service.FileUploadService;

@RestController
@RequestMapping("/comercio")
@RequiredArgsConstructor
@Tag(name = "Comércios", description = "Endpoints para gerenciamento de comércios")
public class ComercioController {

    
    private final ComercioService comercioService;
    private final FileUploadService fileUploadService;

    @Operation(summary = "Cadastro de comércio",description = "Permite que um usuário cadastre um comércio no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comércio cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarComercio(
    @RequestPart("dto") ComercioRequestDTO dto,@RequestPart("file") MultipartFile file) throws IOException {

    if (file.isEmpty()) {
        throw new RegraDeNegocioException("Logo da empresa é obrigatória.");
    }

    String url = fileUploadService.salvarArquivo(file, "usuarios"); 
    dto.setFotoUrl(url); 

    comercioService.cadastrar(dto);
    return ResponseEntity.ok("Comércio cadastrado com sucesso!");
}

    @Operation(summary = "Atualizar e-mail",description = "Permite que o comércio atualize seu endereço de e-mail.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "E-mail atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Comércio não encontrado")
    })
    @PatchMapping("/{id}/email")
    @PreAuthorize("hasRole('COMERCIO')")
    public ResponseEntity<Void> atualizarEmail(@PathVariable Long id, @RequestBody AtualizarEmailDTO dto) {
       comercioService.atualizarEmail(id, dto.getNovoEmail());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar senha",description = "Permite que o  comércio atualize sua senha.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Comércio não encontrado")
    })
    @PatchMapping("/{id}/senha")
    @PreAuthorize("hasRole('COMERCIO')")
    public ResponseEntity<Void> atualizarSenha(@PathVariable Long id, @RequestBody AtualizarSenhaDTO dto) {
        comercioService.atualizarSenha(id, dto.getSenhaAtual(),dto.getNovaSenha());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Visualizar perfil",description = "Retorna os dados do perfil do comércio.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Comércio não encontrado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COMERCIO')")
    public ResponseEntity<ComercioResponseDTO> visualizarPerfil(@PathVariable Long id) {
        var comercio = comercioService.buscarPorId(id)
                          .orElseThrow(() -> new RegraDeNegocioException(" não encontrado."));
        return ResponseEntity.ok(ComercioMapper.toResponse(comercio));
    }


    



}
