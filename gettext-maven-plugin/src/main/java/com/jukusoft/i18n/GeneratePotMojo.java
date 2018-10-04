package com.jukusoft.i18n;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

@Mojo( name = "generatepot", threadSafe = true )
public class GeneratePotMojo extends AbstractMojo {

    /**
    * source directory
    */
    @Parameter( property = "generatepot.src", defaultValue = "src/" )
    private String srcDir;

    /**
     * pot file output directory
     */
    @Parameter( property = "generatepot.outputDir", defaultValue = "lang/" )
    private String outputDir;

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

        //check, if output directory exists
        if (!new File(outputDir).exists()) {
            //create output directory
            getLog().info("generate pot outputDir '" + new File(outputDir).getAbsolutePath() + "' doesn't exists, create directory now.");

            new File(outputDir).mkdirs();
        }

        System.out.println("hello!");
    }

}
