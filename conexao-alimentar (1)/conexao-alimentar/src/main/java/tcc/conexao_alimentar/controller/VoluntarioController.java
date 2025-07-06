package tcc.conexao_alimentar.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import tcc.conexao_alimentar.DTO.VeiculoRequestDTO;
import tcc.conexao_alimentar.DTO.VoluntarioRequestDTO;
import tcc.conexao_alimentar.DTO.VoluntarioResponseDTO;
import tcc.conexao_alimentar.service.VeiculoService;
import tcc.conexao_alimentar.service.VoluntarioService;

@RestController
@RequestMapping("/voluntario")
@RequiredArgsConstructor
@Tag(name = "Voluntários", description = "Endpoints para gerenciamento de voluntários")
public class VoluntarioController {

    private final VoluntarioService voluntarioService;
    private final VeiculoService veiculoService;

    
    @Operation(summary = "Cadastro de voluntário",description = "Permite que um usuário se cadastre como voluntário no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Voluntário cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

        })
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody VoluntarioRequestDTO dto) {
        voluntarioService.cadastrar(dto);
        return ResponseEntity.ok("Voluntário cadastrado com sucesso! Aguarde aprovação.");
    }

    
    @Operation(summary = "Cadastro de veículo ",description = "Permite que um usuário que se cadastra om voluntário e escolhe ser transportador cadastre seu veículo no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @PostMapping("/{voluntarioId}/veiculo")
    public ResponseEntity<String> cadastrarVeiculo(@PathVariable Long voluntarioId,@RequestBody VeiculoRequestDTO dto) {
        dto.setVoluntarioId(voluntarioId);  
        veiculoService.cadastrarVeiculo(dto);
        return ResponseEntity.ok("Veículo cadastrado com sucesso para voluntário!");
    }

    
    @Operation(summary = "Listar todos os voluntários",description = "Lista todos os voluntários cadastrados no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de voluntários retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais de autenticação inválidas"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping
    public ResponseEntity<List<VoluntarioResponseDTO>> listarTodos() {
        List<VoluntarioResponseDTO> lista = voluntarioService.listarTodos();
        return ResponseEntity.ok(lista);
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

    



}
