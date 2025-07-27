package tcc.conexao_alimentar.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.AtualizarEmailDTO;
import tcc.conexao_alimentar.DTO.AtualizarSenhaDTO;
import tcc.conexao_alimentar.DTO.PessoaFisicaRequestDTO;
import tcc.conexao_alimentar.DTO.PessoaFisicaResponseDTO;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.PessoaFisicaMapper;
import tcc.conexao_alimentar.service.FileUploadService;
import tcc.conexao_alimentar.service.PessoaFisicaService;

@RestController
@RequestMapping("pessoa-fisica")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Pessoas Físicas", description = "Endpoints para gerenciamento de pessoas físicas")
public class PessoaFisicaCadastroController {

    private final PessoaFisicaService pessoaFisicaService;
    private final FileUploadService fileUploadService;

    
    @Operation(summary = "Cadastro de pessoa física",description = "Permite que uma pessoa física se cadastre no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cadastro efetuado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarPessoaFisica(
    @RequestPart("dto") PessoaFisicaRequestDTO dto,@RequestPart("file") MultipartFile file) throws IOException {

    if (file.isEmpty()) {
        throw new RegraDeNegocioException("A foto e perfil é obrigatória.");
    }

    String url = fileUploadService.salvarArquivo(file, "usuarios"); 
    dto.setFotoUrl(url); 

    pessoaFisicaService.cadastrar(dto);
    return ResponseEntity.ok("Usuário cadastrado com sucesso!");
}


    
    @Operation(summary = "Atualizar e-mail",description = "Permite que a pessoa atualize seu endereço de e-mail.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "E-mail atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrado")
    })
    @PatchMapping("/{id}/email")
    @PreAuthorize("hasRole('PESSOA_FISICA')")
    public ResponseEntity<Void> atualizarEmail(@PathVariable Long id, @RequestBody AtualizarEmailDTO dto) {
       pessoaFisicaService.atualizarEmail(id, dto.getNovoEmail());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar senha",description = "Permite que a pessoa atualize sua senha.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrado")
    })
    @PatchMapping("/{id}/senha")
    @PreAuthorize("hasRole('PESOA_FISICA')")
    public ResponseEntity<Void> atualizarSenha(@PathVariable Long id, @RequestBody AtualizarSenhaDTO dto) {
        pessoaFisicaService.atualizarSenha(id, dto.getNovaSenha());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Visualizar perfil",description = "Retorna os dados do perfil da pessoa.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PESSOA_FISICA')")
    public ResponseEntity<PessoaFisicaResponseDTO> visualizarPerfil(@PathVariable Long id) {
        var pf = pessoaFisicaService.buscarPorId(id)
                          .orElseThrow(() -> new RegraDeNegocioException("Pessoa não encontrada."));
        return ResponseEntity.ok(PessoaFisicaMapper.toResponse(pf));
    }




}
