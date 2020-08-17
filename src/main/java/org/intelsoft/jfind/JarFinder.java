/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intelsoft.jfind;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author mintelisano
 */
public class JarFinder {

    private static InputStream extractFromArchive(ZipInputStream zis) {

        try {
            
            //FileOutputStream fos = new FileOutputStream("ggg.jar");
            
            byte[] buffer = new byte[2048];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            int len = 0;
            while ((len = zis.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
                //fos.write(buffer, 0, len);
            }
            
            byte[] bytes = baos.toByteArray();
            InputStream isret = new ByteArrayInputStream(bytes);
            
            //fos.close();
            
            return isret;
            
        } catch (IOException ex) {
            Logger.getLogger(JarFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;

    }
    
    private static boolean findText(ZipInputStream zis, FinderEnv env){
        
        String text = env.getTextMatch().toLowerCase();
        BufferedReader br = new BufferedReader(new InputStreamReader(zis));
        
        try{
        
            String line;
            while ((line = br.readLine()) != null){

                if (line.toLowerCase().contains(text)){

                    if (env.isTextPrintLine()){
                        env.setTextLineFound(line);
                    }

                    return true;

                }

            }
            
        }catch (IOException ex){
            Logger.getLogger(JarFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
        
    }
    
    private static boolean findBytes(ZipInputStream zis, FinderEnv env){
        
        byte [] text = env.getTextMatch().getBytes();
        
        String line = "";
        
        int k = 0;
        boolean found = false;
        
        try {
            
            int c;
            while ((c = zis.read()) > -1) {
                
                char cc = (char) c;
                
                if (env.isTextPrintLine()){
                    
                    if (cc == '\n') {
                        
                        if (found) {
                            env.setTextLineFound(line);
                            return true;
                        }
                        
                        line = "";
                        
                    } else {
                        line += cc;
                    }
                    
                }
                
                if ((k < text.length) && (text[k] == cc)){
                    k++;
                }else{
                    k = 0;
                }
                
                if (k == text.length){
                    
                    if (env.isTextPrintLine()){
                        found = true;
                    }else{
                        return true;
                    }
                    
                }
                
            }
            
        }catch (IOException ex){
            Logger.getLogger(JarFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (env.isTextPrintLine() && found){
            env.setTextLineFound(line);
        }
        
        return found;
        
    }

    private static void scanIS(String root, InputStream is, FinderEnv env) {
        
        try {

            ZipEntry ze;
            ZipInputStream zis = new ZipInputStream(is);

            while ((ze = zis.getNextEntry()) != null) {
                
                if (!ze.isDirectory()) {
                    
                    OverridePrinter.getInstance().print("scanning " + root + "/" +ze.getName());
                    
                    String entryName = ze.getName();
                    
                    if (env.isTextToFind()){
                        
                        if (env.isExtensionToLook(entryName)){
                            
                            boolean found;
                            
                            if (env.isTextIgnoreCase()){
                                found = findText(zis, env);
                            }else{
                                found = findBytes(zis, env);
                            }
                            
                            
                            if (found){
                                OverridePrinter.getInstance().println(" => TEXT: " + root + "/" + entryName);
                                if (env.isTextPrintLine()){
                                    OverridePrinter.getInstance().println("\n          ...\n          " + env.getTextLineFound() + "\n          ...\n");
                                }
                            }
                            
                        }
                        
                    }
                    
                    if (env.isPathToFind()){
                        
                        if (entryName.matches(env.getPathMatchRegEx())){
                            OverridePrinter.getInstance().println(" => PATH: " + root + "/" + entryName);
                        }
                        
                    }
                    
                    if (env.isValidArchive(entryName)) {
                        
                        if (env.isRecursive()){
                            
                            InputStream isFile = extractFromArchive(zis);
                            scanIS(root + "/" + entryName, isFile, env);

                            if (isFile != null){
                                isFile.close();
                            }
                            
                        }
                        
                    }
                    
                }
                
            }

        } catch (IOException ex) {
            Logger.getLogger(JarFinder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void scan(String root, String path, FinderEnv env) {

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            scanIS(root, fis, env);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JarFinder.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                Logger.getLogger(JarFinder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
}
