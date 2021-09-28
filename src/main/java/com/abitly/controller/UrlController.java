package com.abitly.controller;

import com.abitly.dto.request.PostUrlRequestDto;
import com.abitly.dto.response.PostUrlResponseDto;
import com.abitly.dto.response.ShortLinkListResponseDto;
import com.abitly.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/short-links")
    public PostUrlResponseDto postUrl(@RequestBody PostUrlRequestDto requestDto) {
        return urlService.postUrl(requestDto);
    }

    @GetMapping("/r/{short_id}")
    public String redirectWithShortId(@PathVariable String short_id) {
        String redirectedUrl = urlService.getRedirectedUrl(short_id);
        return "redirect:" + redirectedUrl;
    }

    @GetMapping("/short-links")
    public ShortLinkListResponseDto getShortLinkList(@RequestParam("size") int size, @RequestParam("page") int page) {
        return urlService.getShortLinkList(size, page);
    }

    @GetMapping("/short-links/{short_id}")
    public PostUrlResponseDto getUrlByShortId(@PathVariable String short_id) {
        return urlService.getUrlByShortId(short_id);
    }
}
