package tcc.conexao_alimentar.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcc.conexao_alimentar.DTO.ComercioRequestDTO;
import tcc.conexao_alimentar.service.ComercioService;

@RestController
@RequestMapping("/comercio")
@RequiredArgsConstructor
public class ComercioController {

    
    private final ComercioService comercioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody ComercioRequestDTO dto) {
        comercioService.cadastrar(dto);
        return ResponseEntity.ok("Comércio cadastrado com sucesso! Aguarde aprovação.");
    }



}
