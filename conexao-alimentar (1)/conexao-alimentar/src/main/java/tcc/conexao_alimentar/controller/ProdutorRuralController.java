package tcc.conexao_alimentar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import tcc.conexao_alimentar.DTO.ProdutorRuralRequestDTO;
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

}
