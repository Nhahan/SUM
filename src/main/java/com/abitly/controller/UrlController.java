package com.abitly.controller;

import com.abitly.dto.request.PatchAliasNameRequestDto;
import com.abitly.dto.request.PostUrlRequestDto;
import com.abitly.dto.response.PostUrlResponseDto;
import com.abitly.dto.response.ShortLinkListResponseDto;
import com.abitly.dto.response.UrlByShortIdResponseDto;
import com.abitly.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UrlController {

    private final UrlService urlService;

    @GetMapping("/u/{url}")
    public String getUrl(@PathVariable String url) {
        return urlService.getUrl(url);
    }

    @PostMapping("/short-links")
    public PostUrlResponseDto postUrl(@RequestBody PostUrlRequestDto requestDto) {
        return urlService.postUrl(requestDto);
    }

    @GetMapping("/short-links")
    public ShortLinkListResponseDto getShortLinkList(@RequestParam("size") int size, @RequestParam("page") int page) {
        return urlService.getShortLinkList(size, page);
    }

    @GetMapping("/short-links/{short_id}")
    public UrlByShortIdResponseDto getUrlByShortId(@PathVariable String short_id) {
        return urlService.getUrlByShortId(short_id);
    }

    @PatchMapping("/short-links/{short_id}")
    public void patchAliasName(@RequestBody PatchAliasNameRequestDto requestDto, @PathVariable String short_id) {
        urlService.patchAliasNameRequestDto(requestDto, short_id);
    }

    @GetMapping("/s/{short_id}")
    public void redirectWithShortId(HttpServletResponse httpServletResponse,
                                    @PathVariable String short_id) throws IOException {
        String redirectedUrl = urlService.getRedirectedUrlWithShortId(short_id);
        httpServletResponse.sendRedirect(redirectedUrl);
    }

    @GetMapping("/a/{alias_name}")
    public void redirectWithAliasName(HttpServletResponse httpServletResponse,
                                      @PathVariable String alias_name) throws IOException {
        String redirectedUrl = urlService.getRedirectedUrlWithAliasName(alias_name);
        httpServletResponse.sendRedirect(redirectedUrl);
    }

    @DeleteMapping("/short-links/{short_id}")
    public void deleteShortId(@PathVariable String short_id) {
        urlService.deleteShortId(short_id);
    }
}
