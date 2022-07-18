package com.gitter.socialapi.modules.user.infrastructure;

import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageResizeService {
    private static final double DEFAULT_QUALITY = 0.7;

    public static InputStream resize(byte[] file, int width, int height, String format) {
        InputStream inputStream = new ByteArrayInputStream(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Thumbnails.of(inputStream)
                    .size(width, height)
                    .outputFormat(format.toUpperCase())
                    .outputQuality(DEFAULT_QUALITY)
                    .toOutputStream(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
