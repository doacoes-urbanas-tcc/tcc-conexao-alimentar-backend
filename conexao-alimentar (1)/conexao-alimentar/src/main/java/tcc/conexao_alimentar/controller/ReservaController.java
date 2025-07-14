package tcc.conexao_alimentar.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.ReservaRequestDTO;
import tcc.conexao_alimentar.DTO.ReservaResponseDTO;
import tcc.conexao_alimentar.service.ReservaService;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    
    private final ReservaService reservaService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ONG')")
    public ResponseEntity<?> criarReserva(@RequestBody @Valid ReservaRequestDTO dto) {
        reservaService.cadastrar(dto);
        return ResponseEntity.ok("Reserva criada com sucesso!");
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_VOLUNTARIO')")
    public ResponseEntity<List<ReservaResponseDTO>> listarTodas() {
        List<ReservaResponseDTO> lista = reservaService.listarTodas();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_VOLUNTARIO')")
    public ResponseEntity<ReservaResponseDTO> buscarPorId(@PathVariable Long id) {
        ReservaResponseDTO dto = reservaService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_ONG')")
    public ResponseEntity<Void> cancelar(@PathVariable Long id, @RequestBody String justificativa) {
        reservaService.cancelarReserva(id, justificativa);
        return ResponseEntity.noContent().build();
    }
}
