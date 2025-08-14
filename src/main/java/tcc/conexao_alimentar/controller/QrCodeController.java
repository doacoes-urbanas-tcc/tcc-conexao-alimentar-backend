package tcc.conexao_alimentar.controller;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.QrCodeDTO;
import tcc.conexao_alimentar.DTO.QrCodeResponseDTO;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.model.ReservaModel;
import tcc.conexao_alimentar.repository.ReservaRepository;
import tcc.conexao_alimentar.service.QrCodeService;
@RequiredArgsConstructor
@RestController
@RequestMapping("/qr-code")
@Tag(name = "QR Code", description = "Endpoints para geração de QR Codes de reserva")
public class QrCodeController {

    
    private final QrCodeService qrCodeService;
    private final ReservaRepository reservaRepository;


    @Operation(summary = "Geração de QR Code", description = "Endpoint para que o QR Code seja gerado ao realizar uma reserva")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "QR Code gerado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "QR Code não encontrado")
    })
    @PostMapping("/generate/{doacaoId}")
    @PreAuthorize("hasRole('ONG')")
    public ResponseEntity<String> gerarQr(@PathVariable Long doacaoId) {
        try {
            QrCodeDTO dto = new QrCodeDTO();
            dto.setDoacaoId(doacaoId);
            dto.setNomeAlimento("Arroz");
            dto.setUnidadeMedida("KG");
            dto.setQuantidade(2.0);
            dto.setDataValidade("2025-12-31");
            dto.setDescricao("Arroz integral");
            dto.setCategoria("Grãos");

            qrCodeService.generateQRCodeAndUpload(dto, doacaoId);
            return ResponseEntity.ok("QR Code gerado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao gerar QR Code.");
        }
    }

    @Operation(summary = "Buscar QR Code com tempo restante")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "QR Code gerado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "QR Code não encontrado")
    })
    @GetMapping("/url/{doacaoId}")
    @PreAuthorize("hasRole('ONG')")
    public ResponseEntity<QrCodeResponseDTO> buscarQrCodeComTempo(@PathVariable Long doacaoId) {
        ReservaModel reserva = reservaRepository.findByDoacaoId(doacaoId)
                .orElseThrow(() -> new RegraDeNegocioException("Reserva não encontrada para esta doação."));

        String url = reserva.getUrlQrCode();
        if (url == null || url.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ZoneId zoneBrasilia = ZoneId.of("America/Sao_Paulo");

        OffsetDateTime dataReserva = reserva.getDataReserva()
                .atZoneSameInstant(zoneBrasilia)
                .toOffsetDateTime();

        OffsetDateTime agora = OffsetDateTime.now(zoneBrasilia);

        long segundosPassados = Duration.between(dataReserva, agora).getSeconds();
        if (segundosPassados < 0) segundosPassados = 0;

        long segundosRestantes = Math.max(0, 7200 - segundosPassados);

        System.out.println("Reserva ID: " + reserva.getId());
        System.out.println("Data reserva: " + dataReserva);
        System.out.println("Agora: " + agora);
        System.out.println("Segundos passados: " + segundosPassados);
        System.out.println("Segundos restantes: " + segundosRestantes);

        QrCodeResponseDTO response = new QrCodeResponseDTO();
        response.setUrl(reserva.getUrlQrCode());
        response.setSegundosRestantes(segundosRestantes);
        response.setSegundosTotais(7200);
        response.setReservaId(reserva.getId());
        response.setDataReserva(dataReserva);
        response.setStatusReserva(reserva.getStatus().name());

        return ResponseEntity.ok(response);
    }

}
