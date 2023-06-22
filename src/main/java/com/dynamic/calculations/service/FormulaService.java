package com.dynamic.calculations.service;

import com.dynamic.calculations.dto.Formula;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.LinkedList;

@Component
public class FormulaService {

    private final static Map<String, Operator> OPS = new HashMap<>();

    static {
        for (Operator operator : Operator.values()) {
            OPS.put(operator.symbol, operator);
        }
    }

    public Integer calculateResult(Formula formula) {
        List<String> tokens = tokenizeEquation(formula.getFormulaString());
        List<String> postfixNotation = convertFormulaToReversePolishNotation(formula);
        return evaluateFormula(postfixNotation);
    }


    private List<String> tokenizeEquation(String equation) {
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

            if (Character.isLetterOrDigit(currentChar) || currentChar == '.') {
                currentToken.append(currentChar);
            } else if (currentChar == '(' || currentChar == ')') {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken = new StringBuilder();
                }
                tokens.add(String.valueOf(currentChar));
            } else {
                throw new IllegalArgumentException("Invalid character in equation: " + currentChar);
            }
        }

        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }

        return tokens;
    }

    private Integer convertParamToActualValue(String token, Formula formula) {
        return switch (token) {
            case "x1" -> formula.getX1();
            case "x2" -> formula.getX2();
            case "x3" -> formula.getX3();
            case "x4" -> formula.getX4();
            case "x5" -> formula.getX5();
            case "true" -> 1;
            case "false" -> 0;
            default -> -1;
        };
    }

    private List<String> convertFormulaToReversePolishNotation(Formula formula) {

        List<String> tokens = tokenizeEquation(formula.getFormulaString());

        List<String> output = new LinkedList<>();
        Stack<String> stack = new Stack<>();

        for (String token : tokens) {
            if (OPS.containsKey(token)) {
                while (!stack.isEmpty() && OPS.containsKey(stack.peek())) {
                    Operator cOp = OPS.get(token);
                    Operator lOp = OPS.get(stack.peek());
                    if ((cOp.comparePrecedence(lOp) <= 0) || (cOp.comparePrecedence(lOp) < 0)) {
                        output.add(stack.pop());
                        continue;
                    }
                    break;
                }
                stack.push(token);
            } else if ("(".equals(token)) {
                stack.push(token);
            } else if (")".equals(token)) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                stack.pop();
            } else {
                Integer paramValue = convertParamToActualValue(token, formula);
                output.add(paramValue.toString());
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }

        return output;
    }

    private int evaluateFormula(List<String> tokens) {
        Stack<String> stack = new Stack<>();
        int x, y;
        String result;
        String choice;
        int value;
        String p = "";

        for (String token : tokens) {

            if (!OPS.containsKey(token)) {
                stack.push(token);
                continue;
            } else {
                choice = token;
            }

            switch (choice) {
                case "and" -> {
                    x = Integer.parseInt(stack.pop());
                    y = Integer.parseInt(stack.pop());
                    value = x & y;
                    result = p + value;
                    stack.push(result);
                }
                case "or" -> {
                    x = Integer.parseInt(stack.pop());
                    y = Integer.parseInt(stack.pop());
                    value = y | x;
                    result = p + value;
                    stack.push(result);
                }
                case "not" -> {
                    x = Integer.parseInt(stack.pop());
                    value = ~x;
                    result = p + value;
                    stack.push(result);
                }
                default -> {
                }
            }
        }
        return Integer.parseInt(stack.pop());
    }
}
