package com.abitly.domain.visitor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Document(collection = "visitor")
@NoArgsConstructor
public class Visitor {

    @Id
    private String visitorId;
    private String ipAddress;
    private LocalDateTime createdAt;

    public void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public Visitor(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public static Visitor createVisitor(String ipAddress) {
        return Visitor.builder()
                .ipAddress(ipAddress)
                .build();
    }
}
