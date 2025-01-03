package com.starchive.springapp.image.controller;

import com.starchive.springapp.global.dto.ResponseDto;
import com.starchive.springapp.image.dto.PostImageDto;
import com.starchive.springapp.image.service.PostImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "게시글 이미지")
@RestController
@RequiredArgsConstructor
public class PostImageController {
    private final PostImageService postImageService;

    @Operation(summary = "게시글에 포함될 이미지 업로드")
    @PostMapping(value = "/postImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<PostImageDto>> imageUpload(@RequestParam("image") MultipartFile image) {
        PostImageDto postImageDto = postImageService.uploadImage(image);

        return ResponseEntity.ok(new ResponseDto<>(postImageDto));
    }
}
