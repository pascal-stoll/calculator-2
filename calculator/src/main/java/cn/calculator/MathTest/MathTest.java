package cn.calculator.MathTest;

import java.util.ArrayList;
import java.util.List;

public class MathTest {
    public final List<Question> questions;
    public int correctAnswers;
    
    public static final int MAX_QUESTIONS = 3;

    public MathTest() {
        this.questions = new ArrayList<>();
        this.correctAnswers = 0;
    }

    /**
     * Evaluates the user's input for the current equation.
     * If the input is empty, the question is stored as skipped.
     * If the input is numeric, it is checked against the correct result.
     */
    public void evaluateAnswer(String input) {
        if (this.questions.size() == 0) {
            throw new IllegalStateException("Equation cannot be null.");
        }

        final Question currentQuestion = this.questions.get(this.questions.size() - 1);

        if (input.isEmpty()) {
            currentQuestion.setSkipped();
        } else {
            currentQuestion.evaluateAnswer(input);
        }
    }

    /**
     * Generates and shows the next random equation in the view.
     */
    public Question generateQuestion() {
        final int questionNumber = this.questions.size() + 1;
        final Question newQuestion = Question.randomQuestion(questionNumber);
        this.questions.add(newQuestion);
        return newQuestion;
    }

    public boolean testFinished() {
        return this.questions.size() >= MAX_QUESTIONS;
    }
}
