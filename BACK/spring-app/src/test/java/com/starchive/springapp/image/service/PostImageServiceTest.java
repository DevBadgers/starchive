package com.starchive.springapp.image.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.starchive.springapp.image.domain.PostImage;
import com.starchive.springapp.image.dto.PostImageDto;
import com.starchive.springapp.image.repository.PostImageRepository;
import com.starchive.springapp.s3.S3Service;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class PostImageServiceTest {
    @Mock
    private S3Service s3Service;

    @Mock
    private PostImageRepository postImageRepository;

    @InjectMocks
    private PostImageService postImageService;

    @Test
    public void 이미지_업로드_서비스로직_단위_테스트() throws Exception {
        //given
        String path = "test.png";
        String contentType = "image/png";
        String content = "테스트 내용";

        MockMultipartFile file = new MockMultipartFile("test", path, contentType, content.getBytes());

        when(s3Service.saveFile(Mockito.any(MockMultipartFile.class))).thenReturn("https://test.png");
        when(postImageRepository.save(Mockito.any())).then(invocation -> {
            PostImage postImage = invocation.getArgument(0);
            Field field = postImage.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(postImage, 1L);
            return postImage;
        });

        //when
        PostImageDto postImageDto = postImageService.uploadImage(file);

        //then
        verify(s3Service).saveFile(Mockito.any(MockMultipartFile.class));
        verify(postImageRepository).save(Mockito.any());
        assertThat(postImageDto.getId()).isEqualTo(1L);
        assertThat(postImageDto.getImagePath()).contains("test.png");
    }

}