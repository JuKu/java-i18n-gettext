package com.jukusoft.i18n;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PotWriter {

    protected PotWriter () {
        //
    }

    public static void write (File file, Log log, Map<String,String> headerMap, List<PotEntry> list) throws MojoFailureException {
        if (!file.getName().endsWith(".pot")) {
            throw new IllegalArgumentException("file name has to end with .pot extension, current file path: " + file.getAbsolutePath());
        }

        //delete file, if exists
        if (file.exists()) {
            file.delete();
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            log.error("Cannot create .pot file: " + file.getAbsolutePath());
            log.error(e);
        }

        //put default values
        headerMap.putIfAbsent("title", "My Example Project title");
        headerMap.putIfAbsent("copyright", "Copyright (C) YEAR THE PACKAGE'S COPYRIGHT HOLDER");
        headerMap.putIfAbsent("license", "This file is distributed under the same license as the PACKAGE package.");
        headerMap.putIfAbsent("author", "FIRST AUTHOR <EMAIL@ADDRESS>, YEAR.");
        headerMap.putIfAbsent("version", "Version 1.0.0");
        headerMap.putIfAbsent("bugReportUrl", "");
        headerMap.putIfAbsent("langTeam", "");
        headerMap.putIfAbsent("defaultLang", "en");
        headerMap.putIfAbsent("charset", "UTF-8");

        try (FileWriter writer = new FileWriter(file)) {
            //first, writer header
            writer.write("# " + headerMap.get("title") + "\n");
            writer.write("#\n");
            writer.write("# Attention! Don't edit this file manually! This file is auto generated and will be overriden!\n");
            writer.write("#\n");
            writer.write("# " + headerMap.get("copyright") + "\n");
            writer.write("# " + headerMap.get("license") + "\n");
            writer.write("# " + headerMap.get("author") + "\n");
            writer.write("#\n");
            writer.write("#, fuzzy\n");
            writer.write("msgid \"\"\n");
            writer.write("msgstr \"\"\n");
            writer.write("\"Project-Id-Version: PACKAGE VERSION\\n\"\n");

            if (!headerMap.get("bugReportUrl").isEmpty()) {
                writer.write("\"Report-Msgid-Bugs-To: " + headerMap.get("bugReportUrl") + "\\n\"\n");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dyyyy-MM-dd HH:mm:SS");

            writer.write("\"POT-Creation-Date: " + sdf.format(new Date()) + "+0100\\n\"\n");
            writer.write("\"PO-Revision-Date: YEAR-MO-DA HO:MI+ZONE\\n\"\n");
            writer.write("\"Last-Translator: Auto generated file\\n\"\n");

            if (!headerMap.get("langTeam").isEmpty()) {
                writer.write("\"Language-Team: " + headerMap.get("langTeam") + "\\n\"\n");
            }

            writer.write("\"Language: " + headerMap.get("defaultLang") + "\\n\"\n");
            writer.write("\"MIME-Version: 1.0\\n\"\n");
            writer.write("\"Content-Type: text/plain; charset=" + headerMap.get("charset") + "\\n\"\n");
            writer.write("\"Content-Transfer-Encoding: 8bit\\n\"\n");
            writer.flush();

            for (PotEntry entry : list) {
                writer.write("\n");

                //comments (files and lines)
                for (String fileStr : entry.listOccurrences()) {
                    writer.write("#: file " + fileStr + "\n");
                }

                writer.write("msgid \"" + entry.getMsgId() + "\"\n");

                if (entry.hasPlural()) {
                    writer.write("msgid_plural \"" + entry.getPluralMsgId() + "\"\n");

                    writer.write("msgstr[0] \"" + entry.getMsgId() + "\"\n");
                    writer.write("msgstr[1] \"" + entry.getPluralMsgId() + "\"\n");
                } else {
                    writer.write("msgstr \"" + entry.getMsgId() + "\"\n");
                }
            }

            writer.flush();
        } catch (IOException e) {
            log.error(e);
        }
    }

}
