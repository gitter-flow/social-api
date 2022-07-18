package com.gitter.socialapi.kernel.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("bucketClient")
public class BucketClient {
    private final String minioEndpoint;
    private final String minioUser;
    private final String minioPassword;

    public BucketClient(
            @Value("${minio.url}") String minioEndpoint,
            @Value("${minio.access.key}") String minioUser, 
            @Value("${minio.access.secret}") String minioPassword) {
        this.minioEndpoint = minioEndpoint;
        this.minioUser = minioUser;
        this.minioPassword = minioPassword;
    }
    
    @Bean
    public MinioClient get() {
        return MinioClient.builder()
                        .endpoint(minioEndpoint)
                        .credentials(minioUser, minioPassword)
                        .build();
    }
}
