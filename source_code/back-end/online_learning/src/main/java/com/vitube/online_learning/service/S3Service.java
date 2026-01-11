package com.vitube.online_learning.service;

import java.io.IOException;
import java.time.Duration;

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

/**
 * Interface cung cấp các phương thức liên quan đến dịch vụ S3.
 */
public interface S3Service {

    /**
     * Tải lên file vào bucket private.
     *
     * @param file Tệp tin cần tải lên.
     * @param key Khóa định danh của tệp tin.
     * @return URL của tệp tin sau khi tải lên.
     * @throws IOException Lỗi xảy ra khi xử lý tệp.
     */
    public String uploadPrivate(MultipartFile file, String key) throws IOException;

    /**
     * Tải lên file vào bucket public (có thể truy cập trực tiếp qua URL).
     *
     * @param file Tệp tin cần tải lên.
     * @param key Khóa định danh của tệp tin.
     * @return URL của tệp tin sau khi tải lên.
     * @throws IOException Lỗi xảy ra khi xử lý tệp.
     */
    public String uploadPublicFile(MultipartFile file, String key) throws IOException;

    /**
     * Tạo URL có thời hạn để truy cập tệp tin.
     *
     * @param fileName Tên của tệp tin.
     * @return URL có thời hạn để truy cập tệp tin.
     */
    public String generatePresignedUrl(String fileName);

    /**
     * Xóa tệp tin trong bucket private.
     *
     * @param key Khóa định danh của tệp tin.
     */
    public void deleteFile(String key);

}