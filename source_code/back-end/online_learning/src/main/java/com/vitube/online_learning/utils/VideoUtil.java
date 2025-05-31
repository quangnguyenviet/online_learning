package com.vitube.online_learning.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

@Component
public class VideoUtil {

    public long getVideoDuration(File videoFile) {
        long durationInSeconds = 0;

        try {
            if (!videoFile.exists()) {
                System.err.println("File does not exist: " + videoFile.getAbsolutePath());
                return 0;
            }

            FFprobe ffprobe = new FFprobe("D:\\ffmpeg-master-latest-win64-gpl-shared\\bin\\ffprobe.exe");
            FFmpegProbeResult probeResult = ffprobe.probe(videoFile.getAbsolutePath());

            FFmpegFormat format = probeResult.getFormat();
            if (format != null && !Double.isNaN(format.duration) && format.duration > 0) {
                durationInSeconds = (long) format.duration;
            } else {
                System.err.println("Unable to extract duration from: " + videoFile.getAbsolutePath());
            }

        } catch (IOException e) {
            System.err.println("Error while probing video file: " + e.getMessage());
            e.printStackTrace();
        }

        return durationInSeconds;
    }
}
