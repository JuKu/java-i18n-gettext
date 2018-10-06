package com.jukusoft.i18n.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class IsoUtils {

    private static final Set<String> ISO_LANGUAGES = new HashSet<>
            (Arrays.asList(Locale.getISOLanguages()));

    private static final Set<String> ISO_COUNTRIES = new HashSet<>
            (Arrays.asList(Locale.getISOCountries()));

    protected IsoUtils() {

    }

    /**
    * check, if language token is valide
     *
     * @param s language token, for example "en"
     *
     * @see <a href="https://stackoverflow.com/questions/15921332/cleaner-way-to-check-if-a-string-is-iso-country-of-iso-language-in-java">Stackoverflow</a>
     *
     * @return true, if token is valid
    */
    public static boolean isValidISOLanguage(String s) {
        return ISO_LANGUAGES.contains(s);
    }

    public static boolean isValidISOCountry(String s) {
        return ISO_COUNTRIES.contains(s);
    }

}
