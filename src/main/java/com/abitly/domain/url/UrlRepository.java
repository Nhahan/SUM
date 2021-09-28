package com.abitly.domain.url;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    @Query("select u from Url u order by u.createdAt asc")
    List<Url> findAllPaging(PageRequest pageable);

    Optional<Url> findByShortId(String short_id);

    Optional<Url> findByAliasName(String alias_name);

    void deleteByShortId(String short_id);
}
