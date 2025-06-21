package tcc.conexao_alimentar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcc.conexao_alimentar.DTO.ProdutorRuralCadastroDTO;
import tcc.conexao_alimentar.service.ProdutorRuralService;

@RestController
@RequestMapping("/produtor-rural")
@RequiredArgsConstructor
public class ProdutorRuralController {

    private final ProdutorRuralService produtorRuralService;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody ProdutorRuralCadastroDTO dto) {
        produtorRuralService.cadastrar(dto);
        return ResponseEntity.ok("Produtor Rural cadastrado com sucesso! Aguarde aprovação.");
    }

}
