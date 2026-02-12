package com.vitube.online_learning.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig {

    // Lấy giá trị access key từ file cấu hình
    @Value("${aws.accessKey}")
    private String accessKey;

    // Lấy giá trị secret key từ file cấu hình
    @Value("${aws.secretKey}")
    private String secretKey;

    // Lấy giá trị region từ file cấu hình
    @Value("${aws.region}")
    private String region;

    /**
     * Định nghĩa bean cho S3Client.
     * Phương thức này khởi tạo và cấu hình client AWS S3 sử dụng credentials và region được cung cấp.
     * @return Đối tượng S3Client đã được cấu hình
     */
    @Bean
    public S3Client s3Client() {
        // Tạo credentials AWS từ access key và secret key
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        // Xây dựng và trả về client S3 với region và credentials đã chỉ định
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .build();
    }
}