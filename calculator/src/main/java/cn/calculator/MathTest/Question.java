package cn.calculator.MathTest;

import java.util.Optional;

import cn.calculator.calculation.Equation;

public class Question {
    
    public final int questionNumber;
    public final Equation equation;
    public Optional<Double> answer;

    private Question(final int questionNumber, final Equation equation) {
        this.questionNumber = questionNumber;
        this.equation = equation;
        this.answer = Optional.empty();
    }

    public static Question randomQuestion(final int questionNumber) {
        return new Question(questionNumber, Equation.randomEquation());
    }
}
