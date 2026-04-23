package com.vitube.online_learning.utils;

public class FileUtil {
    public static String generateFileName(String originalFilename) {
        String fileExtension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex >= 0) {
            fileExtension = originalFilename.substring(dotIndex);
        }
        String uniqueFileName = System.currentTimeMillis() + "_" + java.util.UUID.randomUUID() + fileExtension;
        return uniqueFileName;
    }
    public static String getKeyFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
