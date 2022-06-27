package com.anmol.bhargava;

public class Expression {

    private int priority;
    private Double value;
    private String expression;
    private String operator;

    public Expression(double value) {
        this.value = value;
        if(value % 1 == 0){
            this.expression =  String.valueOf(this.value.longValue());
        }else{
            this.expression =  String.valueOf(value);
        }
        this.operator = "";
        this.priority = -1;
    }

    public Expression(Expression first, Expression second, String operator, int priority)  throws IllegalArgumentException{
        this.value = calculateValue(first.value, second.value, operator);
        this.operator = operator;
        this.expression = String.format("%s %s %s", first.expression, operator, second.expression);
        this.priority = priority;
    }

    private double calculateValue(double a, double b, String operator) throws IllegalArgumentException, ArithmeticException{
        switch(operator){
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if(b == 0){
                    throw new ArithmeticException("Division by zero not allowed.");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    public int getPriority() {
        return priority;
    }

    public double getValue() {
        return value;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return expression;
    }
    
}
