package com.alkemy.ong.domain.cloud;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface CloudGateway {

    String uploadFile(MultipartFile multipartFile);

    void uploadFileToS3bucket(String fileName, File file);

    String deleteFileFromS3Bucket(String fileUrl);
}
