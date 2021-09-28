package com.abitly.dto.response;

import com.abitly.domain.url.Url;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UrlByShortIdResponseDtoData {

    private String aliasName;
    private String shortId;
    private LocalDateTime createdAt;

    @Builder
    public UrlByShortIdResponseDtoData(String aliasName, String shortId, LocalDateTime createdAt) {
        this.aliasName = aliasName;
        this.shortId = shortId;
        this.createdAt = createdAt;
    }

    public static UrlByShortIdResponseDtoData createUrlByShortIdResponseDtoData(Url url) {
        return UrlByShortIdResponseDtoData.builder()
                .shortId(url.getShortId())
                .aliasName(url.getAliasName())
                .createdAt(url.getCreatedAt())
                .build();
    }
}
