package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;

/**
 * Lớp triển khai các phương thức liên quan đến dịch vụ S3.
 */
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    @Value("${aws.videoBucketName}")
    private String videoBucketName;

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.imageBucketName}")
    private String imageBucketName;

    private final S3Client s3Client;

    /**
     * Upload file lên bucket riêng tư.
     *
     * @param file Tệp cần upload.
     * @param key Khóa của tệp.
     * @return URL của tệp đã upload.
     * @throws IOException Lỗi xảy ra khi xử lý tệp.
     */
    @Override
    public String uploadPrivate(MultipartFile file, String key) throws IOException {
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(videoBucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putRequest, RequestBody.fromBytes(file.getBytes()));

        return String.format("https://%s.s3.amazonaws.com/%s", videoBucketName, key);
    }

    /**
     * Upload file lên bucket công khai (truy cập trực tiếp qua URL).
     *
     * @param file Tệp cần upload.
     * @param key Khóa của tệp.
     * @return URL của tệp đã upload.
     * @throws IOException Lỗi xảy ra khi xử lý tệp.
     */
    @Override
    public String uploadPublicFile(MultipartFile file, String key) throws IOException {
        System.out.printf("Uploading to bucket: %s, region: %s, key: %s\n", imageBucketName, region, key);

        System.out.println("Content type: " + file.getContentType());

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(imageBucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putRequest, RequestBody.fromBytes(file.getBytes()));

        return String.format("https://%s.s3.%s.amazonaws.com/%s", imageBucketName, region, key);
    }

    /**
     * Tạo URL có thời hạn để truy cập tệp trong bucket riêng tư.
     *
     * @param fileName Tên tệp cần tạo URL.
     * @return URL có thời hạn.
     */
    @Override
    public String generatePresignedUrl(String fileName) {
        S3Presigner presigner = S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();

        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder().bucket(videoBucketName).key(fileName).build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofMinutes(60)) // hết hạn sau 1 giờ
                .build();

        PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
        return presignedRequest.url().toString();
    }

    /**
     * Xóa tệp trong bucket riêng tư.
     *
     * @param key Khóa của tệp cần xóa.
     */
    @Override
    public void deletePrivateFile(String key) {
        s3Client.deleteObject(builder -> builder.bucket(videoBucketName).key(key));
    }

    /**
     * Xóa tệp trong bucket công khai.
     *
     * @param key Khóa của tệp cần xóa.
     */
    @Override
    public void deletePublicFile(String key) {
        s3Client.deleteObject(builder -> builder.bucket(imageBucketName).key(key));
    }
}