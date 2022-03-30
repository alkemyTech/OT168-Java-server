package com.alkemy.ong.domain.cloud;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudService {

    private final CloudGateway cloudGateway;

    public CloudService(CloudGateway cloudGateway) {
        this.cloudGateway = cloudGateway;
    }

    public String uploadFile(MultipartFile multipartFile) {
        return cloudGateway.uploadFile(multipartFile);
    }

    public String deleteFile(String fileUrl) {
        return cloudGateway.deleteFileFromS3Bucket(fileUrl);
    }
}


