# Media Storage Microservice (Google Cloud Storage)

## Student Information
- **Student Name:** J P Bhanuka Viraj Madhuranga
- **Student Number:** 241711105
- **GCP Project ID:** enterprise-cloud-module-503705

---

## Project Description
Media management microservice providing file upload and asset retrieval integrated with Google Cloud Storage (GCS) buckets.

---

## Cloud Storage Configuration
- **Storage Provider:** Google Cloud Storage (GCS)
- **Bucket:** enterprise-cloud-media-bucket
- **SDK:** com.google.cloud:google-cloud-storage

---

## API Endpoints
- POST /api/v1/media/upload — Multipart file upload (returns public GCS URL)

---

## Technology Stack
- Java 25
- Spring Boot 4.1.1
- Google Cloud Storage Client SDK
- PM2 Process Manager

---

## Running Locally
- Default Port: 8083
`ash
./mvnw clean package
java -jar target/media-service-0.0.1-SNAPSHOT.jar
`
