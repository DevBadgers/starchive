package com.starchive.springapp.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequest {
    @NotEmpty
    @Size(max = 64)
    @Schema(description = "게시글 제목", example = "게시글 제목 예시")
    private String title;

    @NotEmpty
    @Schema(description = "게시글 내용", example = "게시글 내용 예시")
    private String content;

    @NotEmpty
    @Size(max = 32)
    @Schema(description = "작성자 이름", example = "홍길동")
    private String author;

    @NotEmpty
    @Size(max = 128)
    @Schema(description = "비밀번호", example = "1234")
    private String password;

    @NotNull
    @Schema(description = "카테고리 ID", example = "1")
    private Long categoryId;

    @Nullable
    @Schema(description = "해쉬 태그 ID", example = "[1,2,3]")
    private List<Long> hashTagIds;

    @Nullable
    @Schema(description = "첨부 이미지 ID", example = "[1,2]")
    private List<Long> imageIds;

}
