package com.starchive.springapp.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.starchive.springapp.global.ErrorMessage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 단일 파일 저장
    public String saveFile(MultipartFile file) {
        String randomFilename = generateRandomFilename(file);
        log.info("File upload started: {}, bucketName: {}", randomFilename, bucket);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            amazonS3.putObject(bucket, randomFilename, file.getInputStream(), metadata);
        } catch (AmazonS3Exception e) {
            log.error("Amazon S3 error while uploading file: " + e.getMessage());
            throw new RuntimeException(ErrorMessage.FAIL_UPLOAD);
        } catch (SdkClientException e) {
            log.error("AWS SDK client error while uploading file: " + e.getMessage());
            throw new RuntimeException(ErrorMessage.FAIL_UPLOAD);
        } catch (IOException e) {
            log.error("IO error while uploading file: " + e.getMessage());
            throw new RuntimeException(ErrorMessage.FAIL_UPLOAD);
        }

        log.info("File upload completed: " + randomFilename);

        return amazonS3.getUrl(bucket, randomFilename).toString();
    }

    public void deleteObject(String key) {
        amazonS3.deleteObject(bucket, key);
        log.info("Deleted S3 Object: {}", key);
    }

    // 랜덤파일명 생성 (파일명 중복 방지)
    private String generateRandomFilename(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension = validateFileExtension(originalFilename);
        String randomFilename = UUID.randomUUID() + "." + fileExtension;
        return randomFilename;
    }

    // 파일 확장자 체크
    private String validateFileExtension(String originalFilename) {
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        List<String> allowedExtensions = Arrays.asList("jpg", "png", "gif", "jpeg");

        if (!allowedExtensions.contains(fileExtension)) {
            throw new RuntimeException(ErrorMessage.NOT_IMAGE_EXTENSION);
        }
        return fileExtension;
    }
}
