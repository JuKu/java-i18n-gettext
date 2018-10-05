package com.jukusoft.i18n;

import com.jukusoft.i18n.loader.DomainBundle;
import com.jukusoft.i18n.loader.ILoader;
import com.jukusoft.i18n.loader.NoLangDomainFoundException;
import com.jukusoft.i18n.loader.PoILoader;
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

    //default domain
    protected static String defaultDomain = "";

    //map with domains
    protected static final Map<CacheKey,DomainBundle> cache = new ConcurrentHashMap<>(DEFAULT_EXPECTED_BUNDLES);

    protected static ILoader loader = new PoILoader();

    /**
    * private constructor, so noone can create an instance of this class
    */
    protected I () {
        //
    }

    public static void init (File langFolder) {
        init(langFolder, Locale.getDefault(), "messages");
    }

    public static void init (File langFolder, Locale defaultLang, String defaultDomain) {
        //check, if directory exists
        if (!langFolder.exists()) {
            throw new IllegalArgumentException("langFolder doesn't exists!");
        }

        if (!langFolder.isDirectory()) {
            throw new IllegalArgumentException("given i18n directory isn't a directory!");
        }

        I.langFolder = langFolder;
        setLanguage(defaultLang);
        I.defaultDomain = defaultDomain;
    }

    public static void setLanguage (String langToken) {
        StringUtils.requireNotEmpty(langToken, "language token");
        setLanguage(Locale.forLanguageTag(langToken));
    }

    public static void setLanguage (Locale locale) {
        I.locale = locale;
    }

    public static Locale getLanguage () {
        return I.locale;
    }

    public static void loadDomain (String domain, Locale locale) throws NoLangDomainFoundException {
        cache.put(new CacheKey(domain, locale), loader.load(langFolder, domain, locale));
    }

    public static void unloadDomain (String domain, Locale locale) {
        cache.remove(new CacheKey(domain, locale));
    }

    public static boolean isDomainLoaded (String domain, Locale locale) {
        return cache.containsKey(new CacheKey(domain, locale));
    }

    protected static void loadDomainIfNeccessary (String domain, Locale locale) throws NoLangDomainFoundException {
        if (!isDomainLoaded(domain, locale)) {
            //load domain
            loadDomain(domain, locale);
        }
    }

    /**
     * Translate a literal message  to the user's current language.
     *
     * @param msgId english-language message (also the default message, if there was no translation found)
     *
     * @return translated message or msg, if no translation was found
     */
    public static String tr (String msgId) {
        return tr(defaultDomain, msgId);
    }

    /**
     * Translate a literal message  to the user's current language.
     *
     * @param domainName domain name (filename without extension)
     * @param msgId english-language message (also the default message, if there was no translation found)
     *
     * @return translated message or msg, if no translation was found
     */
    public static String tr (String domainName, String msgId) {
        try {
            loadDomainIfNeccessary(domainName, getLanguage());
        } catch (NoLangDomainFoundException e) {
            System.err.println("Coulnd't find translations for domain '" + domainName + "' in language '" + getLanguage() + "'!");

            //create dummy domain bundle
            cache.put(new CacheKey(domainName, getLanguage()), new DomainBundle());
        }

        //get bundle
        DomainBundle bundle = cache.get(new CacheKey(domainName, getLanguage()));

        return bundle.tr(msgId);
    }

    /**
    * translate message with use of singular / plural (if n  &lt;= 1, singular is used, else plural is used)
     *
     * @param msgId english message in singular to translate
     * @param msgIdPlural english message in plural to translate
     * @param n number
     *
     * @return translated string
    */
    public static String ntr (String msgId, String msgIdPlural, long n) {
        return ntr(defaultDomain, msgId, msgIdPlural, n);
    }

    /**
     * translate message with use of singular / plural (if n  &lt;= 1, singular is used, else plural is used)
     *
     * @param domainName domain name
     * @param msgId english message in singular to translate
     * @param msgIdPlural english message in plural to translate
     * @param n number
     *
     * @return translated string
     */
    public static String ntr (String domainName, String msgId, String msgIdPlural, long n) {
        return (n > 1 ? tr(msgIdPlural) : tr(msgId));
    }

}
