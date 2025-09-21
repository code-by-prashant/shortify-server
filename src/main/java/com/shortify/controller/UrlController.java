package com.shortify.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shortify.model.UrlRequest;
import com.shortify.service.UrlService;
import com.shortify.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shortify/url")
@RequiredArgsConstructor
@Tag(name = "ShortUrl Controller")
public class UrlController {	
	
    private final UrlService urlService;
    private final UserService userService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(
    		@RequestBody UrlRequest request,
            @RequestHeader("X-API-KEY") String apiKey) {

        userService.validateApiKey(apiKey);
        var shortUrl = urlService.shortenUrl(request);
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortUrl}")
    public void redirect(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        var url = urlService.getOriginalUrl(shortUrl);
        response.sendRedirect(url.getOriginalUrl());
    }
    
    @GetMapping("/ping")
    public String home() {
        return "Shortify is running!";
    }
}
