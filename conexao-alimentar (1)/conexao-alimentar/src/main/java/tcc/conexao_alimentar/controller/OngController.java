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
import tcc.conexao_alimentar.DTO.OngRequestDTO;
import tcc.conexao_alimentar.DTO.OngResponseDTO;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.OngMapper;
import tcc.conexao_alimentar.service.FileUploadService;
import tcc.conexao_alimentar.service.OngService;

@RestController
@RequestMapping("/ong")
@RequiredArgsConstructor
@Tag(name = "ONG's", description = "Endpoints para gerenciamento de ONG's")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OngController {

    
    private final OngService ongService;
    private final FileUploadService fileUploadService;

    
    @Operation(summary = "Cadastro de ONG",description = "Permite que uma ONG se cadastre no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ONG cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarOng(
    @RequestPart("dto") OngRequestDTO dto,@RequestPart("file") MultipartFile file) throws IOException {

    if (file.isEmpty()) {
        throw new RegraDeNegocioException("Logo da ong é obrigatória.");
    }

    String url = fileUploadService.salvarArquivo(file, "usuarios"); 
    dto.setFotoUrl(url); 

    ongService.cadastrar(dto);
    return ResponseEntity.ok("Comércio cadastrado com sucesso!");
}

    
    
    @Operation(summary = "Atualizar e-mail",description = "Permite que a ONG atualize seu endereço de e-mail.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "E-mail atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "ONG não encontrada")
    })
    @PatchMapping("/{id}/email")
    @PreAuthorize("hasRole('ONG')")
    public ResponseEntity<Void> atualizarEmail(@PathVariable Long id, @RequestBody AtualizarEmailDTO dto) {
       ongService.atualizarEmail(id, dto.getNovoEmail());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar senha",description = "Permite que a ONG atualize sua senha.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "ONG não encontrado")
    })
    @PatchMapping("/{id}/senha")
    @PreAuthorize("hasRole('ONG')")
    public ResponseEntity<Void> atualizarSenha(@PathVariable Long id, @RequestBody AtualizarSenhaDTO dto) {
        ongService.atualizarSenha(id, dto.getNovaSenha());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Visualizar perfil",description = "Retorna os dados do perfil da ONG.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "ONG não encontrada")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ONG')")
    public ResponseEntity<OngResponseDTO> visualizarPerfil(@PathVariable Long id) {
        var ong = ongService.buscarPorId(id)
                          .orElseThrow(() -> new RegraDeNegocioException("ONG não encontrado."));
        return ResponseEntity.ok(OngMapper.toResponse(ong));
    }


}
