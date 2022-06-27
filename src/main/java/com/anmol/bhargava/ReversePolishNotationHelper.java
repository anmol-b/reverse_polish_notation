package com.anmol.bhargava;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.FileStore;
import java.util.Scanner;
import java.util.Stack;

public class ReversePolishNotationHelper {
    FileReader reader;
    Scanner input;

    public ReversePolishNotationHelper(File file) throws FileNotFoundException {
        this.reader = new FileReader(file);
        this.input = new Scanner(this.reader);
    }

    private String[] validateLine(String line) throws IllegalArgumentException {
        if(line.length() == 0 || line.equals("") || line.charAt(0) == '#'){
            return null;
        }
        
        String[] ret = line.split(",");

        if(ret.length < 3){
            throw new IllegalArgumentException("Invalid line input: " + line + ". Expected at least 3 comma seperated values.");
        }
        
        for(int i = 0; i < ret.length; i++){
            ret[i] = ret[i].trim();
        }
        
        return ret;
    }

    private boolean isOperator(String s){
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }

    private int getPriority(String operator) throws IllegalArgumentException{
        switch(operator){
            case "-":
            case "+":
                return 0;
            case "/":
            case "*":
                return 1;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }

    private String evaluateRPN(String[] tokens) throws IllegalArgumentException, NumberFormatException{
 
        Stack<Expression> exp = new Stack<>();
 
        for(int i = 0; i < tokens.length; i++){
            String token = tokens[i];

            if (isOperator(token)) {

                if(exp.size() < 2){
                    throw new IllegalArgumentException("Invalid number of operators and operands.");
                }
 
                Expression second = exp.pop();
                Expression first = exp.pop();
                
                int currOperPriority = getPriority(token);
                int firstOperPriority = first.getPriority();
                int secondOperPriority = second.getPriority();
                // first expression is not a number
                if (firstOperPriority != -1){ 
                    // Firsts priority is different than current operator or if its same it is different than either second expression or current token
                    if (firstOperPriority != currOperPriority || (!second.getOperator().isBlank() && (!first.getOperator().equals(second.getOperator())) || !first.getOperator().equals(token))){
                        first.setExpression("( " + first.getExpression() + " )");
                    }
                }

                if (secondOperPriority != -1){
                    if ((secondOperPriority != currOperPriority || (!first.getOperator().isBlank() && (!first.getOperator().equals(second.getOperator()) ) || !second.getOperator().equals(token)))){
                        second.setExpression("( " + second.getExpression() + " )");
                    }
                }
 
                exp.push(new Expression(first, second, token, currOperPriority));
            } else {
                try{
                    double number = Double.parseDouble(token);
                    Expression numExp = new Expression(number);
                    if(number < 0){
                        numExp.setExpression('(' + numExp.getExpression() +  ')');
                    }
                    exp.push(numExp);
                } catch (NumberFormatException numExp){
                    throw new NumberFormatException("Not a number encountered for token " + token + " at token number " + i + " additional diagnostics: " + numExp.getMessage());
                }  
            }
        }

        if(exp.size() > 1){
            throw new IllegalArgumentException("Invalid number of operators and operands.");
        }

        String expression = exp.peek().getExpression() ;
        Double value = exp.peek().getValue();
        String finalValue;

        if(value % 1 == 0){
            finalValue =  String.valueOf(value.longValue());
        }else{
            finalValue =  String.valueOf(value);
        }
        return expression + " = " + finalValue;
    }

    public void parseInput() throws Exception{

        long lineNumber = 0;

        while(input.hasNextLine()){
            lineNumber++;
            String line = input.nextLine();
            try{
                String[] tokens = validateLine(line);
                if(tokens != null){
                    System.out.println(evaluateRPN(tokens)); 
                }
            }catch(Exception e){
                // Need to just print out instead of passing on the exception to be able to continue reading the file.
                ReversePolishNotation.printError("occured at line: " + lineNumber + " :: " + e.getMessage());
            }
        }

        input.close();
        reader.close();
    }
}
