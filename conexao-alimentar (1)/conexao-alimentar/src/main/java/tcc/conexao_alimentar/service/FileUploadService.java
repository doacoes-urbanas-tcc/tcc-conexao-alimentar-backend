package tcc.conexao_alimentar.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    
    private final String rootDir = "static/imagens/";

    public String salvarArquivo(MultipartFile file, String subpasta) throws IOException {
        String pasta = rootDir + subpasta + "/";

        File dir = new File(pasta);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File uploadedFile = new File(pasta + fileName);

        try (FileOutputStream fos = new FileOutputStream(uploadedFile)) {
            fos.write(file.getBytes());
        }

        return "/imagens/" + subpasta + "/" + fileName;
    }
}


