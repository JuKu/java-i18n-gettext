package com.jukusoft.i18n;

import com.jukusoft.i18n.utils.StringUtils;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class I {

    protected static final int DEFAULT_EXPECTED_BUNDLES = 20;

    //folder, which contains .mo files
    protected static File langFolder = null;

    //current selected language
    protected static Locale locale = null;

    //map with domains
    protected static final Map<CacheKey,DomainBundle> cache = new ConcurrentHashMap<>(DEFAULT_EXPECTED_BUNDLES);

    /**
    * private constructor, so noone can create an instance of this class
    */
    protected I () {
        //
    }

    public static void init (File langFolder) {
        init(langFolder, Locale.getDefault());
    }

    public static void init (File langFolder, Locale defaultLang) {
        //check, if directory exists
        if (!langFolder.exists()) {
            throw new IllegalArgumentException("langFolder doesn't exists!");
        }

        if (!langFolder.isDirectory()) {
            throw new IllegalArgumentException("given i18n directory isn't a directory!");
        }

        I.langFolder = langFolder;
        setLanguage(defaultLang);
    }

    public static void setLanguage (String langToken) {
        StringUtils.requireNotEmpty(langToken, "language token");
        setLanguage(Locale.forLanguageTag(langToken));
    }

    public static void setLanguage (Locale locale) {
        I.locale = locale;
    }

    public static void loadDomain (String domain, Locale locale) {
        throw new UnsupportedOperationException("method isn't implemented yet.");
    }

    public static void unloadDomain (String domain, Locale locale) {
        throw new UnsupportedOperationException("method isn't implemented yet.");
    }

    public static boolean isDomainLoaded (String domain, Locale locale) {
        return cache.containsKey(new CacheKey(domain, locale));
    }

    /**
     * Translate a literal message  to the user's current language.
     *
     * @param msgId english-language message (also the default message, if there was no translation found)
     *
     * @return translated message or msg, if no translation was found
     */
    public static String tr (String msgId) {
        return msgId;
    }

    /**
     * Translate a literal message  to the user's current language.
     *
     *
     * @param msgId english-language message (also the default message, if there was no translation found)
     *
     * @return translated message or msg, if no translation was found
     */
    public static String tr (String domainName, String msgId) {
        return msgId;
    }

    /**
    * translate message with use of singular / plural
     *
     * @param n number, if n <= 1, singular is used, else plural is used
    */
    public static String ntr (String msgId, String msgIdPlural, long n) {
        return (n > 1 ? msgIdPlural : msgId);
    }

}
