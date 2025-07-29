package tcc.conexao_alimentar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.QrCodeDTO;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.model.ReservaModel;
import tcc.conexao_alimentar.repository.ReservaRepository;
import tcc.conexao_alimentar.service.CloudinaryService;
import tcc.conexao_alimentar.service.QrCodeService;
@RequiredArgsConstructor
@RestController
@RequestMapping("/qr-code")
public class QrCodeController {

    
    private final QrCodeService qrCodeService;
    private final CloudinaryService cloudinaryService;
    private final ReservaRepository reservaRepository;

    @PostMapping("/generate/{doacaoId}")
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
    @GetMapping("/url/{doacaoId}")
    public ResponseEntity<String> buscarUrlQrCode(@PathVariable Long doacaoId) {
         ReservaModel reserva = reservaRepository.findByDoacaoId(doacaoId)
        .orElseThrow(() -> new RegraDeNegocioException("Reserva não encontrada para esta doação."));

    String url = reserva.getUrlQrCode();

    if (url == null || url.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("QR Code ainda não disponível.");
    }

    return ResponseEntity.ok(url);
    }
}
