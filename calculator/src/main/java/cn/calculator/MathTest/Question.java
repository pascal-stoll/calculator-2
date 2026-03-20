package cn.calculator.MathTest;

import java.util.Optional;

import cn.calculator.calculation.Equation;

public class Question {
    
    public final int questionNumber;
    public final Equation equation;

    public Optional<String> answer;
    public AnswerStatus status;
    public Optional<Boolean> correctAnswer;

    private Question(final int questionNumber, final Equation equation) {
        this.questionNumber = questionNumber;
        this.equation = equation;
        this.answer = Optional.empty();
        this.correctAnswer = Optional.empty();
        this.status = AnswerStatus.NOT_ANSWERED;
    }

    public static Question randomQuestion(final int questionNumber) {
        return new Question(questionNumber, Equation.randomEquation());
    }

    public void setSkipped() {
        this.correctAnswer = Optional.of(false);
        this.status = AnswerStatus.SKIPPED;
    }
    
    public void evaluateAnswer(final String answer) {
        this.answer = Optional.of(answer);

        try {
            double doubleAnswer = Double.parseDouble(answer);
            
            this.status = AnswerStatus.ANSWERED;
            final boolean correctAnswer = Double.compare(this.equation.getResult(), doubleAnswer) == 0;
            this.correctAnswer = Optional.of(correctAnswer);            
        }
        catch (NumberFormatException error) {
            this.status = AnswerStatus.INPUT_ERROR;
            this.correctAnswer = Optional.of(false);
        } 
    }
}
