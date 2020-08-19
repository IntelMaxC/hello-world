/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intelsoft.jfind;

/**
 *
 * @author Massimo Intelisano
 */
public class FinderEnv {
    
    private String dir;
    private boolean recursive;
    private String [] validArchive;
    
    private boolean pathToFind;
    private String pathMatch;
    private String pathMatchRegEx;
    private boolean pathMid;
    
    private boolean textToFind;
    private String textMatch;
    private String textFiles;
    private String [] fileExtension;
    private boolean textPrintLine;
    private String textLineFound;
    private boolean textIgnoreCase;
    
    private void genPathMatchRegEx(){
        
        if (this.pathMatch != null && this.pathMatch.trim().length() > 0){
            
            this.pathToFind = true;
            this.pathMatchRegEx = "(.*)" + this.pathMatch.replace(".", "/");
            
            if (this.pathMid){
                this.pathMatchRegEx += "(.*)";
            }
            
        }else{
            this.pathToFind = false;
        }
        
    }
    
    private void genTextFind(){
        
        if (this.textMatch != null && this.textMatch.trim().length() > 0){
            
            this.textToFind = true;
            this.fileExtension = this.textFiles.split(",");
            
        }else{
            this.textToFind = false;
        }
        
    }
    
    public FinderEnv() {
        this.recursive = true;
        this.pathMid = true;
        this.pathToFind = false;
        this.textToFind = false;
        this.textFiles = "properties,xml";
        this.textPrintLine = true;
        this.textIgnoreCase = false;
        this.validArchive = new String[] {"jar", "ear", "esb", "war"};
        this.dir = ".";
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
    
    public String getDir() {
        return dir;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }
    
    public boolean isRecursive() {
        return recursive;
    }
    
    public boolean isPathToFind() {
        return pathToFind;
    }

    public boolean isTextToFind() {
        return textToFind;
    }

    public void setPathMatch(String pathMatch) {
        this.pathMatch = pathMatch;
        genPathMatchRegEx();
    }

    public String getPathMatch() {
        return pathMatch;
    }

    public String getPathMatchRegEx() {
        return pathMatchRegEx;
    }

    public void setPathMid(boolean pathMid) {
        this.pathMid = pathMid;
        genPathMatchRegEx();
    }
    
    public boolean isPathMid() {
        return pathMid;
    }

    public void setTextMatch(String textMatch) {
        this.textMatch = textMatch;
        genTextFind();
    }

    public String getTextMatch() {
        return textMatch;
    }
    
    public void setTextFiles(String textFiles) {
        if (textFiles != null && textFiles.trim().length() > 0){
            this.textFiles = textFiles;
            genTextFind();
        }
    }

    public String[] getFileExtension() {
        return fileExtension;
    }
    
    public boolean isExtensionToLook(String fname){
        
        for (String ext : fileExtension){
            
            if (ext.startsWith("!")){
                
                String realExt = "." + ext.substring(1).toLowerCase();
                
                if (!(fname.toLowerCase().endsWith(realExt))){
                    return true;
                }
                
            }else{
                
                String realExt = "." + ext.toLowerCase();
                
                if (fname.toLowerCase().endsWith(realExt)){
                    return true;
                }
                
            }
            
        }
        
        return false;
        
    }

    public boolean isTextPrintLine() {
        return textPrintLine;
    }

    public void setTextPrintLine(boolean printLine) {
        this.textPrintLine = printLine;
    }
    
    public void setTextLineFound(String lineFound) {
        this.textLineFound = lineFound.trim();
    }

    public String getTextLineFound() {
        return textLineFound;
    }

    public boolean isTextIgnoreCase() {
        return textIgnoreCase;
    }
    
    public void setTextIgnoreCase(boolean textIgnoreCase) {
        this.textIgnoreCase = textIgnoreCase;
    }
    
    public void setValidArchives(String archiveExtensions){
        
        if (archiveExtensions != null && archiveExtensions.trim().length() > 0){
            this.validArchive = archiveExtensions.split(",");
        }
        
    }
    
    public boolean isValidArchive(String fname){
        
        for (String archive : validArchive){
            
            if (fname.endsWith("." + archive)){
                return true;
            }
            
        }
        
        return false;
        
    }
    
}
