package ru.kashigin.musichub.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Value("${endpoint}")
    private String endpointName;
    @Value("${access.key}")
    private String accessKey;
    @Value("${secret.key}")
    private String secretKey;
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpointName)
                .credentials(accessKey, secretKey)
                .build();
    }
}
