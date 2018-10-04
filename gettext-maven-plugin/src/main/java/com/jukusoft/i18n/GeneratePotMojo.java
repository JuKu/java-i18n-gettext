package com.jukusoft.i18n;

import com.jukusoft.i18n.utils.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Mojo( name = "generatepot", threadSafe = true )
public class GeneratePotMojo extends AbstractMojo {

    /**
    * source directory
    */
    @Parameter( property = "generatepot.src", defaultValue = "${project.build.sourceDirectory}" )
    private String srcDir;

    /**
     * pot file output directory
     */
    @Parameter( property = "generatepot.outputDir", defaultValue = "lang/" )
    private String outputDir;

    /**
    * default language token
    */
    @Parameter( property = "generatepot.defaultLang", defaultValue = "en" )
    private String defaultLang;

    /**
    * default gettext domain
    */
    @Parameter( property = "generatepot.defaultDomain", defaultValue = "default" )
    private String defaultDomain;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        //check, if source directory exists
        if (!new File(srcDir).exists()) {
            throw new MojoFailureException("generatepot source directory '" + new File(srcDir).getAbsolutePath() + "' doesnt exists!");
        }

        //check, if it is a directory
        if (!new File(srcDir).isDirectory()) {
            throw new MojoFailureException("generatepot srcDir '" + new File(srcDir).getAbsolutePath() + "' isn't a directory!");
        }

        if (!outputDir.endsWith("/")) {
            throw new MojoFailureException("configuration outputDir has to end with '/'! Current value: '" + outputDir + "'.");
        }

        //check, if output directory exists
        if (!new File(outputDir).exists()) {
            //create output directory
            getLog().info("generate pot outputDir '" + new File(outputDir).getAbsolutePath() + "' doesn't exists, create directory now.");

            new File(outputDir).mkdirs();
        }

        //check, if language token is valide
        if (!isValidLocale(new Locale(defaultLang))) {
            throw new MojoFailureException("default language isn't a valide language token! Here you can check, if token is valide: http://schneegans.de/lv/");
        }

        String langDir = outputDir + defaultLang + "/";

        //create language directory, if not exists
        if (!new File(langDir).exists()) {
            //create directory
            new File(langDir).mkdirs();
        }

        if (!new File(langDir).isDirectory()) {
            throw new MojoFailureException("langDir isn't a directory: " + new File(langDir).getAbsolutePath());
        }

        getLog().info("Analyze source directory '" + Paths.get(srcDir) + "' now...");

        if (new File(srcDir).listFiles().length == 0) {
            throw new MojoFailureException("source directory '" + new File(srcDir).getAbsolutePath() + "' is empty!");
        }

        getLog().info("generatepot source directory: " + new File(srcDir).getAbsolutePath());

        Map<String,Set<String>> entriesMap = new HashMap<>();

        try {
            //Files.walk(Paths.get(srcDir));
                    //.filter(Files::isRegularFile);
                    //.forEach(System.out::println);

            Files.find(Paths.get(srcDir),
                    Integer.MAX_VALUE,
                    (filePath, fileAttr) -> fileAttr.isRegularFile())
                    .forEach((path) -> {
                        try {
                            analyzeFile(path, defaultDomain, entriesMap);
                        } catch (MojoFailureException e) {
                            throw new IllegalStateException("Error while analyzing file '" + path.toFile().getAbsolutePath() + "'!");
                        }
                    });
        } catch (IOException e) {
            getLog().error("IOException while find files");
            getLog().error(e);
            throw new MojoFailureException(e.getLocalizedMessage());
        } catch (Exception e) {
            getLog().error("Exception while find files");
            getLog().error(e);
            throw new MojoFailureException(e.getLocalizedMessage());
        }

        getLog().info("" + entriesMap.keySet().size() + " different domains found in files:");

        for (String domain : entriesMap.keySet()) {
            getLog().info("Domain: " + domain);
        }

        //parse source files

        System.out.println("hello!");
    }

    private boolean isValidLocale (Locale locale) {
        try {
            return locale.getISO3Language() != null && locale.getISO3Country() != null;
        } catch (MissingResourceException e) {
            return false;
        }
    }

    protected void analyzeFile (Path path, String defaultDomain, Map<String,Set<String>> entriesMap) throws MojoFailureException {
        FileAnalyzer.analyzeFile(path.toFile(), getLog(), defaultDomain, entriesMap);
    }

}
