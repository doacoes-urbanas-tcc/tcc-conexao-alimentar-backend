package tcc.conexao_alimentar.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import tcc.conexao_alimentar.DTO.ComercioRequestDTO;
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



}
