package com.jukusoft.i18n.loader;

import com.jukusoft.i18n.utils.StringUtils;

import java.io.File;
import java.util.Locale;
import java.util.Objects;

public class PoILoader implements ILoader {

    protected static final String FILE_SLASH = File.separator;

    @Override
    public DomainBundle load(File langFolder, String domain, Locale locale) throws NoLangDomainFoundException {
        Objects.requireNonNull(langFolder);
        Objects.requireNonNull(locale);

        StringUtils.requireNotEmpty(domain);

        String poFilePath = langFolder.getAbsolutePath() + FILE_SLASH + locale.getLanguage() + FILE_SLASH + domain + ".po";

        if (!new File(poFilePath).exists()) {
            throw new NoLangDomainFoundException("Cannot found .po file for domain '" + domain + "' in language '" + locale.getLanguage() + "'! Search path: " + poFilePath);
        }

        //TODO: load PO file

        return new DomainBundle();
    }

}
