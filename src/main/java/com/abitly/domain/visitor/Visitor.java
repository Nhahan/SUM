package com.abitly.domain.visitor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
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

    @Indexed(name = "ipAddress", direction = IndexDirection.DESCENDING)
    private String ipAddress;

    @Indexed(name = "expire_after_seconds_index", expireAfterSeconds = 300)
    private LocalDateTime dateOfBirth;

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
