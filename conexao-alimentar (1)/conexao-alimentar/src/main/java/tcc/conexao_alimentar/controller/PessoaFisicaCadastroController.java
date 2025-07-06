package tcc.conexao_alimentar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.PessoaFisicaRequestDTO;
import tcc.conexao_alimentar.service.PessoaFisicaService;

@RestController
@RequestMapping("pessoa-fisica")
@RequiredArgsConstructor
@Tag(name = "Pessoas Físicas", description = "Endpoints para gerenciamento de pessoas físicas")
public class PessoaFisicaCadastroController {

    private final PessoaFisicaService pessoaFisicaService;

    
    @Operation(summary = "Cadastro de pessoa física",description = "Permite que uma pessoa física se cadastre no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cadastro efetuado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")

    })
    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody PessoaFisicaRequestDTO dto) {
        pessoaFisicaService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
