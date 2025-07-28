package com.example.Premind_BE.domain.portfolio.service;


import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.example.Premind_BE.infra.s3.S3Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileService {
    private final AmazonS3 amazonS3;
    private final S3Properties s3Properties;

    public String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public String createFileName(Long memberId, String imageKey) {
        return memberId
                + "/"
                + imageKey;
    }

    public GeneratePresignedUrlRequest createGeneratePresignedUrlRequest(String bucket, String fileName) {

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileName)
                .withKey(fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(getPresignedUrlExpiration());

        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString()
        );

        return generatePresignedUrlRequest;
    }

    public Date getPresignedUrlExpiration() {
        Date expiration = new Date();
        long expTime = expiration.getTime();
        expTime += TimeUnit.MINUTES.toMillis(5);
        expiration.setTime(expTime);

        return expiration;
    }

    public void deleteFile(String fileUrl) {
        try {
            String bucket = s3Properties.getBucket();
            String fileKey = extractKeyFromUrl(fileUrl);
            amazonS3.deleteObject(bucket, fileKey);
            log.info("Deleted file from S3: {}", fileKey);
        } catch (Exception e) {
            log.error("Failed to delete file from S3: {}", fileUrl, e);
        }
    }

    private String extractKeyFromUrl(String fileUrl) {
        // presigned URL의 실제 파일 경로만 추출
        URI uri = URI.create(fileUrl);
        return uri.getPath().substring(1); // e.g., "1/f829333c-2e4f-4a38-8eb9-2ee10ae2117e"
    }
}


