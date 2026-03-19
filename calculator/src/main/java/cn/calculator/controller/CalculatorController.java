package cn.calculator.controller;

import cn.calculator.calculation.Equation;
import cn.calculator.report.QuestionResult;
import cn.calculator.report.ReportBuilder;
import cn.calculator.view.CalculatorView;
import cn.calculator.view.ReportView;

import java.util.ArrayList;
import java.util.List;

public class CalculatorController {

    private final CalculatorView view;
    private final List<QuestionResult> results;
    private final ReportBuilder reportBuilder;

    private long startTime;

    private int questionNumber;
    private int correctAnswers;
    private Equation currentEquation;


    private static final int MAX_QUESTIONS = 10;

    public CalculatorController(CalculatorView view) {
        this.view = view;
        this.results = new ArrayList<>();
        this.reportBuilder = new ReportBuilder();

        this.questionNumber = 1;
        this.correctAnswers = 0;

        this.view.addNextListener(e -> handleNextQuestion());
        this.view.addQuitListener(e -> System.exit(0));
    }

    public void start() {
        startTime = System.currentTimeMillis();
        showNextEquation();
        view.setVisible(true);
        view.focusAnswerField();
    }

    private void handleNextQuestion() {
        if (currentEquation != null) {
            evaluateCurrentAnswer();
        }

        if (questionNumber > MAX_QUESTIONS) {
            finishTest();
            return;
        }
        showNextEquation();
    }

    private void showNextEquation() {
        currentEquation = Equation.randomEquation();

        view.setEquationText(currentEquation.stringifyEquation(false, null));
        view.clearAnswerField();
        view.setStatusText("Question " + questionNumber + " of " + MAX_QUESTIONS);

        questionNumber++;
        view.focusAnswerField();
    }
    private void evaluateCurrentAnswer() {
        String input = view.getAnswerText();
        String questionText = currentEquation.stringifyEquation(false, null);
        double correctResult = currentEquation.getResult();

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

    private void finishTest() {
        long endTime = System.currentTimeMillis();
        long elapsedTime = (endTime - startTime) / 1000;
        view.setEquationText("Test completed!");
        view.setStatusText("Correct answers: " + correctAnswers + "/" + MAX_QUESTIONS + "       "
                            + "Time: " + elapsedTime + " seconds");

        String report = reportBuilder.buildReport(results, correctAnswers, MAX_QUESTIONS, elapsedTime);
        view.closeWindow();

        ReportView reportView = new ReportView(report);
        reportView.addQuitListener(e -> System.exit(0));
        reportView.setVisible(true);
    }
}
