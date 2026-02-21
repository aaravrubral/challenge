package com.blackrock.challenge.util;

import java.time.LocalDateTime;

public class DateUtils {
    public static boolean inRange(LocalDateTime t, LocalDateTime s, LocalDateTime e) {
        return !t.isBefore(s) && !t.isAfter(e);
    }
}
