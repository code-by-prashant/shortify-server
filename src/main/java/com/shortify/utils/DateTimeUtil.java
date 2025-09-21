package com.shortify.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Utility class for Date and LocalDateTime conversions.
 */
public class DateTimeUtil {

    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    /**
     * Converts Date to LocalDateTime.
     *
     * @param date input Date
     * @return LocalDateTime or null if input is null
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) return null;
        return Instant.ofEpochMilli(date.getTime())
                      .atZone(DEFAULT_ZONE)
                      .toLocalDateTime();
    }

    /**
     * Converts LocalDateTime to Date.
     *
     * @param localDateTime input LocalDateTime
     * @return Date or null if input is null
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return Date.from(localDateTime.atZone(DEFAULT_ZONE).toInstant());
    }
}
