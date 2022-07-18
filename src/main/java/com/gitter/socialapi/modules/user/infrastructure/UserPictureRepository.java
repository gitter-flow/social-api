package com.gitter.socialapi.modules.user.infrastructure;

import com.gitter.socialapi.kernel.minio.BucketClient;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
@Slf4j
public class UserPictureRepository {
    
    private final BucketClient bucketClient;
    
    private final String pictureBucketName;
    
    
    @Autowired
    public UserPictureRepository(BucketClient bucketClient, @Value("${minio.bucket.picture.name}") String pictureBucketName) {
        this.bucketClient = bucketClient;
        this.pictureBucketName = pictureBucketName;
    }
    
    public void uploadPicture(byte[] image, String target, String format) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image));
        InputStream imageResized = ImageResizeService.resize(image, bufferedImage.getWidth(), bufferedImage.getHeight(), format);
        ByteArrayInputStream bais = new ByteArrayInputStream(IOUtils.toByteArray(imageResized));
        try {
            boolean found = bucketClient.get().bucketExists(BucketExistsArgs.builder().bucket(pictureBucketName).build());
            if (!found) {
                throw new RuntimeException(String.format("Bucket %s not found", pictureBucketName));
            }
            bucketClient.get().putObject(
                    PutObjectArgs.builder()
                            .bucket(pictureBucketName)
                            .object(target)
                            .stream(bais, bais.available(), -1)
                            .contentType("application/octet-stream")
                            .build()
            );
            log.info(String.format("Picture has been uploaded to %s", target));
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    public InputStream getPicture(String target) {
        try {
            return bucketClient.get().getObject(
                    GetObjectArgs.builder()
                            .bucket(pictureBucketName)
                            .object(target)
                            .offset(1024L)
                            .length(4096L)
                            .build());
        } catch (
                InvalidKeyException |
                MinioException |
                NoSuchAlgorithmException |
                IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
