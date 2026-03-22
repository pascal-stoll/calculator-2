package cn.calculator.controller;

import cn.calculator.MathTest.MathTest;
import cn.calculator.MathTest.Question;
import cn.calculator.report.MathTestReport;
import cn.calculator.view.CalculatorView;
import cn.calculator.view.ReportView;

/**
 * The controller of the calculator quiz application.
 * It handles the control flow and moderates between the view and the model (MathTest).
 */
public class CalculatorController {

    private CalculatorView view;
    private MathTest mathTest;
    
    private long startTime;    

    /**
     * Creates a controller object and instantiates the UI component
     */
    public CalculatorController() {
        this.view = new CalculatorView(e -> handleNextQuestion());
        this.mathTest = new MathTest();
    }
    
    private void restartTest() {
        this.view = new CalculatorView(e -> handleNextQuestion());
        this.mathTest = new MathTest();
        startTest();
    }
    /**
     * Instantiates and starts the quiz, stores the start time and shows the first equation.
    */
   public void startTest() {
        this.startTime = System.currentTimeMillis();
        nextQuestion();
        view.setVisible(true);
        view.focusAnswerField();
    }

    /**
     * Generates and displays a new question
     */
    private void nextQuestion() {
        final Question nextQuestion = mathTest.generateQuestion();
        
        view.setEquationText(nextQuestion.equation.stringifyEquation(false, null));
        view.clearAnswerField();
        view.setStatusText("Question " + nextQuestion.questionNumber + " of " + MathTest.MAX_QUESTIONS);

        view.focusAnswerField();
    }

    /**
     * Handles the transition to the next question.
     * It first evaluates the current answer and then either shows the next equation or finishes the test.
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
     * Finishes the test, calculates the elapsed time, builds the final report and opens the report view.
     */
    private void finishTest() {
        long endTime = System.currentTimeMillis();
        long elapsedTime = (endTime - startTime) / 1000;
        view.closeWindow();

        String report = new MathTestReport(mathTest, elapsedTime).buildReport();
        ReportView reportView = new ReportView(report, e -> restartTest());
        reportView.setVisible(true);
    }
}
