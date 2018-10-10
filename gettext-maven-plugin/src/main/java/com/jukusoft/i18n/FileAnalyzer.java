package com.jukusoft.i18n;

import com.jukusoft.i18n.utils.FileUtils;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileAnalyzer {

    protected static final String[] methodNames = new String[]{
            "tr", "ntr"
    };

    protected FileAnalyzer () {
        //
    }
    
    public static void analyzeFile (File file, Log log, String defaultDomain, Map<String,List<PotEntry>> entriesMap) throws MojoFailureException {
        log.debug("analyze file: " + file.getAbsolutePath());

        //dont analyze class I itself
        if (file.getAbsolutePath().endsWith("I.java")) {
            log.debug("skip class I.java: " + file.getAbsolutePath());
            return;
        }

        //search for string literals
        String content = null;

        try {
            content = FileUtils.readFile(file.getAbsolutePath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(e);
            throw new MojoFailureException("Coulnd't analyze java file '" + file.getAbsolutePath() + "', caused by an IOException", e);
        }

        //check, if class I is used
        if (!content.contains("I.")) {
            log.debug("skip file '" + file.getAbsolutePath() + "', because this class doesn't uses class I.");
            return;
        }

        log.debug("java file '" + file.getAbsolutePath() + "' contains class I.");

        //iterate through lines
        String[] lines = content.split(System.lineSeparator());

        int lineNumber = 0;

        for (String line : lines) {
            lineNumber++;

            while (true) {
                if (line.contains("I.")) {
                    char[] c = line.toCharArray();

                    int pos = line.indexOf("I.");
                    int pos1 = 0;

                    int argumentsPos = 0;

                    //check character before I.
                    if (c[pos - 1] == ' ' || c[pos - 1] == '(') {
                        //its the right class
                        StringBuilder sb = new StringBuilder();
                        String methodName = null;

                        //iterate through '(' is found
                        for (int i = pos + 2; i < c.length; i++) {
                            if (c[i] == '(') {
                                //end of method name
                                methodName = sb.toString();

                                argumentsPos = i + 1;

                                break;
                            }

                            sb.append(c[i]);
                        }

                        if (!isAllowedMethod(methodName)) {
                            //its a method like I.init()
                            continue;
                        }

                        StringBuilder sb1 = new StringBuilder();

                        //get arguments
                        for (int i = argumentsPos; i < line.length(); i++) {
                            if (c[i] == ')') {
                                pos1 = i;
                                break;
                            }

                            sb1.append(c[i]);
                        }

                        //get string and remove quotes
                        String argumentsStr = sb1.toString().replace("\"", "");

                        //split arguments in single arguments
                        String[] arguments = argumentsStr.split(", ");

                        String domainName = defaultDomain;
                        String msgId = "";
                        String msgId1 = "";

                        switch (methodName) {
                            case "tr":
                                //requires 1 or 2 arguments
                                if (arguments.length == 1) {
                                    //its only the msgId
                                    msgId = arguments[0];
                                } else if (arguments.length == 2) {
                                    //its the domain name and the msgId
                                    msgId = arguments[1];
                                    domainName = arguments[0];
                                } else {
                                    log.warn("Unexpected argument in file " + file.getAbsolutePath() + " in method I." + methodName + "()!");
                                }

                                break;

                            case "ntr":
                                //requires 3 arguments
                                if (arguments.length < 3 || arguments.length > 4) {
                                    log.warn("Unexpected argument list, 3 or 4 arguments required in file " + file.getAbsolutePath() + " in method I." + methodName + "()! Line: " + line);
                                }

                                if (arguments.length == 3) {
                                    msgId = arguments[0];
                                    msgId1 = arguments[1];
                                } else {
                                    //4 arguments, with domain name
                                    domainName = arguments[0];
                                    msgId = arguments[1];
                                    msgId1 = arguments[2];
                                }

                                break;

                            default:
                                throw new MojoFailureException("Invalide method name of class I: " + methodName);
                        }

                        if (!entriesMap.containsKey(domainName)) {
                            entriesMap.put(domainName, new ArrayList<>());
                        }

                        //get domain list
                        List<PotEntry> entries = entriesMap.get(domainName);

                        PotEntry entry = new PotEntry(domainName, msgId, msgId1);

                        if (entries.contains(entry)) {
                            entry = entries.get(entries.indexOf(entry));
                        } else {
                            entries.add(entry);
                        }

                        entry.addFile(file.getName(), lineNumber);
                    } else {
                        //its another class, which ends with "I.", like "MyExampleClassI."
                        continue;
                    }

                    line = line.substring(pos1 + 1);
                } else {
                    //line doesn't use class I
                    break;
                }
            }
        }
    }

    protected static boolean isAllowedMethod (String methodName) {
        Objects.requireNonNull(methodName);

        for (String str : methodNames) {
            if (methodName.equals(str)) {
                return true;
            }
        }

        return false;
    }
        
}
