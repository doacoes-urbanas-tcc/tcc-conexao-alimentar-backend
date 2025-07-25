package tcc.conexao_alimentar.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.QrCodeDTO;
import tcc.conexao_alimentar.service.CloudinaryService;
import tcc.conexao_alimentar.service.QrCodeService;
@RequiredArgsConstructor
@RestController
@RequestMapping("/qr-code")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class QrCodeController {

    
    private final QrCodeService qrCodeService;
    private final CloudinaryService cloudinaryService;

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
        String publicId = "qrcodes/doacao-" + doacaoId;

        String url = cloudinaryService.getPublicUrl(publicId);

        return ResponseEntity.ok(url);
    }
}
