package com.ijse.media_service.controller;

import com.ijse.media_service.service.GcsStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

    @Autowired
    private GcsStorageService gcsStorageService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            String fileUrl = gcsStorageService.uploadFile(file);
            response.put("fileName", file.getOriginalFilename());
            response.put("fileUrl", fileUrl);
            response.put("status", "SUCCESS");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "FAILED");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
