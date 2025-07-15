package tcc.conexao_alimentar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class QrCodeService {
    public void generateQRCode(Object dto, Long doacaoId) throws WriterException, IOException {
        int width = 300;
        int height = 300;

        ObjectMapper objectMapper = new ObjectMapper();
        String text = objectMapper.writeValueAsString(dto);

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        String uploadDir = "qrcodes/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Path path = FileSystems.getDefault().getPath(uploadDir + "doacao-" + doacaoId + ".png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

}
