package com.starchive.springapp.image.service;

import com.starchive.springapp.image.domain.PostImage;
import com.starchive.springapp.image.dto.PostImageDto;
import com.starchive.springapp.image.repository.PostImageRepository;
import com.starchive.springapp.post.domain.Post;
import com.starchive.springapp.s3.S3Service;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostImageService {
    private final S3Service s3Service;
    private final PostImageRepository postImageRepository;

    public PostImageDto uploadImage(MultipartFile image) {
        String imagePath = s3Service.saveFile(image);
        PostImage postImage = new PostImage(imagePath);

        postImageRepository.save(postImage);

        return new PostImageDto(postImage.getId(), postImage.getImagePath());
    }

    public void setPost(List<Long> imageIds, Post post) {
        List<PostImage> postImages = postImageRepository.findManyByIdIn(imageIds);
        postImages.forEach(postImage -> {
            postImage.setPost(post);
        });
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void deleteOldOrphanedPostImages() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(1); // 하루 전

        List<PostImage> oldOrphanedPostImages = postImageRepository.findOldOrphanedPostImages(cutoffDate);
        oldOrphanedPostImages.forEach(postImage -> {
            s3Service.deleteObject(extractKeyFromUrl(postImage.getImagePath()));
            postImageRepository.delete(postImage);
            log.info("Deleted old orphaned PostImages: {}", postImage.getImagePath());
        });

    }

    private String extractKeyFromUrl(String url) {
        URI uri = URI.create(url);
        return uri.getPath().substring(1); // 첫 번째 '/' 제거
    }

}
