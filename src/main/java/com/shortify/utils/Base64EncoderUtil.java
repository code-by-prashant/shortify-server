package com.shortify.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Utility class for Base64 encoding/decoding.
 */
public class Base64EncoderUtil {

    /**
     * Encodes a numeric ID into a Base64 URL-safe string.
     * 
     * @param id the numeric ID
     * @return encoded Base64 string
     */
    public static String encode(final long id) {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(String.valueOf(id).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Decodes a Base64 URL-safe string back to numeric ID.
     * 
     * @param encoded encoded Base64 string
     * @return numeric ID
     */
    public static long decode(final String encoded) {
        var decoded = new String(Base64.getUrlDecoder().decode(encoded), StandardCharsets.UTF_8);
        return Long.parseLong(decoded);
    }
}
