package group.aist.cinemaapp.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Component
public class ImageSaveUtil {

    @Value("${uploads.file.path}")
    private String basePath;

    public String saveImage(String name, MultipartFile multipartFile, String folderName)
            throws IOException {
        Path uploadPath = Paths.get(basePath + folderName);
        String fileName = multipartFile.getOriginalFilename();
        Path filePath = Path.of(name ,"-", String.valueOf(LocalDateTime.now()));
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileCode = RandomStringUtils.randomAlphanumeric(8);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            filePath = uploadPath.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }
        return filePath.toString();
    }
}
