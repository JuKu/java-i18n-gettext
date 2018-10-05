package com.jukusoft.i18n.loader;

import com.jukusoft.i18n.DomainBundle;

import java.io.File;
import java.util.Locale;

public interface Loader {

    /**
    * load complete domain into RAM memory
    */
    public DomainBundle load (File langFolder, String domain, Locale locale);

}
