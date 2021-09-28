package com.abitly.domain.url;

import com.abitly.domain.common.Timestamped;
import com.abitly.dto.request.PostUrlRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Url extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long urlId;
    private String url;
    private String shortId;
    private String aliasName;

    @Builder
    public Url(String url, String shortId, String aliasName) {
        this.url = url;
        this.shortId = shortId;
        this.aliasName = aliasName;
    }

    public static Url createUrl(String url, String shortId, String aliasName) {
        return Url.builder()
                .url(url)
                .shortId(shortId)
                .aliasName(aliasName)
                .build();
    }
}
