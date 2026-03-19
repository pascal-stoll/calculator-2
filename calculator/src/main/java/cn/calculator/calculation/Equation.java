package cn.calculator.calculation;

import java.util.Optional;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Stream;

/**
 * The representation of an equation, that consists of MathmaticalTokens.
 */
public final class Equation {

    public static int MIN_NUMBER_OPERATORS = 3;
    public static int MAX_NUMBER_OPERATORS = 5;
    public static Random RANDOM = new Random();

    private final int numberOperators;
    private final MathematicalToken[] equation;
    private final double result;

    private Equation(final int numberOperators, final MathematicalToken[] equation) {
        this.numberOperators = numberOperators;
        this.equation = equation;
        this.result = evaluate();
    }

    /**
     * Randomly generates an equation with between `MIN_NUMBER_OPERATORS` and `MAX_NUMBER_OPERATORS` many operators
     * @return an Equation object
     */
    public static Equation randomEquation() {
        final int numberOperators = RANDOM.nextInt(MIN_NUMBER_OPERATORS, MAX_NUMBER_OPERATORS + 1);
        final int tokenCount = 2 * numberOperators + 1;

        MathematicalToken[] tokens = new MathematicalToken[tokenCount];
        tokens[0] = MathematicalToken.randomNumber();

        for (int i=1; i < tokenCount; i+=2) {
            tokens[i] = MathematicalToken.randomOperator();
            tokens[i+1] = MathematicalToken.randomNumber();
        }

        return new Equation(numberOperators, tokens);
    }

    /**
     * Calculates the 
     * @param equations
     * @return
     */
    public static int maxResultPadding(final Equation[] equations) {
        final boolean containsNegative = Stream.of(equations).anyMatch((eq) -> eq.evaluate() < 0d);
        final int negativeCount = containsNegative ? 1 : 0;

        final Optional<Integer> largestSolution = Stream.of(equations)
        .map(Equation::evaluate)
        .map(Equation::numberOfDigits)
        .max(Integer::compareTo);

        if (largestSolution.isEmpty()) {
            throw new IllegalStateException("Unreachable code");
        }
        return largestSolution.get() + negativeCount;
    }

    /**
     * Generates a string of the equation
     * @param solution indicating whether the result should be printed or not
     * @return the string representation of the equation
     */
    public String stringifyEquation(boolean solution, Integer maxResultPadding) {
        if (maxResultPadding == null) {
            maxResultPadding = 0;
        }

        StringBuilder builder = new StringBuilder();
        for (MathematicalToken token : equation) {
            builder.append(token).append(" ");
        }
        builder.append(this.operatorPadding());

        builder.append("= ");

        if (solution) {
            builder.append(this.resultPadding(maxResultPadding)).append(evaluate());
        } else {
            builder.append("?");
        }
        return builder.toString();
    }

    /**
     * Evaluates the equation, processing * and / before + and -
     * @return the result as float with two decimal digits 
     */
    public double evaluate() {
        final Stack<Integer> stack = new Stack<>();
        Operator currentOperator = Operator.PLUS; // default for first number

        for (int i = 0; i < this.equation.length; i++) {
            MathematicalToken token = this.equation[i];

            if (token.isNumber()) {
                int num = token.getNumber();

                switch (currentOperator) {
                    case PLUS:
                        stack.push(num);
                        break;
                    case MINUS:
                        stack.push(-1 * num);
                        break;
                    case MULTIPLY:
                        stack.push(stack.pop() * num);
                        break;
                    case DIVIDE:
                        stack.push(stack.pop() / num);
                        break;
                }
            } else if (token.isOperator()) {
                currentOperator = token.getOperator();
            }
        }

        // Sum all values in stack
        int result = 0;
        for (int value : stack) {
            result += value;
        }

        return Math.round(result * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return this.stringifyEquation(true, null);
    }

    /**
     * Returns number of digits (in base 10) of a given number
     * @param value
     * @return
     */
    private static int numberOfDigits(final double value) {
            return (int) Math.floor(Math.log10(Math.abs(value) + 1));
    }

    /**
     * Returns the exact number of spaces needed to right-pad the equation to align the equal signs of all equations
     * @return a String only consisting of spaces
     */
    private String operatorPadding() {
        final int operatorPaddingCount = MAX_NUMBER_OPERATORS - this.numberOperators;
        final int SPACES_PER_OPERATOR = 5;
        return " ".repeat(operatorPaddingCount * SPACES_PER_OPERATOR);
    }

    /**
     * Returns the exact number of spaces needed to left-pad the result to align the decimal point 
     * @param maxResultPadding a String consisting of spaces
     * @return
     */
    private String resultPadding(final int maxResultPadding) {
        final boolean isPositive = this.result >= 0;
        final int positiveIndicator = isPositive ? 1 : 0;
        return " ".repeat(maxResultPadding - numberOfDigits(this.result) + positiveIndicator);
    }

    public double getResult() {
        return result;
    }
}
