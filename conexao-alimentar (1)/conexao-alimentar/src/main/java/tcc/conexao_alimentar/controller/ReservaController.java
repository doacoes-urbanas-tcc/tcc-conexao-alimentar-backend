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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.ReservaRequestDTO;
import tcc.conexao_alimentar.DTO.ReservaResponseDTO;
import tcc.conexao_alimentar.service.ReservaService;
@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
@Tag(name = "Reservas", description = "Endpoints referentes a reservas")
public class ReservaController {

    private final ReservaService reservaService;

    
    @Operation(summary = "Endpoint para fazer uma reserva",description = "Endpoint para uma ONG reservar uma doação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reserva efetuada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ONG')")
    public ResponseEntity<?> criarReserva(@RequestBody @Valid ReservaRequestDTO dto) {
        reservaService.cadastrar(dto);
        return ResponseEntity.ok("Reserva criada com sucesso!");
    }
    @Operation(summary = "Endpoint para fazer listar todas as reservas",description = "Endpoint para uma ONG  ou um administrador listarem as reservas cadastrardas no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de reservas retornada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })

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
    @Operation(summary = "Endpoint para  cancelar uma reserva",description = "Endpoint para uma ONG ou um administrador cancelarem a reserva de uma doação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reserva cancelada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_ONG')")
    public ResponseEntity<Void> cancelar(@PathVariable Long id, @RequestBody String justificativa) {
        reservaService.cancelarReserva(id, justificativa);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Endpoint para a ONG listar as reservas que já efetuou no sistema",description = "Endpoint para uma ONG listar as reservas que tem cadastradas no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de reservas retornada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })

    @GetMapping("/minhas-reservas")
    @PreAuthorize("hasRole('ONG')")
    public ResponseEntity<List<ReservaResponseDTO>> listarMinhasReservas() {
        List<ReservaResponseDTO> minhasReservas = reservaService.listarReservasDoReceptor();
        return ResponseEntity.ok(minhasReservas);
    }

    @Operation(summary = "Endpoint para listar as avaliações que estão pendentes de serem efetuadas",description = "Endpoint para  armazenar as avaliações pendentes de serem efetuadas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reserva efetuada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    @GetMapping("/avaliacoes-pendentes")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ReservaResponseDTO>> listarReservasParaAvaliar() {
        List<ReservaResponseDTO> pendentes = reservaService.listarReservasParaAvaliar();
        return ResponseEntity.ok(pendentes);
    }
    @Operation(summary = "Endpoint para um usuário avaliar outro",description = "Endpoint para listar as avaliações que um usuário já recebeu")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reserva efetuada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    @GetMapping("/avaliar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReservaResponseDTO> proximaReservaParaAvaliar() {
    ReservaResponseDTO dto = reservaService.buscarProximaReservaParaAvaliar();

    if (dto == null) {
        return ResponseEntity.noContent().build(); 
    }

    return ResponseEntity.ok(dto);
}

}
