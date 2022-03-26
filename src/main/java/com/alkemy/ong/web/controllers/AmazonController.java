package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.aws.AmazonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage/")
public class AmazonController {

    private final AmazonService amazonService;

    public AmazonController(AmazonService amazonService) {
        this.amazonService = amazonService;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonService.uploadFile(file);
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<Void> deleteFile(@RequestPart(value = "url") String fileUrl) {
        amazonService.deleteFile(fileUrl);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}