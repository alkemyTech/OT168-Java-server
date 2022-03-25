package com.alkemy.ong.domain.aws;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AmazonService {

    private final AmazonGateway amazonGateway;

    public AmazonService(AmazonGateway amazonGateway) {
        this.amazonGateway = amazonGateway;
    }

    public String uploadFile(MultipartFile multipartFile) {
        return amazonGateway.uploadFile(multipartFile);
    }

    public String deleteFile(String fileUrl) {
        return amazonGateway.deleteFileFromS3Bucket(fileUrl);
    }
}
