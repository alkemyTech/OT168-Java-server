package com.alkemy.ong.domain.cloud;

import org.springframework.web.multipart.MultipartFile;

public interface CloudGateway {

    String uploadFile(MultipartFile multipartFile);
    String deleteFileFromS3Bucket(String fileUrl);
}
