package tcc.conexao_alimentar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcc.conexao_alimentar.DTO.VoluntarioCadastroDTO;
import tcc.conexao_alimentar.service.VoluntarioService;

@RestController
@RequestMapping("/voluntario")
@RequiredArgsConstructor
public class VoluntarioController {

    private final VoluntarioService voluntarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody VoluntarioCadastroDTO dto) {
        voluntarioService.cadastrar(dto);
        return ResponseEntity.ok("Voluntário cadastrado com sucesso! Aguarde aprovação.");
    }

}
