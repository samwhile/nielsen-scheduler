package com.nielsen.sports.test.scheduler.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Slf4j
public class CommonUtils {

    /**
     * Return a date offset with random hours from 9-5
     * @param days days offset
     * @return localDateTime
     */
    public static LocalDateTime getLocalDateTimeOffset(int days) {
        int randomHour = random(9, 17);
        return LocalDateTime
                .of(LocalDate.now(), LocalTime.of(randomHour, 0))
                .plusDays(days);
    }

    /**
     * Return a date offset with random hours from 9-5
     * @param date date
     * @return localDateTime
     */
    public static LocalDateTime getLocateDateTimeFrom(String date) {
        try {
            return LocalDateTime.parse(date);
        } catch(DateTimeParseException e) {
            log.error("Failed to parse date {}", date);
            return null;
        }
    }


    /**
     *
     * @param lowerBound lower bound for random number
     * @param upperBound upper bound for random number
     * @return random number
     */
    public static int random(int lowerBound, int upperBound) {
        return (int) ((Math.random() * (upperBound - lowerBound)) + lowerBound);
    }

    /**
     * Returns a random UUID truncated to size.
     * @param len size of uuid
     * @return uuid
     */
    public static String getRandomUUID(int len) {
        return UUID.randomUUID().toString().substring(0,len);
    }
}
