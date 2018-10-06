package com.jukusoft.i18n.loader;

import com.jukusoft.i18n.utils.StringUtils;

import java.io.*;
import java.util.Locale;
import java.util.Objects;

public class PoILoader implements ILoader {

    protected static final String FILE_SLASH = File.separator;

    @Override
    public DomainBundle load(File langFolder, String domain, Locale locale) throws NoLangDomainFoundException {
        Objects.requireNonNull(langFolder);
        Objects.requireNonNull(locale);

        StringUtils.requireNotEmpty(domain);

        String poFilePath = langFolder.getAbsolutePath() + FILE_SLASH + locale.getLanguage() + (locale.getCountry().isEmpty() ? "" : "_" + locale.getCountry()) + FILE_SLASH + domain + ".po";

        if (!new File(poFilePath).exists()) {
            throw new NoLangDomainFoundException("Cannot found .po file for domain '" + domain + "' in language '" + locale.getLanguage() + (locale.getCountry().isEmpty() ? "" : "_" + locale.getCountry()) + "'! Search path: " + poFilePath);
        }

        DomainBundle bundle = new DomainBundle();

        String msgId = "";
        String pluralMsgId = "";
        String msgIdValue = "";
        String pluralMsgIdValue = "";

        //load PO file
        try (BufferedReader br = new BufferedReader(new FileReader(new File(poFilePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.

                //ignore first 2 lines
                if (line.contains("msgid \"\"") || line.contains("msgstr \"\"")) {
                    continue;
                }

                //ignore comments and header
                if (line.startsWith("#") || line.startsWith("\"")) {
                    continue;
                }

                if (line.isEmpty()) {
                    //save singular translation
                    if (!msgId.isEmpty() && !msgIdValue.isEmpty()) {
                        bundle.addTranslation(msgId, msgIdValue);
                    }

                    //save plural translation
                    if (!pluralMsgId.isEmpty() && !pluralMsgIdValue.isEmpty()) {
                        bundle.addTranslation(pluralMsgId, pluralMsgIdValue);
                    }

                    //reset strings
                    msgId = "";
                    msgIdValue = "";
                    pluralMsgId = "";
                    pluralMsgIdValue = "";
                } else if (line.startsWith("msgid ")) {
                    //singular key
                    msgId = line.replace("msgid ", "").replace("\"", "");
                } else if (line.startsWith("msgid_plural ")) {
                    //plural key
                    pluralMsgId = line.replace("msgid_plural ", "").replace("\"", "");
                } else if (line.startsWith("msgstr ") || line.startsWith("msgstr[0] ")) {
                    //singular value
                    msgIdValue = line.replace("msgstr ", "").replace("msgstr[0] ", "").replace("\"", "");
                } else if (line.startsWith("msgstr[1] ")) {
                    //plural value
                    pluralMsgIdValue = line.replace("msgstr[1] ", "").replace("\"", "");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new NoLangDomainFoundException("Cannot load .po file for domain '" + domain + "' in language '" + locale.getLanguage() + "'! Search path: " + poFilePath + ", exception: " + e.getLocalizedMessage());
        }

        return bundle;
    }

}
