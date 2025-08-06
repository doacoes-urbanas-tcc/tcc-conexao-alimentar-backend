package tcc.conexao_alimentar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

@Service
@RequiredArgsConstructor
public class QrCodeService {
    
    private final CloudinaryService cloudinaryService;

    public String generateQRCodeAndUpload(Object dto, Long doacaoId) throws WriterException, IOException {
        int width = 300;
        int height = 300;

        ObjectMapper objectMapper = new ObjectMapper();
        String text = objectMapper.writeValueAsString(dto);

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", os);
        InputStream inputStream = new ByteArrayInputStream(os.toByteArray());

        return cloudinaryService.uploadInputStream(inputStream, "qrcodes/doacao-" + doacaoId);
    }

}
