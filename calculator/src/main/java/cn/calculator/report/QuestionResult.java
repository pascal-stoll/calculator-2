package cn.calculator.report;

/**
 * DTO for a single quiz question.
 * It stores the displayed equation, the user input, the correct result
 * and whether the answer was correct.
 */
public class QuestionResult {

    private final String questionText;
    private final String userInput;
    private final double correctResult;
    private final boolean correct;

    public QuestionResult(String questionText, String userInput, double correctResult, boolean correct) {
        this.questionText = questionText;
        this.userInput = userInput;
        this.correctResult = correctResult;
        this.correct = correct;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getUserInput() {
        return userInput;
    }

    public double getCorrectResult() {
        return correctResult;
    }

    public boolean isCorrect() {
        return correct;
    }

}
