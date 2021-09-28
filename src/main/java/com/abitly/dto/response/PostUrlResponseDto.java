package com.abitly.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUrlResponseDto {
    private PostUrlResponseDtoData data;

    @Builder
    public PostUrlResponseDto(PostUrlResponseDtoData data) {
        this.data = data;
    }

    public static PostUrlResponseDto createPostUrlResponseDto(PostUrlResponseDtoData data) {
        return PostUrlResponseDto.builder()
                .data(data)
                .build();
    }
}
