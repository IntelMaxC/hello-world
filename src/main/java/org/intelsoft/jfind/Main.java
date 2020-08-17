/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intelsoft.jfind;

/**
 *
 * @author mintelisano
 */
public class Main {
    
    private static void printHelp(){
        
        System.out.println("\n**** jfind help ****\n");
        System.out.println("command: jfind [argument1 argument2 ....]\n");
        System.out.println("arguments:\n");
        System.out.println("--dir [root directory to start search]");
        System.out.println("--path.match [portion of path to match]");
        System.out.println("\t if empty the scan path is disabled");
        System.out.println("--path.mid [true, false]");
        System.out.println("\t true (default): path may not be completed");
        System.out.println("\t false: path must be completed to end of file name");
        System.out.println("--scan.recursive [true, false]");
        System.out.println("\t true (default): recursive scan inside subfolder and zip files");
        System.out.println("\t false: flat scan");
        System.out.println("--scan.archives [comma separated zip archives to find inside]");
        System.out.println("\t default: \"jar,war,ear,esb\"");
        System.out.println("--text.match [text to find inside the files]");
        System.out.println("\t if empty the scan text is disabled");
        System.out.println("--text.files [comma separated extensions to find the text]");
        System.out.println("\t default is \"properties,xml\"");
        System.out.println("\t it is possible use !extension to exclude files from scan");
        System.out.println("--text.printfound [true, false]");
        System.out.println("\t true (default): print the line where the text is matched");
        System.out.println("\t false: print only the file where the text is matched");
        System.out.println("--text.ignorecase [true, false]");
        System.out.println("\t true: search text in case not sensive mode (slower)");
        System.out.println("\t false (default): search the exact sequence of byte (faster)");
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        FinderEnv env = new FinderEnv();

        for (int i = 0; i < args.length; i++){

            if (args[i].equals("--dir") && i < args.length - 1){
                env.setDir(args[i + 1]);
            }else if (args[i].equals("--path.match") && i < args.length - 1){
                env.setPathMatch(args[i + 1]);
            }else if (args[i].equals("--path.mid")){
                env.setPathMid(Boolean.valueOf(args[i + 1]));
            }else if (args[i].equals("--scan.recursive")){
                env.setRecursive(Boolean.valueOf(args[i + 1]));
            }else if (args[i].equals("--scan.archives")){
                env.setValidArchives(args[i + 1]);
            }else if (args[i].equals("--text.match") && i < args.length - 1){
                env.setTextMatch(args[i + 1]);
            }else if (args[i].equals("--text.files") && i < args.length - 1){
                env.setTextFiles(args[i + 1]);
            }else if (args[i].equals("--text.printfound") && i < args.length - 1){
                env.setTextPrintLine(Boolean.valueOf(args[i + 1]));
            }else if (args[i].equals("--text.ignorecase") && i < args.length - 1){
                env.setTextIgnoreCase(Boolean.valueOf(args[i + 1]));
            }else if (args[i].equals("--help") || args[i].equals("-h")){
                printHelp();
                return;
            }
            
        }

        if (env.isPathToFind() || env.isTextToFind()){

            FileFinder finder = new FileFinder(env);
            finder.scan();

        }else{
            printHelp();
        }
        
    }
    
}
