package com.abitly.domain.url;

import com.abitly.domain.common.Timestamped;
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
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long urlId;
    private String url;
    private String shortId;
    private String aliasName;

}
