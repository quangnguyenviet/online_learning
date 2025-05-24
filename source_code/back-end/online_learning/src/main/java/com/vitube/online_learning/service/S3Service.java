package com.vitube.online_learning.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;

@Service
public class S3Service {
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


    // s3 vaanx nhận cấu hình nhờ có spring boot tự cấu hình rồi
    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

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
     * Upload file to public bucket (truy cập trực tiếp qua URL)
     */
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

    public String generatePresignedUrl(String fileName) {
        S3Presigner presigner = S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(videoBucketName)
                .key(fileName)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofMinutes(60)) // hết hạn sau 1 giờ
                .build();

        PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
        return presignedRequest.url().toString();
    }

    public void deletePrivateFile(String key) {
        s3Client.deleteObject(builder -> builder.bucket(videoBucketName).key(key));
    }
    public void deletePublicFile(String key) {
        s3Client.deleteObject(builder -> builder.bucket(imageBucketName).key(key));
    }

}
