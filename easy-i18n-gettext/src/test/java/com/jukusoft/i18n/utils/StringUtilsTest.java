package com.jukusoft.i18n.utils;

import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void testConstructor () {
        new StringUtils();
    }

    @Test (expected = NullPointerException.class)
    public void testNullString () {
        StringUtils.requireNotEmpty(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testEmptyString () {
        StringUtils.requireNotEmpty("");
    }

    @Test
    public void testValideString () {
        StringUtils.requireNotEmpty("test");
    }

}
