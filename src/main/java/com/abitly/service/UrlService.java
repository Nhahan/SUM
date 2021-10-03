package com.abitly.service;

import com.abitly.domain.url.Url;
import com.abitly.domain.url.UrlRepository;
import com.abitly.domain.visitor.Visitor;
import com.abitly.domain.visitor.VisitorRepository;
import com.abitly.dto.request.PatchAliasNameRequestDto;
import com.abitly.dto.request.PostUrlRequestDto;
import com.abitly.dto.response.*;
import com.abitly.exception.ApiRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private final VisitorRepository visitorRepository;

    @Transactional
    public PostUrlResponseDto postUrl(PostUrlRequestDto requestDto) {
        String shortId = getShortId();

        Url url = urlRepository.findByUrl(requestDto.getUrl()).orElseGet(
                () -> urlRepository.save(createUrl(requestDto.getUrl(), shortId, shortId)));

        if (url.getCreatedAt().isAfter(LocalDateTime.now().minusSeconds(1))) {
            log.info(requestDto.getUrl() + " posted as " + shortId);
        }

        PostUrlResponseDtoData data = createPostUrlResponseDtoData(url);

        VisitorIpChecker();

        return createPostUrlResponseDto(data);
    }

    private void VisitorIpChecker() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null)
            ipAddress = req.getRemoteAddr();

        Visitor visitor = Visitor.createVisitor(ipAddress);
        visitorRepository.save(visitor);

        Long visitorCounts = visitorRepository.countByIpAddress(ipAddress);

        if (visitorCounts >= 6L) {
            throw new ApiRequestException("too many requests");
        }
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
        if (urlRepository.findAll().stream()
                .anyMatch(u -> exceptionWhenShortUrlAlreadyExist(u, requestDto.getAliasName()))) {
            throw new ApiRequestException("url already exist");
        }

        url.updateAliasName(requestDto.getAliasName());

        log.info(short_id + " has been patched with " + requestDto.getAliasName());
    }

    public boolean exceptionWhenShortUrlAlreadyExist(Url url, String aliasName) {
        return url.getAliasName().equals(aliasName) || url.getUrl().equals(aliasName);
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
        Long count = urlRepository.deleteByShortId(short_id);

        if (count > 0) {
            log.info(short_id + " has been deleted");
        } else {
            throw new ApiRequestException("url not found");
        }
    }

    public String getShortId() {
        List<String> urlList = urlRepository.findAll()
                .stream()
                .flatMap(u -> Stream.of(u.getShortId(), u.getAliasName()))
                .distinct()
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

    public String getUrl(String url) {
        Url foundUrl = urlRepository.findByUrl(url).orElseThrow(() -> new ApiRequestException("url not found"));
        return "suml.xyz/s/" + foundUrl.getShortId();
    }
}
