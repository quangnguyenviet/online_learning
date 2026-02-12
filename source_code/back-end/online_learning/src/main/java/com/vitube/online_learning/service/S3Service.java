package com.vitube.online_learning.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public void deleteFile(String key, String type);

}