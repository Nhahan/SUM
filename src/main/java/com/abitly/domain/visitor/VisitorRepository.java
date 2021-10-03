package com.abitly.domain.visitor;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VisitorRepository extends MongoRepository<Visitor, String> {
    Long countByIpAddress(String ipAddress);
}
