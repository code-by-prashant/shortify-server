package com.shortify.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.shortify.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interceptor to validate X-API-KEY for protected endpoints.
 */
@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    private final UserService userService;

    public ApiKeyInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
        final var apiKey = request.getHeader("X-API-KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API key missing");
            return false;
        }

        try {
            userService.validateApiKey(apiKey);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API key");
            return false;
        }

        return true;
    }
}

