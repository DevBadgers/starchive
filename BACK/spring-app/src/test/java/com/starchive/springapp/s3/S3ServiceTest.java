package com.starchive.springapp.s3;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazonaws.services.s3.AmazonS3;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;


@SpringBootTest
@Import(S3MockConfig.class)
class S3ServiceTest {

    @Autowired
    @Qualifier("MockS3Client")
    private AmazonS3 amazonS3;

    @Autowired
    S3MockConfig s3MockConfig;

    @Autowired
    private S3Service s3Service;

    @Test
    public void 이미지_업로드_테스트() throws Exception {
        //given
        String path = "test.png";
        String contentType = "image/png";
        String bucket = s3MockConfig.getTestBucketName();

        MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());
        //Reflection s3Service
        Field reflectionFieldFor_amazonS3 = s3Service.getClass().getDeclaredField("amazonS3");
        reflectionFieldFor_amazonS3.setAccessible(true);
        reflectionFieldFor_amazonS3.set(s3Service, amazonS3);

        Field reflectionFieldFor_bucket = s3Service.getClass().getDeclaredField("bucket");
        reflectionFieldFor_bucket.setAccessible(true);
        reflectionFieldFor_bucket.set(s3Service, bucket);

        //when
        String urlPath = s3Service.saveFile(file);

        //then
        assertThat(urlPath).contains(bucket);

        amazonS3.listBuckets().forEach(System.out::println);
        amazonS3.listObjects(bucket).getObjectSummaries().forEach(System.out::println);

        assertThat(amazonS3.listObjects(bucket).getObjectSummaries().size()).isEqualTo(1);

        String key = urlPath.substring(urlPath.lastIndexOf("/") + 1);

        assertThat(amazonS3.listObjects(bucket).getObjectSummaries().get(0).getKey())
                .isEqualTo(key);

    }

}