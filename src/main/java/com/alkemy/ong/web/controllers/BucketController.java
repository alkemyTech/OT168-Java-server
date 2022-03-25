package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.aws.AwsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage/")
public class BucketController {

    private final AwsService awsService;

    public BucketController(AwsService awsService) {
        this.awsService = awsService;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.awsService.uploadFile(file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.awsService.deleteFileFromS3Bucket(fileUrl);
    }
}