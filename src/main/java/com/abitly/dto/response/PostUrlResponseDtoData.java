package com.abitly.dto.response;

import com.abitly.domain.url.Url;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostUrlResponseDtoData {
    private String url;
    private String shortId;
    private LocalDateTime createdAt;

    @Builder
    public PostUrlResponseDtoData(String url, String shortId, LocalDateTime createdAt) {
        this.url = url;
        this.shortId = shortId;
        this.createdAt = createdAt;
    }

    public static PostUrlResponseDtoData createPostUrlResponseDtoData(Url url) {
        return PostUrlResponseDtoData.builder()
                .url(url.getUrl())
                .shortId(url.getShortId())
                .createdAt(url.getCreatedAt())
                .build();
    }
}
