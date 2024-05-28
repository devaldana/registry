package com.univercenter.registry.common;

import java.util.regex.Pattern;

public final class StringUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,3}$");

    private StringUtils() {}

    public static boolean matches(final String value, final String regex) {
        return Pattern.matches(regex, value);
    }

    public static boolean isBlank(final String value) {
        return value == null || value.isBlank();
    }

    public static boolean isValidEmail(final String email) {
        return !isBlank(email) && EMAIL_PATTERN.matcher(email).matches();
    }
}
