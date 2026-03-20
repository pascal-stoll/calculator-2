package cn.calculator.MathTest;

import java.util.Optional;

import cn.calculator.calculation.Equation;

/**
 * Encapsulates one question of a MathTest, representing an equation together with a user's answer and the data whether it is correct or not.
 */
public class Question {
    
    public final int questionNumber;
    public final Equation equation;

    public Optional<String> answer;
    public AnswerStatus status;
    public Optional<Boolean> correctAnswer;

    /**
     * Private constructor, as it should only be called from the static factory method 
     * @param questionNumber the ordinal number of the question
     * @param equation the equation
     */
    private Question(final int questionNumber, final Equation equation) {
        this.questionNumber = questionNumber;
        this.equation = equation;
        this.answer = Optional.empty();
        this.correctAnswer = Optional.empty();
        this.status = AnswerStatus.NOT_ANSWERED;
    }

    /**
     * Randomly generates an equation and wraps it in a Question object
     * @param questionNumber the ordinal number of the question
     * @return the Question object
     */
    public static Question randomQuestion(final int questionNumber) {
        return new Question(questionNumber, Equation.randomEquation());
    }

    /**
     * Marks this question as skipped by the user.
     * Is called if the question is answered with empty input. 
     */
    public void setSkipped() {
        this.correctAnswer = Optional.of(false);
        this.status = AnswerStatus.SKIPPED;
    }
    
    /**
     * Evaluates the user input and saves the result.
     * @param answer the user input 
     */
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
