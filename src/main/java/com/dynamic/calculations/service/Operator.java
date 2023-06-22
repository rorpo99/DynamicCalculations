package com.dynamic.calculations.service;

public enum Operator implements Comparable<Operator> {
    NOT("not", 2),
    AND("and", 1),
    OR("or", 0);

    public final String symbol;
    final int precedence;

    Operator(String symbol, int precedence) {
        this.symbol = symbol;
        this.precedence = precedence;
    }

    public int comparePrecedence(Operator operator) {
        return this.precedence - operator.precedence;
    }
}
