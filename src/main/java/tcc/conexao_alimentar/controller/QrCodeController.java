package tcc.conexao_alimentar.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

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


    @Operation(summary = "Geração de QR Code ",description = "Endpoint para que o QR Code seja gerado ao realizar uma reserva")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "QR Code gerado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
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

    @Operation(summary = "Geração de QR Code com tempo de expiração",description = "Endpoint para geração de QR Code com tempo de expiração de 2 horas apartir do momento em que a reserva é efetuada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "404", description = "Produtor rural não encontrado")
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

    OffsetDateTime dataReserva = reserva.getDataReserva(); 
    long segundosTotais = 7200;
    long segundosPassados = Duration.between(dataReserva, OffsetDateTime.now()).getSeconds();
    long segundosRestantes = Math.max(0, segundosTotais - segundosPassados);

    QrCodeResponseDTO response = new QrCodeResponseDTO();
    response.setUrl(url);
    response.setSegundosRestantes(segundosRestantes);
    response.setReservaId(reserva.getId());
    response.setDataReserva(dataReserva);
    response.setStatusReserva(reserva.getStatus().name()); 

    return ResponseEntity.ok(response);
   }

}
