package cn.calculator.calculation;

import java.util.Random;
import java.util.Stack;

import cn.calculator.view.EquationPrinter;

/**
 * The representation of an equation, that consists of MathmaticalTokens.
 */
public final class Equation {

    public static int MIN_NUMBER_OPERATORS = 3;
    public static int MAX_NUMBER_OPERATORS = 5;
    public static Random RANDOM = new Random();

    public final int numberOperators;
    private final MathmaticalToken[] equation;
    public final double result;

    /**
     * Creates an equation object. The Constructur is private and only called by the static method `randomEquation`.
     * @param numberOperators the number of operators of the equation
     * @param equation the equation's tokens
     */
    private Equation(final int numberOperators, final MathmaticalToken[] equation) {
        this.numberOperators = numberOperators;
        this.equation = equation;
        this.result = this.evaluate();
    }

    /**
     * Randomly generates an equation with between `MIN_NUMBER_OPERATORS` and `MAX_NUMBER_OPERATORS` many operators
     * @return an Equation object
     */
    public static Equation randomEquation() {
        final int numberOperators = RANDOM.nextInt(MIN_NUMBER_OPERATORS, MAX_NUMBER_OPERATORS + 1);
        final int tokenCount = 2 * numberOperators + 1;

        MathmaticalToken[] tokens = new MathmaticalToken[tokenCount];
        tokens[0] = MathmaticalToken.randomNumber();

        for (int i=1; i < tokenCount; i+=2) {
            tokens[i] = MathmaticalToken.randomOperator();
            tokens[i+1] = MathmaticalToken.randomNumber(); 
        }

        return new Equation(numberOperators, tokens);
    }

    public MathmaticalToken[] getTokens() {
        return this.equation;
    } 

    /**
     * Evaluates the equation, processing * and / before + and -
     * @return the result as float with two decimal digits 
     */
    private double evaluate() {
        final Stack<Integer> stack = new Stack<>();
        Operator currentOperator = Operator.PLUS; // default for first number

        for (int i = 0; i < this.equation.length; i++) {
            MathmaticalToken token = this.equation[i];

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
        final EquationPrinter printer = new EquationPrinter(this.numberOperators, EquationPrinter.getLength(this.result));
        return printer.print(this, true);
    }
}
