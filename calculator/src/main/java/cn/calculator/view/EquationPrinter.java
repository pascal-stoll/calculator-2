package cn.calculator.view;

import java.util.Optional;
import java.util.stream.Stream;

import cn.calculator.calculation.Equation;
import cn.calculator.calculation.MathematicalToken;

/**
 * Class used to pretty-print multiple equations so they are aligned at the operators, equal sign (=) and negative sign (-).
 */
public final class EquationPrinter {
    
    public static int CHAR_PADDING = 1;

    private final boolean useOperatorPadding;
    private final int maxOperators;
    private final boolean useResultPadding;
    private final int maxResultLength;

    /**
     * Creates an EquationPrinter object 
     * @param maxOperators the maximum number of operators in the group of equations (relevant for padding)
     * @param maxResultLength the maximum length of the results in the group of equations (relevant for padding)
     */
    public EquationPrinter(final int maxOperators, final int maxResultLength) {
        if (maxOperators < 0 || maxResultLength < 0) {
            throw new IllegalArgumentException("Arguments must be non-negative integers.");
        }

        this.maxOperators = maxOperators;
        this.maxResultLength = maxResultLength;
        this.useOperatorPadding = true;
        this.useResultPadding = true;
    }

    public EquationPrinter() {
        this.maxOperators = 0;
        this.maxResultLength = 0;
        this.useOperatorPadding = false;
        this.useResultPadding = false;
    }
    
    public EquationPrinter(final int maxOperators) {
        this.maxOperators = maxOperators;
        this.maxResultLength = 0;
        this.useOperatorPadding = true;
        this.useResultPadding = false;
    }

    /**
     * Generates a string of the equation
     * @param solution indicating whether the result should be printed or not
     * @return the string representation of the equation
     */
    public final String print(final Equation equation, final boolean solution) {
        StringBuilder builder = new StringBuilder();
        for (MathematicalToken token : equation.getTokens()) {
            builder.append(this.numberPadding(token))
                .append(" ".repeat(CHAR_PADDING));
        }
        if(this.useOperatorPadding) {
            builder.append(this.operatorPadding(equation.numberOperators));
        }

        builder.append("= ");

        if (solution) {
            if(equation.result < 0d) {
                builder.append('-');
            }
            else {
                builder.append(' ');
            }

            if(this.useResultPadding) {
                builder.append(this.resultPadding(equation.result));
            }

            builder.append(Math.abs(equation.result));

        } else {
            builder.append("?");
        }
        return builder.toString();
    }

    /**
     * Adds padding if token is a number
     * @param token the token to be padded
     * @return the correctly padded String 
     */
    private String numberPadding(final MathematicalToken token) {
        if(token.isOperator()) return token.getOperator().toString();

        final int MAX_NUMBER_LENGTH = 2;
        final String unpadded = String.valueOf(token.getNumber());
        String padded = " ".repeat(MAX_NUMBER_LENGTH - unpadded.length()) + unpadded;  
        return padded;
    }

    /**
     * Returns the exact number of spaces needed to right-pad the equation to align the equal signs of all equations
     * @return a String only consisting of spaces
     */
    private String operatorPadding(final int equationOperators) {
        final int paddingNeeded = this.maxOperators - equationOperators;
        final int SPACES_PER_OPERATOR = 2*CHAR_PADDING + 1 + 2;
        return " ".repeat(paddingNeeded * SPACES_PER_OPERATOR);
    }

    /**
     * Returns the exact number of spaces needed to left-pad the result to align the decimal point 
     * @param maxResultPadding a String consisting of spaces
     * @return
     */
    private String resultPadding(final double result) {
        final int paddingNeeded = this.maxResultLength - EquationPrinter.getLength(result);
        return " ".repeat(paddingNeeded);
    }

    /**
     * calculates the maximum result length of a group of `Equation`s, which is input into the `EquationPrinter` constructor 
     * @param equations the group of equations for which to calculate the maximum
     * @return the maximum length (number of chars needed in base 10) 
     */
    public static int maxResultLength(final Equation[] equations) {
        final boolean containsNegative = Stream.of(equations).anyMatch((eq) -> eq.result < 0d);
        final int negativeCount = containsNegative ? 1 : 0;

        final Optional<Integer> largestSolution = Stream.of(equations)
        .map(eq -> eq.result)
        .map(EquationPrinter::getLength)
        .max(Integer::compareTo);

        if (largestSolution.isEmpty()) {
            throw new IllegalStateException("Unreachable code");
        }
        return largestSolution.get() + negativeCount;
    }

    /**
     * Computes the maximum number of operators in a group of equations
     * @param equations the group of equations 
     * @return the maximum number of operators
     */
    public static int maxOperatorCount(final Equation[] equations) {
        if (equations.length == 0) {
            throw new IllegalArgumentException("`equations` must not be empty");
        }

        return Stream.of(equations)
            .map(Equation::getTokens)
            .map(tokens -> tokens.length)
            .map(length -> (length - 1) / 2)
            .max(Integer::compareTo)
            .get();
    }

    /**
     * Returns the number of characters (in base 10) of a given number
     * @param value the number to be evaluated
     * @return the length of the printed number 
     */
    public static int getLength(final double value) {
        final double newValue = Math.max(1d, Math.abs(value));
        return (int) Math.floor(Math.log10(newValue));
    }
}
