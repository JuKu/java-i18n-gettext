package com.jukusoft.i18n;

import com.jukusoft.i18n.loader.NoLangDomainFoundException;
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

    @Test
    public void testSetLanguage () {
        I.setLanguage("de");
        I.setLanguage("en");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetLanguage1 () {
        I.setLanguage("german");
    }

    @Test
    public void testLoadDomain () throws NoLangDomainFoundException {
        I.init(new File("../testdata/po/"), Locale.ENGLISH, "messages");
        I.loadDomain("messages", Locale.ENGLISH);

        assertEquals("singular string", I.tr("sg1"));
        assertEquals("singular string", I.ntr("sg1", "pl1", 1));
        assertEquals("plural string", I.ntr("sg1", "pl1", 2));

        //unload domain
        I.unloadDomain("messages", Locale.ENGLISH);

        //should reload domain again, because it is used
        assertEquals("singular string", I.tr("sg1"));
        assertEquals("singular string", I.ntr("sg1", "pl1", 1));
        assertEquals("plural string", I.ntr("sg1", "pl1", 2));
    }

    @Test
    public void testLoadDomainNotExists () throws NoLangDomainFoundException {
        I.init(new File("../testdata/po/"), Locale.CANADA, "messages");

        //this line should't throw an exception, instead msgId should be returned
        //I.loadDomain("messages", Locale.CANADA);

        //should return msgId, because no translation for CANADA exists
        assertEquals("sg1", I.tr("sg1"));

        I.optimizeMemory(10000);

        //unload domain
        I.unloadDomain("messages", Locale.CANADA);
    }

    @Test
    public void testOptimizeMemory () throws NoLangDomainFoundException {
        I.init(new File("../testdata/po/"), Locale.ENGLISH, "messages");
        I.loadDomain("messages", Locale.ENGLISH);

        assertEquals(true, I.isDomainLoaded("messages", Locale.ENGLISH));

        //this line should remove the domain bundle
        I.optimizeMemory(0);

        assertEquals(false, I.isDomainLoaded("messages", Locale.ENGLISH));
    }

}
