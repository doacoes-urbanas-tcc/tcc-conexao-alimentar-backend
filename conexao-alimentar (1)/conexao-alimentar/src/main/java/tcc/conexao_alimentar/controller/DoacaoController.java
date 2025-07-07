package tcc.conexao_alimentar.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import tcc.conexao_alimentar.DTO.DoacaoRequestDTO;
import tcc.conexao_alimentar.DTO.DoacaoResponseDTO;
import tcc.conexao_alimentar.service.DoacaoService;

import java.util.List;

@RestController
@RequestMapping("/doacoes")
@RequiredArgsConstructor
@Tag(name = "Doações", description = "Endpoints para gerenciamento de doações")
public class DoacaoController {

    private final DoacaoService service;
    
    @Operation(summary = "Cadastrar nova doação",description = "Permite que um usuário do tipo COMERCIO, PRODUTOR_RURAL ou PESSOA_FISICA cadastre uma nova doação.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Doação cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Credenciais de autenticação inválidas"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
    })
    @PostMapping
    @PreAuthorize("hasRole('COMERCIO') or hasRole('PRODUTOR_RURAL') or hasRole('PESSOA_FISICA')")
    public ResponseEntity<?> criar(@RequestBody DoacaoRequestDTO dto) {
        service.cadastrar(dto);
        return ResponseEntity.ok("Doação cadastrada com sucesso!");
    }

    @Operation(summary = "Listar todas as doações",description = "Retorna uma lista de todas as doações disponíveis. Somente ONG, VOLUNTÁRIO ou ADMIN podem acessar.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de doações retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @GetMapping
    @PreAuthorize("hasRole('ONG') or hasRole('VOLUNTARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<DoacaoResponseDTO>> listarTodas() {
        List<DoacaoResponseDTO> lista = service.listarTodas();
        return ResponseEntity.ok(lista);
    }

    
    @Operation(summary = "Listar doação por id",description = "Retorna a doação buscada. Somente ONG, VOLUNTÁRIO ou ADMIN podem acessar.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Doação retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ONG') or hasAuthority('ROLE_VOLUNTARIO') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<DoacaoResponseDTO> buscarPorId(@PathVariable Long id) {
    DoacaoResponseDTO dto = service.buscarPorId(id);
    return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Deleta doação por id",description = "Deleta a doação selecionada. Somente COMERCIO, PRODUTOR_RURAL, PESSOA_FISICA e ADMIN podem acessar.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Doação removida com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais de autenticação inválidas"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),

    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMERCIO', 'PRODUTOR_RURAL', 'PESSOA_FISICA')")
    public ResponseEntity<Void> remover(@PathVariable Long id, @RequestParam Long doadorId) {
        service.removerDoacao(id, doadorId);
        return ResponseEntity.noContent().build();
    }


    
}


    

