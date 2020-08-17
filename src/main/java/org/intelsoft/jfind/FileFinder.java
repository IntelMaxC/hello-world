/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intelsoft.jfind;

import java.io.IOException;
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
    
    private void analizeFile(Path path){
        
        String spath = path.toString();
        
        OverridePrinter.getInstance().print("scanning " + path.getFileName().toString());
        
        if (env.isValidArchive(spath)){
            JarFinder.scan("./" + path.getFileName().toString(), spath, env);
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
            
            OverridePrinter.getInstance().print("scan completed");
            
        } catch (IOException ex) {
            Logger.getLogger(FileFinder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
