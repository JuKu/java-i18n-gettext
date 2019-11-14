package com.jukusoft.i18n.loader;

import java.io.File;
import java.util.Locale;

public interface ILoader {

    /**
    * load complete domain into RAM memory
     *
     * @param langFolder i18n folder which contains all the multi-language files
     * @param domain domain
     * @param locale locale to load
     *
     * @throws NoLangDomainFoundException if no i18n file for this domain and locale was found
     *
     * @return domain bundle with all i18n values which belongs to this domain
    */
    public DomainBundle load (File langFolder, String domain, Locale locale) throws NoLangDomainFoundException;

}
