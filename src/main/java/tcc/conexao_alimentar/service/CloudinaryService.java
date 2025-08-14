package tcc.conexao_alimentar.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file, String folderName) throws IOException {
        Map<String, Object> uploadParams = new HashMap<>();
        uploadParams.put("folder", folderName);
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadParams);
        return uploadResult.get("secure_url").toString();
    }
    public String uploadInputStream(InputStream inputStream, String fileName) throws IOException {
    byte[] bytes = inputStream.readAllBytes();
    String base64Image = Base64.getEncoder().encodeToString(bytes);
    String dataUri = "data:image/png;base64," + base64Image;

    Map<String, Object> uploadParams = new HashMap<>();
    uploadParams.put("public_id", fileName);
    uploadParams.put("folder", "qrcodes");
    uploadParams.put("resource_type", "image");

    Map uploadResult = cloudinary.uploader().upload(dataUri, uploadParams);
    return uploadResult.get("secure_url").toString();
   }
   public String getPublicUrl(String publicId) {
    return "https://res.cloudinary.com/du9zmknbe/image/upload/" + publicId + ".png";
}


}
