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
public class Main {
    
    private static void printHelp(){
        
        System.out.println(
                "jfind [OPTIONS...]\n" +
                "jfind [path to find] (default --path.match)\n" +
                "--dir [root path to start search]\n" +
                    "\tdefault value = \".\"\n" +
                "--scan.recursive [true, false]\n" +
                    "\ttrue = scan recursively inside sub folders and archives\n" +
                    "\tfalse = flat scan\n" +
                    "\tdefault value = true\n" +
                "--scan.archives [comma separated list of archives extension to look inside]\n" +
                    "\tis possible to set all valid zip archives\n" +
                    "\tis possible to add extensions to the default with \"+ext1,ext2,...\"\n" +
                    "\tdefault value = \"jar,war,ear,esb,sar,zip\"\n" +
                "--path [path to search]\n" +
                    "\tif empty the search by path is disabled\n" +
                "--path.mid [true, false]\n" +
                    "\ttrue = returns all files contain the specified path\n" +
                    "\tfalse = returns all files the name ends exactly with the specified path\n" +
                    "\tdefalut value = true\n" +
                "--text [text content to search]\n" +
                    "\tif empty the search by content is disabled\n" +
                "--text.files [comma separated list of file extensions to search text]\n" +
                    "\tis possible to use \"!extension\" to exclude files from scan\n" +
                    "\tis possible to add extensions to the default with \"+ext1,ext2,...\"\n" +
                    "\tdefault value = \"properties,xml\"\n" +
                "--text.ignorecase [true, false]\n" +
                    "\ttrue = match text without consider the case of characters (slow)\n" +
                    "\tfalse = match text with a byte per byte comparison (fast)\n" +
                    "\tdefault value = false\n" +
                "--text.printfound [true, false]\n" +
                    "\ttrue = print line where text is found\n" +
                    "\tfalse = print only the files where text is found\n" +
                    "\tdefault value = true\n" +
                "--verbose [true, false]" +
                    "\ttrue = current file scanned\n" +
                    "\tfalse = silent scan\n" +
                    "\tdefault value = false\n" +
                "--help or -h to display help");
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        FinderEnv env = new FinderEnv();
        
        if (args.length == 1){
            env.setPathMatch(args[0]);
        }
        
        for (int i = 0; i < args.length; i++){

            if (args[i].equals("--dir") && i < args.length - 1){
                env.setDir(args[i + 1]);
            }else if (args[i].equals("--path") && i < args.length - 1){
                env.setPathMatch(args[i + 1]);
            }else if (args[i].equals("--path.mid")){
                env.setPathMid(Boolean.valueOf(args[i + 1]));
            }else if (args[i].equals("--scan.recursive")){
                env.setRecursive(Boolean.valueOf(args[i + 1]));
            }else if (args[i].equals("--scan.archives")){
                env.setValidArchives(args[i + 1]);
            }else if (args[i].equals("--text") && i < args.length - 1){
                env.setTextMatch(args[i + 1]);
            }else if (args[i].equals("--text.files") && i < args.length - 1){
                env.setTextFiles(args[i + 1]);
            }else if (args[i].equals("--text.printfound") && i < args.length - 1){
                env.setTextPrintLine(Boolean.valueOf(args[i + 1]));
            }else if (args[i].equals("--text.ignorecase") && i < args.length - 1){
                env.setTextIgnoreCase(Boolean.valueOf(args[i + 1]));
            }else if (args[i].equals("--verbose") && i < args.length - 1){
                env.setVerbose(Boolean.valueOf(args[i + 1]));
            }else if (args[i].equals("--help") || args[i].equals("-h")){
                printHelp();
                return;
            }
            
        }

        if (env.isPathToFind() || env.isTextToFind()){
            
            OverridePrinter.getInstance().println("scan archives = " + env.getValidArchives());
            
            if (env.isPathToFind()){
                OverridePrinter.getInstance().println("looking for path = " + env.getPathMatch());
            }
            
            if (env.isTextToFind()){
                OverridePrinter.getInstance().println("looking for content = " + env.getTextMatch());
                OverridePrinter.getInstance().println("scan files = " + env.getTextFiles());
            }
            
            FileFinder finder = new FileFinder(env);
            finder.scan();

        }else{
            printHelp();
        }
        
    }
    
}
