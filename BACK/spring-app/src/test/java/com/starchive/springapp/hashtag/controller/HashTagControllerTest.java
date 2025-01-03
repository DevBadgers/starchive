package com.starchive.springapp.hashtag.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starchive.springapp.hashtag.domain.HashTag;
import com.starchive.springapp.hashtag.dto.HashTagCheckRequest;
import com.starchive.springapp.hashtag.dto.HashTagDto;
import com.starchive.springapp.hashtag.dto.HashTagUpdateRequest;
import com.starchive.springapp.hashtag.service.HashTagService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HashTagController.class)
class HashTagControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HashTagService hashTagService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void 전체_해쉬태그_목록_반환_테스트() throws Exception {
        //given
        List<HashTagDto> mockTags = Arrays.asList(
                new HashTag("Spring"),
                new HashTag("Java")
        ).stream().map(HashTagDto::from).toList();

        when(hashTagService.findAll()).thenReturn(mockTags);
        //when
        //then
        mockMvc.perform(get("/hashtags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(mockTags.size()))
                .andExpect(jsonPath("$.data[0].name").value("Spring"))
                .andExpect(jsonPath("$.data[1].name").value("Java"));
    }

    @Test
    public void 태그조회_없으면_저장_후_반환_테스트() throws Exception {
        // given
        HashTagDto mockTag = HashTagDto.from(new HashTag("Spring"));
        when(hashTagService.findOneOrSave(anyString())).thenReturn(mockTag);

        HashTagCheckRequest request = new HashTagCheckRequest("Spring");

        // when & then
        mockMvc.perform(post("/hashtag")
                        .contentType(MediaType.APPLICATION_JSON) // 요청 Content-Type
                        .content(objectMapper.writeValueAsString(request))) // 요청 본문 JSON 직렬화
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Spring"));
    }

    @Test
    public void 해쉬태그_이름이_빈문자열인경우_예외발생() throws Exception {
        // Given
        HashTagCheckRequest request = new HashTagCheckRequest(""); // name이 빈 문자열

        // When & Then
        mockMvc.perform(post("/hashtag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()) // 400 Bad Request
                .andExpect(jsonPath("$.name").value("해쉬태그이름은 1글자 이상이어야 합니다.")); // 에러 메시지 검증
    }

    @Test
    public void 해쉬태그_이름이_길이가_32자를_초과한경우_예외발생() throws Exception {
        // Given
        String longName = "a".repeat(33); // 33글자 문자열
        HashTagCheckRequest request = new HashTagCheckRequest(longName);

        // When & Then
        mockMvc.perform(post("/hashtag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()) // 400 Bad Request
                .andExpect(jsonPath("$.name").value("해쉬태그이름은 32자 보다 작거나 같아야 합니다.")); // 에러 메시지 검증
    }

    @Test
    void 해시태그_이름_수정_API_테스트() throws Exception {
        // Given
        Long hashTagId = 1L;
        String updatedName = "UpdatedName";
        HashTagDto mockResponse = new HashTagDto(hashTagId, updatedName);

        HashTagUpdateRequest request = new HashTagUpdateRequest(hashTagId, updatedName);

        when(hashTagService.updateName(eq(hashTagId), eq(updatedName))).thenReturn(mockResponse);

        /// When & Then
        mockMvc.perform(put("/hashtag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))) // 요청 데이터 변환
                .andExpect(status().isOk()) // 200 상태 코드 확인
                .andExpect(jsonPath("$.data.id").value(hashTagId)) // 응답 ID 확인
                .andExpect(jsonPath("$.data.name").value(updatedName)); // 응답 이름 확인
    }

    @Test
    void 해시태그_삭제_API_테스트() throws Exception {
        // Given
        Long hashTagId = 1L;

        doNothing().when(hashTagService).delete(eq(hashTagId));

        // When & Then
        mockMvc.perform(delete("/hashtag")
                        .param("hashTagId", hashTagId.toString()))
                .andExpect(status().isNoContent()); // 204 상태 코드 확인
    }
}