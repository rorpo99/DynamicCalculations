package com.dynamic.calculations.util;

public enum Operator implements Comparable<Operator> {
    NOT("not", 2),
    AND("and", 1),
    OR("or", 0);

    private final String symbol;
    private final int precedence;

    public String getSymbol() {
        return this.symbol;
    }

    public int getPrecedence() {
        return this.precedence;
    }

    Operator(String symbol, int precedence) {
        this.symbol = symbol;
        this.precedence = precedence;
    }

    public int comparePrecedence(Operator operator) {
        return this.precedence - operator.precedence;
    }
}
