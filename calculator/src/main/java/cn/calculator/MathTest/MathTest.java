package cn.calculator.MathTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates a Test and stores all necessary data, such as all questions.
 */
public class MathTest {
    public final List<Question> questions;
    
    public static final int MAX_QUESTIONS = 3;

    public MathTest() {
        this.questions = new ArrayList<>();
    }

    /**
     * Evaluates the user's input for the current equation.
     * - If the input is empty, the question is stored as skipped.
     * - If the input is numeric, it is checked against the correct result.
     * @param input the user's input as String
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
     * Generates and returns a new random equation
     * @returns a randomly generated question
     */
    public Question generateQuestion() {
        final int questionNumber = this.questions.size() + 1;
        final Question newQuestion = Question.randomQuestion(questionNumber);
        this.questions.add(newQuestion);
        return newQuestion;
    }

    /**
     * Checks whether the test is finished
     * @return true if the test is finished
     */
    public boolean testFinished() {
        return this.questions.size() >= MAX_QUESTIONS;
    }
}
