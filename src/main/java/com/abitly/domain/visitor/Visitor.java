package com.abitly.domain.visitor;

import com.abitly.domain.url.Url;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long urlId;
    private String url;
    private String shortId;
    private String aliasName;
    private LocalDateTime createdAt;

    @PrePersist
    public void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public Visitor(String url, String shortId, String aliasName) {
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

    public void updateAliasName(String aliasName) {
        this.aliasName = aliasName;
    }
}
