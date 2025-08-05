package tcc.conexao_alimentar.controller;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import tcc.conexao_alimentar.DTO.AtualizarEmailDTO;
import tcc.conexao_alimentar.DTO.AtualizarSenhaDTO;
import tcc.conexao_alimentar.DTO.VeiculoRequestDTO;
import tcc.conexao_alimentar.DTO.VoluntarioRequestDTO;
import tcc.conexao_alimentar.DTO.VoluntarioResponseDTO;
import tcc.conexao_alimentar.DTO.VoluntarioTiRequestDTO;
import tcc.conexao_alimentar.DTO.VoluntarioTiResponseDTO;
import tcc.conexao_alimentar.model.VoluntarioModel;
import tcc.conexao_alimentar.service.FileUploadService;
import tcc.conexao_alimentar.service.VeiculoService;
import tcc.conexao_alimentar.service.VoluntarioService;

@RestController
@RequestMapping("/voluntario")
@RequiredArgsConstructor
@Tag(name = "Voluntários", description = "Endpoints para gerenciamento de voluntários")
public class VoluntarioController {

    private final VoluntarioService voluntarioService;
    private final VeiculoService veiculoService;
    private final FileUploadService fileUploadService;

    
    @Operation(summary = "Cadastro de voluntário",description = "Permite que um usuário se cadastre como voluntário no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Voluntário cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @PostMapping(value = "/cadastrar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> cadastrarVoluntario(
    @RequestPart("dto") @Valid VoluntarioRequestDTO dto,
    @RequestPart("comprovante") MultipartFile comprovante,
    @RequestPart("file") MultipartFile foto) throws IOException {

    VoluntarioModel voluntario = voluntarioService.cadastrar(dto, comprovante, foto);

    Map<String, Object> response = new HashMap<>();
    response.put("id", voluntario.getId());

    return ResponseEntity.ok(response);
   }

    
    @Operation(summary = "Cadastro de veículo ",description = "Permite que um usuário que se cadastra om voluntário e escolhe ser transportador cadastre seu veículo no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @PostMapping(value = "/{voluntarioId}/veiculo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> cadastrarVeiculo(@PathVariable Long voluntarioId,@RequestPart("dados") @Valid VeiculoRequestDTO dto,@RequestPart("cnh") MultipartFile cnh) throws IOException {
    dto.setVoluntarioId(voluntarioId);
    veiculoService.cadastrarVeiculo(dto, cnh);
    return ResponseEntity.ok("Veículo cadastrado com sucesso!");
   }




    @Operation(summary = "Cadastrar perfil TI", description = "Permite que o voluntário do setor TI cadastre suas informações técnicas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil TI cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @PostMapping(value = "/{voluntarioId}/perfil-ti", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> cadastrarPerfilTI(@PathVariable Long voluntarioId,@RequestPart("dados") @Valid VoluntarioTiRequestDTO dto,@RequestPart("certificacoesFile") MultipartFile certificacoesFile) throws IOException {
    String url = fileUploadService.salvarArquivo(certificacoesFile, "certificacoes_ti");
    dto.setCertificacoes(url);

    voluntarioService.cadastrarPerfilTI(voluntarioId, dto);
    return ResponseEntity.ok().build();
   }
    
    @Operation(summary = "Visualizar perfil TI", description = "Retorna o perfil TI do voluntário")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil TI retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    @GetMapping("/{voluntarioId}/perfil-ti")
    @PreAuthorize("hasRole('VOLUNTARIO')")
    public ResponseEntity<VoluntarioTiResponseDTO> visualizarPerfilTI(@PathVariable Long voluntarioId) {
        var perfil = voluntarioService.visualizarPerfilTI(voluntarioId);
        return ResponseEntity.ok(perfil);
    }

    @Operation(summary = "Atualizar perfil TI", description = "Permite que o voluntário de TI atualize suas informações técnicas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil TI atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    @PatchMapping("/{voluntarioId}/perfil-ti")
    @PreAuthorize("hasRole('VOLUNTARIO')")
    public ResponseEntity<Map<String, Long>> atualizarPerfilTI(@PathVariable Long voluntarioId, @RequestBody @Valid VoluntarioTiRequestDTO dto) {
        voluntarioService.atualizarPerfilTI(voluntarioId, dto);
        return ResponseEntity.ok(Map.of("id", voluntarioId));
    }

    @Operation(summary = "Listar voluntário por id",description = "Busca o voluntário por id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Voluntário retornado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais de autenticação inválidas"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VoluntarioResponseDTO> buscarPorId(@PathVariable Long id) {
        VoluntarioResponseDTO dto = voluntarioService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Endpoint para um voluntário atualizar o email",description = "Endpoint para um voluntário atualizar o email")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Email atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })

    @PatchMapping("/{id}/email")
    @PreAuthorize("hasRole('VOLUNTARIO')")
    public ResponseEntity<Void> atualizarEmail(@PathVariable Long id, @RequestBody AtualizarEmailDTO dto) {
       voluntarioService.atualizarEmail(id, dto.getNovoEmail());
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Endpoint para um voluntário atualizar a senha",description = "Endpoint para um voluntário atualizar a senha")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })

    @PatchMapping("/{id}/senha")
    @PreAuthorize("hasRole('VOLUNTARIO')")
    public ResponseEntity<Void> atualizarSenha(@PathVariable Long id, @RequestBody AtualizarSenhaDTO dto) {
    voluntarioService.atualizarSenha(id, dto.getSenhaAtual(),dto.getNovaSenha());
        return ResponseEntity.ok().build();
    }







    



}
