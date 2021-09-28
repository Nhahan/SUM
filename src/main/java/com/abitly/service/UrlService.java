package com.abitly.service;

import com.abitly.domain.url.Url;
import com.abitly.domain.url.UrlRepository;
import com.abitly.dto.request.PatchAliasNameRequestDto;
import com.abitly.dto.request.PostUrlRequestDto;
import com.abitly.dto.response.*;
import com.abitly.exception.ApiRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.abitly.domain.url.Url.createUrl;
import static com.abitly.dto.response.PostUrlResponseDto.createPostUrlResponseDto;
import static com.abitly.dto.response.PostUrlResponseDtoData.createPostUrlResponseDtoData;
import static com.abitly.dto.response.ShortLinkListResponseDto.createShortLinkListResponseDto;
import static com.abitly.dto.response.UrlByShortIdResponseDto.createUrlByShortIdResponseDto;
import static com.abitly.dto.response.UrlByShortIdResponseDtoData.createUrlByShortIdResponseDtoData;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    @Transactional
    public PostUrlResponseDto postUrl(PostUrlRequestDto requestDto) {
        String shortId = getShortId();
        Url url = createUrl(requestDto.getUrl(), shortId, shortId);
        urlRepository.save(url);

        log.info(requestDto.getUrl() + " posted as " + shortId);

        PostUrlResponseDtoData data = createPostUrlResponseDtoData(url);

        return createPostUrlResponseDto(data);
    }

    public ShortLinkListResponseDto getShortLinkList(int size, int page) {
        List<Url> urlList = urlRepository.findAllPaging(PageRequest.of(page - 1, size));
        List<PostUrlResponseDtoData> dataList = urlList.stream()
                .map(PostUrlResponseDtoData::createPostUrlResponseDtoData)
                .collect(Collectors.toList());
        return createShortLinkListResponseDto(dataList);
    }

    public UrlByShortIdResponseDto getUrlByShortId(String short_id) {
        Url url = urlRepository.findByShortId(short_id).orElseThrow(() -> new ApiRequestException("url not found"));
        UrlByShortIdResponseDtoData data = createUrlByShortIdResponseDtoData(url);
        return createUrlByShortIdResponseDto(data);
    }

    @Transactional
    public void patchAliasNameRequestDto(PatchAliasNameRequestDto requestDto, String short_id) {
        Url url = urlRepository.findByShortId(short_id).orElseThrow(() -> new ApiRequestException("url not found"));
        urlRepository.findByAliasName(requestDto.getAliasName()).orElseThrow(
                () -> new ApiRequestException("url already existed"));

        url.updateAliasName(requestDto.getAliasName());

        log.info(short_id + " has been patched with " + requestDto.getAliasName());
    }

    public String getRedirectedUrlWithAliasName(String alias_name) {
        Url url = urlRepository.findByAliasName(alias_name).orElseThrow(() -> new ApiRequestException("url not found"));
        return url.getUrl();
    }

    public String getRedirectedUrlWithShortId(String short_id) {
        Url url = urlRepository.findByShortId(short_id).orElseThrow(() -> new ApiRequestException("url not found"));
        return url.getUrl();
    }

    @Transactional
    public void deleteShortId(String short_id) {
        urlRepository.deleteByShortId(short_id);

        log.info(short_id + " has been deleted");
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