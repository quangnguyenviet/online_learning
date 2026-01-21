package com.vitube.online_learning.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

/**
 * Lớp tiện ích để xử lý các thao tác liên quan đến video.
 */
@Component
public class VideoUtil {
    @Value("${ffmpeg.path}")
    private String ffprobePath;

    /**
     * Lấy thời lượng của video (tính bằng giây).
     *
     * @param videoFile Tệp video cần kiểm tra.
     * @return Thời lượng video tính bằng giây, hoặc 0 nếu không thể lấy thời lượng.
     */
    public long getVideoDuration(File videoFile) {
        long durationInSeconds = 0;

        try {
            // Kiểm tra xem tệp video có tồn tại hay không
            if (!videoFile.exists()) {
                System.err.println("File không tồn tại: " + videoFile.getAbsolutePath());
                return 0;
            }

            // Sử dụng FFprobe để phân tích tệp video
            FFprobe ffprobe = new FFprobe(ffprobePath);
            FFmpegProbeResult probeResult = ffprobe.probe(videoFile.getAbsolutePath());

            // Lấy thông tin định dạng của video
            FFmpegFormat format = probeResult.getFormat();
            if (format != null && !Double.isNaN(format.duration) && format.duration > 0) {
                durationInSeconds = (long) format.duration;
            } else {
                System.err.println("Không thể lấy thời lượng từ: " + videoFile.getAbsolutePath());
            }

        } catch (IOException e) {
            // Xử lý lỗi khi phân tích tệp video
            System.err.println("Lỗi khi phân tích tệp video: " + e.getMessage());
            e.printStackTrace();
        }

        return durationInSeconds;
    }
}