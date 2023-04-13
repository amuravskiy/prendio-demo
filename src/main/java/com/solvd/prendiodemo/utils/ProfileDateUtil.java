package com.solvd.prendiodemo.utils;

import org.apache.commons.lang3.StringUtils;

public class ProfileDateUtil {

    public static String formatDate(String date) {
        return StringUtils.stripStart(date, "0")
                .replace("/0", "/");
    }
}
