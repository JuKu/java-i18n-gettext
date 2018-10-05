package com.jukusoft.i18n.loader;

import java.io.File;
import java.util.Locale;

public interface ILoader {

    /**
    * load complete domain into RAM memory
    */
    public DomainBundle load (File langFolder, String domain, Locale locale) throws NoLangDomainFoundException;

}
