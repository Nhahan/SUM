package com.abitly.service;

import com.abitly.domain.url.Url;
import com.abitly.domain.url.UrlRepository;
import com.abitly.dto.request.PostUrlRequestDto;
import com.abitly.dto.response.PostUrlResponseDto;
import com.abitly.dto.response.PostUrlResponseDtoData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.abitly.domain.url.Url.createUrl;
import static com.abitly.dto.response.PostUrlResponseDtoData.createPostUrlResponseDtoData;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    @Transactional
    public PostUrlResponseDto postUrl(PostUrlRequestDto requestDto) {
        String shortId = getShortId();
        Url createdUrl = createUrl(requestDto.getUrl(), shortId, shortId);
        Url savedUrl = urlRepository.save(createdUrl);

        log.info(requestDto.getUrl() + " posted as " + shortId);

        PostUrlResponseDtoData data = createPostUrlResponseDtoData(savedUrl);

        return PostUrlResponseDto.createPostUrlResponseDto(data);
    }

    public String getShortId() {
        List<String> urlList = urlRepository.findAll()
                .stream()
                .map(Url::getUrl)
                .collect(Collectors.toList());

        while (true) {
            String randomUrl = createShortId();
            if (!urlList.contains(randomUrl)) {
                return randomUrl;
            }
        }
    }

    private String createShortId() {
        final int URL_LENGTH = 5;

        Random random = new Random();

        StringBuilder randomUrl = new StringBuilder();
        for (int i = 0; i < URL_LENGTH; i++) {
            int choice = random.nextInt(2);
            switch (choice) {
                case 0:
                    randomUrl.append((char) (random.nextInt(25) + 97));
                    break;
                case 1:
                    randomUrl.append((char) (random.nextInt(25) + 65));
                    break;
            }
        }

        return randomUrl.toString();
    }
}
