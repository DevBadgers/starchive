package com.starchive.springapp.hashtag.dto;

import com.starchive.springapp.hashtag.domain.HashTag;
import lombok.Data;

@Data
public class HashTagResponse {
    private Long hashTagId;
    private String name;

    public static HashTagResponse from(HashTag hashTag) {
        HashTagResponse hashTagDto = new HashTagResponse();
        hashTagDto.hashTagId = hashTag.getId();
        hashTagDto.name = hashTag.getName();
        return hashTagDto;
    }
}
