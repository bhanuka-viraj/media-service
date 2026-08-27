package com.ijse.media_service.dto;

public record MediaUploadResponseDTO(
        String fileName,
        String fileUrl,
        String contentType,
        long size,
        String status
) {
}
