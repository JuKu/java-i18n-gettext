package com.jukusoft.i18n.utils;

import java.util.Objects;

public class StringUtils {

    protected StringUtils () {
        //
    }

    public static final void requireNotEmpty (String str) {
        requireNotEmpty(str, "string");
    }

    public static final void requireNotEmpty (String str, String name) {
        Objects.requireNonNull(str);

        if (str.isEmpty()) {
            throw new IllegalArgumentException(name + " cannot be empty!");
        }
    }

}
