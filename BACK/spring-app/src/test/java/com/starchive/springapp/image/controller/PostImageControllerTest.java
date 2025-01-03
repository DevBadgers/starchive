package com.starchive.springapp.image.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.starchive.springapp.image.dto.PostImageDto;
import com.starchive.springapp.image.service.PostImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PostImageController.class)
@ExtendWith(MockitoExtension.class)
class PostImageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostImageService postImageService;

    @InjectMocks
    private PostImageController postImageController;

    @Test
    public void 이미지_업로드_컨트롤러_단위_테스트() throws Exception {
        //given
        String path = "test.png";
        String contentType = "image/png";
        String content = "테스트 내용";

        MockMultipartFile file = new MockMultipartFile("image", path, contentType, content.getBytes());

        Mockito.when(postImageService.uploadImage(Mockito.any(MockMultipartFile.class)))
                .thenReturn(new PostImageDto(1L, "Https://" + path));
        //when
        //then
        mockMvc.perform(multipart("/postImage")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.imagePath").value("Https://" + path));
    }

}