package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.cloud.CloudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage/")
public class CloudController {

    private final CloudService amazonService;

    public CloudController(CloudService amazonService) {
        this.amazonService = amazonService;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestPart(value = "file") MultipartFile file) {
        String url = amazonService.uploadFile(file);
        return ResponseEntity.ok().body(url);
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<Void> deleteFile(@RequestPart(value = "url") String fileUrl) {
        amazonService.deleteFile(fileUrl);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}