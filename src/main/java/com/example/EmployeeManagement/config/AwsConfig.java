//package com.example.EmployeeManagement.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.sns.SnsClient;
//import software.amazon.awssdk.services.sqs.SqsClient;
//
//@Configuration
//@RequiredArgsConstructor
//public class AwsConfig {
//
//    @Value("${aws.access-key}")
//    private String awsAccessKey;
//
//    @Value("${aws.secret-key}")
//    private String awsSecretKey;
//
//    @Value("${aws.region}")
//    private String awsRegion;
//
//    @Bean
//    public S3Client s3Client() {
//        return S3Client.builder()
//                .region(Region.of(awsRegion))
//                .credentialsProvider(StaticCredentialsProvider.create(
//                        AwsBasicCredentials.create(awsAccessKey, awsSecretKey)))
//                .build();
//    }
//
//    @Bean
//    public SqsClient sqsClient() {
//        return SqsClient.builder()
//                .region(Region.of(awsRegion))
//                .credentialsProvider(StaticCredentialsProvider.create(
//                        AwsBasicCredentials.create(awsAccessKey, awsSecretKey)))
//                .build();
//    }
//
//    @Bean
//    public SnsClient snsClient() {
//        return SnsClient.builder()
//                .region(Region.of(awsRegion))
//                .credentialsProvider(StaticCredentialsProvider.create(
//                        AwsBasicCredentials.create(awsAccessKey, awsSecretKey)))
//                .build();
//    }
//}
