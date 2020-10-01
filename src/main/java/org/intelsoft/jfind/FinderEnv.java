/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intelsoft.jfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Massimo Intelisano
 */
public class FinderEnv {
    
    private String dir;
    private boolean recursive;
    private List<String> validArchive;
    private boolean verbose;
    
    private boolean pathToFind;
    private String pathMatch;
    private String pathMatchRegEx;
    private boolean pathMid;
    
    private boolean textToFind;
    private String textMatch;
    private String textFiles;
    private List<String> fileExtension;
    private boolean textPrintLine;
    private String textLineFound;
    private boolean textIgnoreCase;
    
    private void genPathMatchRegEx(){
        
        if (this.pathMatch != null && this.pathMatch.trim().length() > 0){
            
            this.pathToFind = true;
            this.pathMatchRegEx = "(.*)" + this.pathMatch;
            
            if (this.pathMid){
                this.pathMatchRegEx += "(.*)";
            }
            
        }else{
            this.pathToFind = false;
        }
        
    }
    
    private void genTextFind(){
        
        if (this.textMatch != null && this.textMatch.trim().length() > 0){
            
            if (this.textFiles.startsWith("+")){
                
                this.textFiles = this.textFiles.substring(1);
                
                List<String> toAdd = Arrays.asList(this.textFiles.split(","));
                this.fileExtension.addAll(toAdd);
                
            }else{
                this.fileExtension = Arrays.asList(this.textFiles.split(","));
            }
            
            this.textToFind = true;
            
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
        this.validArchive = new ArrayList<>(Arrays.asList(new String []{"jar", "ear", "esb", "war", "sar", "zip"}));
        this.dir = ".";
        this.verbose = false;
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

    public String getTextFiles() {
        return textFiles;
    }
    
    public List<String> getFileExtension() {
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
            
            if (archiveExtensions.startsWith("+")){
                
                List<String> toAdd = Arrays.asList(archiveExtensions.substring(1).split(","));
                this.validArchive.addAll(toAdd);
                
            }else{
                this.validArchive = Arrays.asList(archiveExtensions.split(","));
            }
            
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

    public String getValidArchives() {
        
        String s = "";
        
        for (String a : validArchive){
            s += (a + ",");
        }
        
        return s.substring(0, s.length() - 1);
        
    }
    
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isVerbose() {
        return verbose;
    }
    
}
