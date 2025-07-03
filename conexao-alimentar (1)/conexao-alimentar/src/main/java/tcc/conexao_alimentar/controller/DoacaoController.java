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
    @PreAuthorize("hasAuthority('ROLE_COMERCIO') or hasAuthority('ROLE_PRODUTOR_RURAL') or hasAuthority('ROLE_PESSOA_FISICA')")
    public ResponseEntity<?> criar(@RequestBody DoacaoRequestDTO dto) {
        service.cadastrar(dto);
        return ResponseEntity.ok("Doação cadastrada com sucesso!");
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ONG') or hasAuthority('ROLE_VOLUNTARIO') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<DoacaoResponseDTO>> listarTodas() {
        List<DoacaoResponseDTO> lista = service.listarTodas();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ONG') or hasAuthority('ROLE_VOLUNTARIO') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<DoacaoResponseDTO> buscarPorId(@PathVariable Long id) {
    DoacaoResponseDTO dto = service.buscarPorId(id);
    return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMERCIO', 'PRODUTOR_RURAL', 'PESSOA_FISICA')")
    public ResponseEntity<Void> remover(@PathVariable Long id, @RequestParam Long doadorId) {
        service.removerDoacao(id, doadorId);
        return ResponseEntity.noContent().build();
    }


    
}


    

