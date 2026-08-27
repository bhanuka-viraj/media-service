package com.ijse.media_service.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.ijse.media_service.dto.MediaUploadResponseDTO;
import com.ijse.media_service.exception.InvalidFileException;
import com.ijse.media_service.exception.MediaUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Service
public class GcsStorageService {

    private final String bucketName;
    private final String projectId;
    private final Storage storage;

    public GcsStorageService(
            @Value("${gcp.storage.bucket-name:${GCP_BUCKET_NAME:educloud-media-bucket-535026634701}}") String bucketName,
            @Value("${gcp.project-id:${GCP_PROJECT_ID:enterprise-cloud-module-503705}}") String projectId
    ) {
        String resolvedBucket = System.getProperty("GCP_BUCKET_NAME");
        if (resolvedBucket == null || resolvedBucket.isBlank()) {
            resolvedBucket = System.getenv("GCP_BUCKET_NAME");
        }
        if (resolvedBucket == null || resolvedBucket.isBlank()) {
            resolvedBucket = bucketName;
        }

        String resolvedProject = System.getProperty("GCP_PROJECT_ID");
        if (resolvedProject == null || resolvedProject.isBlank()) {
            resolvedProject = System.getenv("GCP_PROJECT_ID");
        }
        if (resolvedProject == null || resolvedProject.isBlank()) {
            resolvedProject = projectId;
        }

        this.bucketName = resolvedBucket;
        this.projectId = resolvedProject;
        System.out.println("[GCS STORAGE] Initializing GCS for bucket: " + this.bucketName + " in project: " + this.projectId);
        this.storage = StorageOptions.newBuilder().setProjectId(this.projectId).build().getService();
    }

    public MediaUploadResponseDTO uploadFile(MultipartFile file) {
        validateFile(file);

        String originalFilename = StringUtils.cleanPath(Objects.requireNonNullElse(file.getOriginalFilename(), "file"));
        String uniqueFileName = UUID.randomUUID() + "-" + originalFilename;
        String contentType = Objects.requireNonNullElse(file.getContentType(), "application/octet-stream");

        try (InputStream inputStream = file.getInputStream()) {
            BlobId blobId = BlobId.of(bucketName, uniqueFileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(contentType)
                    .build();

            storage.createFrom(blobInfo, inputStream);

            String fileUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, uniqueFileName);

            return new MediaUploadResponseDTO(
                    uniqueFileName,
                    fileUrl,
                    contentType,
                    file.getSize(),
                    "SUCCESS"
            );
        } catch (Exception ex) {
            throw new MediaUploadException("Failed to upload file to storage: " + ex.getMessage(), ex);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidFileException("File must not be null or empty");
        }
    }
}
