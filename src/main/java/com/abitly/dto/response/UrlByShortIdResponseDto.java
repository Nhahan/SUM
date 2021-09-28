package com.abitly.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UrlByShortIdResponseDto {
    private UrlByShortIdResponseDtoData data;

    @Builder
    public UrlByShortIdResponseDto(UrlByShortIdResponseDtoData data) {
        this.data = data;
    }

    public static UrlByShortIdResponseDto createUrlByShortIdResponseDto(UrlByShortIdResponseDtoData data) {
        return UrlByShortIdResponseDto.builder()
                .data(data)
                .build();
    }
}
