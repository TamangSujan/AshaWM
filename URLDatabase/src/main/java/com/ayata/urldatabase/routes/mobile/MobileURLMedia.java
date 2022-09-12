package com.ayata.urldatabase.routes.mobile;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/home/sujan/Documents/Java/AshaWM/Assets")
public class MobileURLMedia {
    @GetMapping(value = "/Image/{imageName}", produces = {MediaType.IMAGE_JPEG_VALUE,
                                                          MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<?> getImage(@PathVariable(name = "imageName") String imageName) throws IOException {
        Path path = Paths.get("/home/sujan/Documents/Java/AshaWM/Assets/Image/"+imageName);
        String contentType = "image/"+extractExtension(imageName);
        System.out.println(contentType);
        byte[] buffer = null;
        buffer = Files.readAllBytes(path);
        return ResponseEntity.status(HttpStatus.OK).body(buffer);
    }

    private String extractExtension(String imageName){
        String extension = "";
        for(int i=imageName.length()-1; i>=imageName.length()-3; i--){
            extension += imageName.charAt(i);
        }
        return reverse(extension);
    }

    private String reverse(String data) {
        String result = "";
        for (int i = data.length() - 1; i >= 0; i--) {
            result += data.charAt(i);
        }
        return result;
    }
}
