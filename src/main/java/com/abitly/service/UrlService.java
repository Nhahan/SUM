package com.abitly.service;

import com.abitly.domain.url.Url;
import com.abitly.domain.url.UrlRepository;
import com.abitly.dto.request.PostUrlRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    public void postUrl(PostUrlRequestDto requestDto) {

    }

    public String getUrl() {
        List<String> urlList = urlRepository.findAll()
                .stream()
                .map(Url::getUrl)
                .collect(Collectors.toList());

        while (true) {
            String randomUrl = createUrl();
            if (!urlList.contains(randomUrl)) {
                return randomUrl;
            }
        }
    }

    private String createUrl() {
        final int URL_LENGTH = 5;
        final int START_a = 97;
        final int END_z = 123;

        Random random = new Random();

        return random.ints(START_a, END_z)
                .limit(URL_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
