package com.solvd.prendiodemo.utils;

import com.zebrunner.carina.utils.R;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final int HOURS_OFFSET_FROM_UTC = R.TESTDATA.getInt("hours_offset_from_utc");
    private static final DateTimeFormatter DATE_FORMATTER_SHORT = DateTimeFormatter.ofPattern("M/d/yyyy").withZone(ZoneOffset
            .ofHours(HOURS_OFFSET_FROM_UTC));

    public static String formatDateProfile(String date) {
        return StringUtils.stripStart(date, "0")
                .replace("/0", "/");
    }

    public static int getDayOfTheMonth() {
        return Instant.now().atOffset(ZoneOffset.ofHours(HOURS_OFFSET_FROM_UTC)).getDayOfMonth();
    }

    public static String getCurrentDateScanMatch() {
        return DATE_FORMATTER_SHORT.format(Instant.now());
    }
}
