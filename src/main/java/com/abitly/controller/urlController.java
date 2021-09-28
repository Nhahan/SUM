package com.abitly.controller;

import com.abitly.dto.request.postUrlRequestDto;
import com.abitly.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class urlController {

    private final UrlService urlService;

    @PostMapping
    public void postUrl(@RequestBody postUrlRequestDto requestDto) {
        urlService.postUrl(requestDto);
    }
}
