package cn.calculator.controller;

import cn.calculator.MathTest.MathTest;
import cn.calculator.MathTest.Question;
import cn.calculator.report.MathTestReport;
import cn.calculator.view.CalculatorView;
import cn.calculator.view.ReportView;

/**
 * The controller of the calculator quiz application.
 * It handles the quiz flow, evaluates user answers and creates the final report.
 */
public class CalculatorController {

    private final CalculatorView view;
    private final MathTest mathTest;
    
    private long startTime;    

    /**
     * Creates a controller object and connects the UI actions with the controller logic.
     * @param view the calculator view
     */
    public CalculatorController() {
        this.view = new CalculatorView(e -> handleNextQuestion());
        this.mathTest = new MathTest();
    }

    /**
     * Starts the quiz, stores the start time and shows the first equation.
     */
    public void startTest() {
        this.startTime = System.currentTimeMillis();
        nextQuestion();
        view.setVisible(true);
        view.focusAnswerField();
    }

    private void nextQuestion() {
        final Question nextQuestion = mathTest.generateQuestion();
        
        view.setEquationText(nextQuestion.equation.stringifyEquation(false, null));
        view.clearAnswerField();
        view.setStatusText("Question " + nextQuestion.questionNumber + " of " + MathTest.MAX_QUESTIONS);

        view.focusAnswerField();
    }

    /**
     * Handles the transition to the next question.
     * It first evaluates the current answer and then either shows the next equation
     * or finishes the test.
     */
    private void handleNextQuestion() {
        final String answer = view.getAnswerText();
        mathTest.evaluateAnswer(answer);

        if (mathTest.testFinished()) {
            finishTest();
        } else {
            nextQuestion();
        }
    }

    /**
     * Finishes the test, calculates the elapsed time, builds the final report
     * and opens the report view.
     */
    private void finishTest() {
        long endTime = System.currentTimeMillis();
        long elapsedTime = (endTime - startTime) / 1000;
        view.closeWindow();
        
        String report = new MathTestReport(mathTest, elapsedTime).buildReport();
        ReportView reportView = new ReportView(report);
        reportView.setVisible(true);
    }
}
