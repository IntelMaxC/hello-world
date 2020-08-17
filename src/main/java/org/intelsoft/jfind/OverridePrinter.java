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
public class OverridePrinter {
    
    private static OverridePrinter instance;
    
    private int lastSize;

    public static OverridePrinter getInstance() {
        
        if (instance == null){
            instance = new OverridePrinter();
        }
        
        return instance;
    }
    
    private OverridePrinter(){
        lastSize = 0;
    }
    
    public void print(Object o){
        
        System.out.print("\r");
        System.out.print(" ".repeat(lastSize));
        
        lastSize = o.toString().length();
        System.out.print("\r" + o.toString());
        
    }
    
    public void println(Object o){
        
        System.out.print("\r");
        System.out.print(" ".repeat(lastSize));
        
        lastSize = 0;
        System.out.println("\r" + o);
    }
    
}
