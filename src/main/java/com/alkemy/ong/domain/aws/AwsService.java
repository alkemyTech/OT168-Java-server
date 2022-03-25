package com.alkemy.ong.domain.aws;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AwsService {

    private final AmazonClient amazonClient;

    public AwsService(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    public String uploadFile(MultipartFile multipartFile) {
        return amazonClient.uploadFile(multipartFile);
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        return amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
}
