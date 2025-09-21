package com.shortify.service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shortify.entity.Url;
import com.shortify.entity.UrlCounter;
import com.shortify.model.UrlRequest;
import com.shortify.repository.UrlCounterRepository;
import com.shortify.repository.UrlRepository;
import com.shortify.utils.Base64EncoderUtil;
import com.shortify.utils.DateTimeUtil;

import lombok.RequiredArgsConstructor;

/**
 * Service for URL shortening and retrieval.
 */
@Service
@RequiredArgsConstructor
public class UrlService {

    private static final int EXPIRY_IN_YEARS = 10;
    private final static long CACHE_TTL_HOURS = 24;

    private final UrlRepository urlRepository;
    private final UrlCounterRepository counterRepository;
    private final RedisTemplate<String, Url> redisTemplate;


    /**
     * Shorten a long URL using Base64 encoding of a counter ID.
     *
     * @param request the request
     * @return short URL string
     */
    @Transactional
    public String shortenUrl(final UrlRequest request) {
        var counter = counterRepository.save(new UrlCounter());

        var shortUrl = Base64EncoderUtil.encode(counter.getId());
        
        var url = Url.builder().originalUrl(request.getOriginalUrl())
        		.userId(request.getUserId())
        		.shortUrl(shortUrl)
        		.createdAt(DateTimeUtil.toDate(LocalDateTime.now()))
        		.expiryAt(DateTimeUtil.toDate(LocalDateTime.now().plusYears(EXPIRY_IN_YEARS))).build();

        urlRepository.save(url);

        // Cache with TTL
        redisTemplate.opsForValue().set(shortUrl, url, CACHE_TTL_HOURS, TimeUnit.HOURS);

        return shortUrl;
    }

    /**
     * Retrieve the original URL from a shortened URL.
     *
     * @param shortUrl short URL string
     * @return URL entity
     */
    public Url getOriginalUrl(final String shortUrl) {
        // Redis cache first
        var url = redisTemplate.opsForValue().get(shortUrl);
        if (url != null && url.getExpiryAt().after(DateTimeUtil.toDate(LocalDateTime.now()))) {
            return url;
        }

        url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        if (url.getExpiryAt().before(DateTimeUtil.toDate(LocalDateTime.now()))) {
            throw new RuntimeException("URL expired");
        }

        // Update cache
        redisTemplate.opsForValue().set(shortUrl, url, CACHE_TTL_HOURS, TimeUnit.HOURS);
        return url;
    }
}
