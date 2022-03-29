package com.alkemy.ong.cloud;

import com.alkemy.ong.domain.cloud.CloudGateway;
import com.alkemy.ong.domain.exceptions.ServiceUnavailableException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Component
public class AwsGateway implements CloudGateway {

        private AmazonS3 s3client;

        @Value("${amazonProperties.endpointUrl}")
        private String endpointUrl;
        @Value("${amazonProperties.bucketName}")
        private String bucketName;
        @Value("${amazonProperties.accessKey}")
        private String accessKey;
        @Value("${amazonProperties.secretKey}")
        private String secretKey;

        @PostConstruct
        private void initializeAmazon() {
            AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
            this.s3client = new AmazonS3Client(credentials);
        }

        @Override
        public String uploadFile(MultipartFile multipartFile) {
            String fileUrl = "";
            try {
                File file = convertMultiPartToFile(multipartFile);
                String fileName = generateFileName(multipartFile);
                fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
                uploadFileToS3bucket(fileName, file);
                file.delete();
            } catch (IOException e) {
                throw new ServiceUnavailableException("The server is not available to save the file");
            }
            return fileUrl;
        }

        private File convertMultiPartToFile(MultipartFile file) throws IOException {
            File convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        }

        private String generateFileName(MultipartFile multiPart) {
            return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
        }

        public void uploadFileToS3bucket(String fileName, File file) {
            s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }

        public String deleteFileFromS3Bucket(String fileUrl) {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
            return "Successfully deleted";
        }
    }

