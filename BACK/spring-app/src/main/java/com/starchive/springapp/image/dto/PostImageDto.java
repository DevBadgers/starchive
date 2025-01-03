package com.starchive.springapp.image.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostImageDto {
    Long id;
    String imagePath;
}
