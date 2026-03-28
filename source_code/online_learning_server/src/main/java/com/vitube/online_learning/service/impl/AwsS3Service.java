package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.service.S3Service;
import com.vitube.online_learning.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

@Service
@ConditionalOnProperty(name = "storage.provider", havingValue = "aws", matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class AwsS3Service implements S3Service {

    @Value("${storage.videoBucketName}")
    private String videoBucketName;

    @Value("${storage.imageBucketName}")
    private String imageBucketName;

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Override
    public String uploadPrivate(File file, String key) throws IOException {
        String contentType = Files.probeContentType(file.toPath());

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(videoBucketName)
                .key(key)
                .contentType(contentType)
                .build();

        s3Client.putObject(request, RequestBody.fromFile(file));

        return s3Client.utilities()
                .getUrl(b -> b.bucket(videoBucketName).key(key))
                .toString();
    }

    @Override
    public String uploadPublicFile(MultipartFile file, String key) throws IOException {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(imageBucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));

        return s3Client.utilities()
                .getUrl(b -> b.bucket(imageBucketName).key(key))
                .toString();
    }

    @Override
    public String generatePresignedUrl(String videoUrl) {
        String key = FileUtil.getKeyFromUrl(videoUrl);

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(videoBucketName)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest =
                GetObjectPresignRequest.builder()
                        .getObjectRequest(getObjectRequest)
                        .signatureDuration(Duration.ofMinutes(60))
                        .build();

        return s3Presigner.presignGetObject(presignRequest)
                .url()
                .toString();
    }

    @Override
    public void deleteFile(String key, String type) {
        String bucket = type.equals("IMAGE") ? imageBucketName : videoBucketName;
        s3Client.deleteObject(b -> b.bucket(bucket).key(key));
    }
}