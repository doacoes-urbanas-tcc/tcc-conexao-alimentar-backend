package tcc.conexao_alimentar.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import tcc.conexao_alimentar.DTO.OngRequestDTO;
import tcc.conexao_alimentar.service.OngService;

@RestController
@RequestMapping("/ong")
@RequiredArgsConstructor
@Tag(name = "ONG's", description = "Endpoints para gerenciamento de ONG's")
public class OngController {

    
    private final OngService ongService;

    
    @Operation(summary = "Cadastro de ONG",description = "Permite que uma ONG se cadastre no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ONG cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody @Valid OngRequestDTO dto) {
        ongService.cadastrar(dto);
        return ResponseEntity.ok("ONG cadastrada com sucesso! Aguarde aprovação.");
    }

}
