package com.abitly.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ShortLinkListResponseDto {

    private List<PostUrlResponseDtoData> data = new ArrayList<>();

    @Builder
    public ShortLinkListResponseDto(List<PostUrlResponseDtoData> data) {
        this.data = data;
    }

    public static ShortLinkListResponseDto createShortLinkListResponseDto(List<PostUrlResponseDtoData> dto) {
        return ShortLinkListResponseDto.builder()
                .data(dto)
                .build();
    }
}