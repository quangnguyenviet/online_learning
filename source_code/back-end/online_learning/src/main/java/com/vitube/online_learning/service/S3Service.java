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

public interface S3Service {


    public String uploadPrivate(MultipartFile file, String key) throws IOException;
    /**
     * Upload file to public bucket (truy cập trực tiếp qua URL)
     */
    public String uploadPublicFile(MultipartFile file, String key) throws IOException;

    public String generatePresignedUrl(String fileName);

    public void deletePrivateFile(String key);

    public void deletePublicFile(String key);
}
