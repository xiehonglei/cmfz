package com.baizhi.util;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

import java.io.File;

public class FileUtil {
    public static int getDuration(File file) {
        try {
            MP3File f = (MP3File) AudioFileIO.read(file);
            MP3AudioHeader audioHeader = (MP3AudioHeader) f.getAudioHeader();
            int length = audioHeader.getTrackLength();
            return length;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
