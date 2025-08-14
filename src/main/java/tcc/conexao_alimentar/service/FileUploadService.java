package tcc.conexao_alimentar.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final CloudinaryService cloudinaryService;

    public String salvarArquivo(MultipartFile file, String nomeDoModulo) throws IOException {
        return cloudinaryService.uploadFile(file, nomeDoModulo);
    }
}


