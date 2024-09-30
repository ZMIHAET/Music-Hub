package ru.kashigin.musichub.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ImageController {

    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        return serveFileInternal(filename);
    }

    @GetMapping("/{path:.+}/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFileWithPath(@PathVariable String filename, @PathVariable String path) {
        return serveFileInternal(filename);
    }

    private ResponseEntity<Resource> serveFileInternal(String filename) {
        Resource file = new FileSystemResource("uploads/" + filename);
        if (file.exists() && file.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(file);
        } else {
            System.out.println("File not found or not readable: " + filename);
            return ResponseEntity.notFound().build();
        }
    }


}
