package com.anmol.bhargava;

import java.io.File;

/**
 * Simple implementation of a Reverse Polist Notation evaluator.
 * Assumptions:
 * 1. The program accepts decimal values. Calculation is performed on precision supported by Double class. Output for decimal value is not rounded off or formated.
 * 2. Negative numbers from input are wrapped in parentheses in the output to indicate ptential adjacent operator change while evaluating.
 * 
 */
public class ReversePolishNotation {
    public static void main(String[] args){
        
        if(args.length != 1){
           printError("Enter a valid file name or file path");
        }

        File file = new File(args[0]);
        if(!file.exists() || !file.isFile()){
            printError("Enter a valid file name or file path that exists");
        }

        if(!file.canRead()){
            printError("Cannot read the file: " + file.getAbsolutePath());
        }
        try{
            ReversePolishNotationHelper helper = new ReversePolishNotationHelper(file);
            helper.parseInput();
        }catch(Exception e){
            printError(e.getMessage() + " " + e.getCause());
        }

    }

    public static void printError(String errMsg){
        System.out.println("ERROR - " + errMsg);
    }
}
