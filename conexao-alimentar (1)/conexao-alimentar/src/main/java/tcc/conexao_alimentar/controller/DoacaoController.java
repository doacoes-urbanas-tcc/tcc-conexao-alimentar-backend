package tcc.conexao_alimentar.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tcc.conexao_alimentar.DTO.DoacaoRequestDTO;
import tcc.conexao_alimentar.DTO.DoacaoResponseDTO;
import tcc.conexao_alimentar.service.DoacaoService;

import java.util.List;

@RestController
@RequestMapping("/doacoes")
@RequiredArgsConstructor
public class DoacaoController {

    private final DoacaoService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('COMERCIO', 'PESSOA_FISICA', 'PRODUTOR_RURAL')")
    public ResponseEntity<?> criar(@RequestBody DoacaoRequestDTO dto) {
        service.cadastrar(dto);
        return ResponseEntity.ok("Doação cadastrada com sucesso!");
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ONG', 'VOLUNTARIO', 'ADMIN')")
    public ResponseEntity<List<DoacaoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

}
