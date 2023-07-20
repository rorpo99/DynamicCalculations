package com.dynamic.calculations.util;

import com.dynamic.calculations.dto.Formula;

import java.util.*;

public class FormulaUtil {

    private final static Map<String, Operator> TOKEN_TO_OPERATION = new HashMap<>();

    static {
        for (Operator operator : Operator.values()) {
            TOKEN_TO_OPERATION.put(operator.getSymbol(), operator);
        }
    }

    public static Integer calculateResult(Formula formula) {
        List<String> tokens = tokenizeEquation(formula.getFormulaString());
        List<String> postfixNotation = convertFormulaToReversePolishNotation(tokens, formula);
        return evaluateFormula(postfixNotation);
    }

    private static List<String> tokenizeEquation(String equation) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        for (int i = 0; i < equation.length(); i++) {
            char currentChar = equation.charAt(i);

            if (Character.isWhitespace(currentChar)) {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken = new StringBuilder();
                }
                continue;
            }

            if (Character.isLetterOrDigit(currentChar)) {
                currentToken.append(currentChar);
            }
            else if (currentChar == '(' || currentChar == ')') {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken = new StringBuilder();
                }
                tokens.add(String.valueOf(currentChar));
            }
            else {
                throw new IllegalArgumentException("Invalid character in equation: " + currentChar);
            }
        }

        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }

        return tokens;
    }

    private static Integer convertParamToActualValue(String token, Formula formula) {
        return switch (token) {
            case "x1" -> formula.getX1();
            case "x2" -> formula.getX2();
            case "x3" -> formula.getX3();
            case "x4" -> formula.getX4();
            case "x5" -> formula.getX5();
            case "true" -> 1;
            case "false" -> 0;
            default -> throw new RuntimeException("Value of formula's parameter is missing");
        };
    }

    private static List<String> convertFormulaToReversePolishNotation(List<String> tokens, Formula formula) {

        List<String> result = new LinkedList<>();
        Stack<String> stack = new Stack<>();

        for (String token : tokens) {
            if (TOKEN_TO_OPERATION.containsKey(token)) {
                while (!stack.isEmpty() && TOKEN_TO_OPERATION.containsKey(stack.peek())) {
                    Operator currentOperation = TOKEN_TO_OPERATION.get(token);
                    Operator lastOperation = TOKEN_TO_OPERATION.get(stack.peek());
                    if (currentOperation.comparePrecedence(lastOperation) <= 0) {
                        result.add(stack.pop());
                        continue;
                    }
                    break;
                }
                stack.push(token);
            }
            else if ("(".equals(token)) {
                stack.push(token);
            }
            else if (")".equals(token)) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    result.add(stack.pop());
                }
                stack.pop();
            }
            else {
                Integer paramValue = convertParamToActualValue(token, formula);
                result.add(paramValue.toString());
            }
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

        return result;
    }

    private static int evaluateFormula(List<String> tokens) {
        Stack<String> stack = new Stack<>();
        int leftValue, rigthValue;
        String choice;
        int value;

        for (String token: tokens) {

            if (TOKEN_TO_OPERATION.containsKey(token)) {
                choice = token;
            }
            else {
                stack.push(token);
                continue;
            }

            switch (choice) {
                case "and" -> {
                    leftValue = Integer.parseInt(stack.pop());
                    rigthValue = Integer.parseInt(stack.pop());
                    value = leftValue & rigthValue;
                    stack.push(String.valueOf(value));
                }
                case "or" -> {
                    leftValue = Integer.parseInt(stack.pop());
                    rigthValue = Integer.parseInt(stack.pop());
                    value = rigthValue | leftValue;
                    stack.push(String.valueOf(value));
                }
                case "not" -> {
                    leftValue = Integer.parseInt(stack.pop());
                    value = ~leftValue;
                    stack.push(String.valueOf(value));
                }
                default -> {
                }
            }
        }
        return Integer.parseInt(stack.pop());
    }
}
