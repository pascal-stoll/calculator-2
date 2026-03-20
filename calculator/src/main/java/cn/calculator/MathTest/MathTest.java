package cn.calculator.MathTest;

import java.util.ArrayList;
import java.util.List;

import cn.calculator.report.QuestionResult;

public class MathTest {
    public final List<QuestionResult> results;
    public int questionNumber;
    public int correctAnswers;
    private Question currentQuestion;

    public static final int MAX_QUESTIONS = 3;

    public MathTest() {
        this.results = new ArrayList<>();

        this.questionNumber = 0;
        this.correctAnswers = 0;
    }

    /**
     * Evaluates the user's input for the current equation.
     * If the input is empty, the question is stored as skipped.
     * If the input is numeric, it is checked against the correct result.
     */
    public void evaluateAnswer(String input) {
        if (currentQuestion == null) {
            throw new IllegalStateException("Equation cannot be null.");
        }

        String questionText = currentQuestion.equation.stringifyEquation(false, null);
        double correctResult = currentQuestion.equation.getResult();

        if (input.isEmpty()) {
            results.add(new QuestionResult(questionText, "[skipped]", correctResult, false));
            return;
        }

        try {
            double userAnswer = Double.parseDouble(input);
            boolean isCorrect = Double.compare(userAnswer, correctResult) == 0;

            if (isCorrect) {
                correctAnswers++;
            }
            results.add(new QuestionResult(questionText, input, correctResult, isCorrect));
        }
        catch (NumberFormatException ignored) {}
    }

    /**
     * Generates and shows the next random equation in the view.
     */
    public Question generateQuestion() {
        questionNumber++;
        this.currentQuestion = Question.randomQuestion(questionNumber);
        return this.currentQuestion;
    }

    public boolean testFinished() {
        return questionNumber > MAX_QUESTIONS;
    }
}
