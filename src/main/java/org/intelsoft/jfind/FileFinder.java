/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intelsoft.jfind;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Massimo Intelisano
 */
public class FileFinder {

    private final FinderEnv env;

    public FileFinder(FinderEnv env) {
        this.env = env;
    }

    private void analizeFile(Path path) {

        String spath = path.toString();

        if (env.isVerbose()) {
            OverridePrinter.getInstance().print("scanning " + path.getFileName().toString());
        }

        if (env.isValidArchive(spath)) {
            JarFinder.scan(spath, env);
        } else {

            InputStream fis = null;

            try {

                fis = new FileInputStream(spath);
                JarFinder.analyzeEntry("", spath, fis, env);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileFinder.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

                try {
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(JarFinder.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

    public void scan() {

        try {

            Path rootDir = Paths.get(env.getDir());

            Files.find(
                    rootDir,
                    Integer.MAX_VALUE,
                    (p, basicFileAttributes) -> {
                        return basicFileAttributes.isRegularFile();
                    }).forEach((p) -> analizeFile(p));

            if (env.isVerbose()) {
                OverridePrinter.getInstance().print("scan completed");
            }

        } catch (IOException ex) {
            Logger.getLogger(FileFinder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
