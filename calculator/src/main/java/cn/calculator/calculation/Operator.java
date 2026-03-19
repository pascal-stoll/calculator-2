package cn.calculator.calculation;

import java.util.Random;

public enum Operator {
    PLUS('+'), MINUS('-'), MULTIPLY('*'), DIVIDE('/');

    private static final Random RANDOM = new Random();

    private final char symbol;

    private Operator(char c) {
        this.symbol = c;    
    }

    /**
     * Returns a random operator
     * @return a random operator
     */
    public static Operator randomOperator() {
        final Operator[] values = Operator.values();
        final int index = RANDOM.nextInt(values.length); 
        return values[index];
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
