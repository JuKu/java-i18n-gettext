package com.jukusoft.i18n;

import org.junit.Test;

import java.io.File;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class ITest {

    @Test
    public void testConstructor () {
        new I();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInitNotExistentDir () {
        I.init(new File("../test/"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInitFile () {
        I.init(new File("../testdata/test.txt"));
    }

    @Test
    public void testInit () {
        I.init(new File("../testdata/po/"));
    }

    @Test
    public void testTrWithoutTranslation () {
        I.init(new File("../testdata/po/"), Locale.ENGLISH, "messages");

        String[] testStrings = new String[]{
                "test",
                "test 1",
                "1234",
                "my test string",
                "my-test-string",
                "my_test_string"
        };

        for (String str : testStrings) {
            assertEquals(str, I.tr(str));
        }
    }

}
