package com.ijse.media_service.controller;

import com.ijse.media_service.dto.MediaUploadResponseDTO;
import com.ijse.media_service.service.GcsStorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

    private final GcsStorageService gcsStorageService;

    public MediaController(GcsStorageService gcsStorageService) {
        this.gcsStorageService = gcsStorageService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MediaUploadResponseDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        MediaUploadResponseDTO response = gcsStorageService.uploadFile(file);
        return ResponseEntity.ok(response);
    }
}
