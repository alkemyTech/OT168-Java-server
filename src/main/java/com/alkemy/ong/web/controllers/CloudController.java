package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.cloud.CloudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "5. Cloud")
@RestController
@RequestMapping("/storage/")
public class CloudController {

    private final CloudService amazonService;

    public CloudController(CloudService amazonService) {
        this.amazonService = amazonService;
    }

    @Operation(summary = "Upload a file to the cloud")
    @PostMapping(value = "/uploadFile", consumes = {MULTIPART_FORM_DATA_VALUE} )
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        String url = amazonService.uploadFile(file);
        return ResponseEntity.ok().body(url);
    }

    @Operation(summary = "Delete a file that is in the cloud")
    @DeleteMapping("/deleteFile")
    public ResponseEntity<Void> deleteFile(@RequestParam(value = "url") String fileUrl) {
        amazonService.deleteFile(fileUrl);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}