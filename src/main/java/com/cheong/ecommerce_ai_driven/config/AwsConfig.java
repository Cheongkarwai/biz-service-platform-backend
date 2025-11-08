package com.cheong.ecommerce_ai_driven.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

import java.net.URI;
import java.net.URISyntaxException;

import static software.amazon.awssdk.transfer.s3.SizeConstant.MB;

@Configuration
@EnableConfigurationProperties(S3Properties.class)
public class AwsConfig {

    @Bean
    public S3TransferManager s3Client(S3Properties s3Properties) throws URISyntaxException {
        AwsCredentialsProvider awsCredentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(s3Properties.getAccessKey(), s3Properties.getSecretKey()));
        S3AsyncClient s3AsyncClient = S3AsyncClient.builder()
                .multipartConfiguration(b -> b
                        .thresholdInBytes(16 * MB)
                        .minimumPartSizeInBytes(10 * MB))
                .region(Region.of(s3Properties.getRegion()))
                .endpointOverride(new URI(s3Properties.getEndpoint()))
                .credentialsProvider(awsCredentialsProvider)
                .forcePathStyle(true)
                .build();

        return S3TransferManager.builder()
                .s3Client(s3AsyncClient)
                .build();
    }
}
