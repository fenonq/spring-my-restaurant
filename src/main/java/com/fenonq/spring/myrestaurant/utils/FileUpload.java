package com.fenonq.spring.myrestaurant.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public abstract class FileUpload {

    public static void saveFile(String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get("src/main/resources/static/img");

         if (!(new File("src/main/resources/static/img", fileName).exists())) {
             if (!Files.exists(uploadPath)) {
                 Files.createDirectories(uploadPath);
             }

             try (InputStream inputStream = multipartFile.getInputStream()) {
                 Path filePath = uploadPath.resolve(fileName);
                 Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
             } catch (IOException ioe) {
                 throw new IOException("Could not save image file: " + fileName, ioe);
             }
         }
    }
}
