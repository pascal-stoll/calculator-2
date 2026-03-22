package cn.calculator.calculation;

import java.util.Optional;
import java.util.Random;

/**
 * A wrapper representing a part of an equation, which can either be a number or an operator
 */
public final class MathematicalToken {

    public static int MAX_NUMBER = 99;
    public static int MIN_NUMBER = 1;
    public static Random RANDOM = new Random();

    private final Optional<Operator> operator;
    private final Optional<Integer> number;

    private MathematicalToken(Optional<Operator> operator, Optional<Integer> number) {
        this.operator = operator;
        this.number = number;
    }

    /**
     * Randomly generates a MathematicalToken which is an operator 
     * @return the MathematicalToken object 
     */
    public static MathematicalToken randomOperator() {
        final Operator operator = Operator.randomOperator();
        return new MathematicalToken(Optional.of(operator), Optional.empty());
    }

    /**
     * Randomly generates a MathematicalToken which is a number
     * @return the MathematicalToken object
     */
    public static MathematicalToken randomNumber() {
        final int number = RANDOM.nextInt(MIN_NUMBER, MAX_NUMBER + 1);
        return new MathematicalToken(Optional.empty(), Optional.of(number));
    }

    /**
     * Checks if the token is an operator 
     * @return true if MathematicalToken contains an operator
     */
    public boolean isOperator() {
        return !operator.isEmpty();
    }

    /**
     * Checks if the token is a number 
     * @return true if MathematicalToken contains a number
     */
    public boolean isNumber() {
        return !number.isEmpty();
    }

    /**
     * Returns the number, if it exists
     * @throws NoSuchElementException if Token does not contain number
     * @return the number, if it exists
    */
   public int getNumber() {
       return number.get();
   }
    
    /**
     * Returns the operator, if it exists
     * @throws NoSuchElementException if Token does not contain operator
     * @return the operator
     */
    public Operator getOperator() {
        return operator.get();
    }

    @Override
    public String toString() {
        if (isOperator()) {
            return this.operator.get().toString();
        } else if(isNumber()) {
            return this.number.get().toString();
        }
        else {
            throw new IllegalStateException("Unreachable code");
        }
    }
}