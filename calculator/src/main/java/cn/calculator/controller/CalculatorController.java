package cn.calculator.controller;

import cn.calculator.calculation.Equation;
import cn.calculator.view.CalculatorView;

public class CalculatorController {

    private final CalculatorView view;
    private int questionNumber;
    private static final int MAX_QUESTIONS = 10;

    public CalculatorController(CalculatorView view) {
        this.view = view;
        this.questionNumber = 1;

        this.view.addNextListener(e -> showNextEquation());
        this.view.addQuitListener(e -> System.exit(0));
    }

    public void start() {
        showNextEquation();
        view.setVisible(true);
        view.focusAnswerField();
    }

    private void showNextEquation() {
        if (questionNumber > MAX_QUESTIONS) {
            view.setEquationText("No more questions.");
            view.setStatusText("Test finished.");
            view.disableInput();
            return;
        }

        Equation equation = Equation.randomEquation();

        view.setEquationText(equation.stringifyEquation(false, null));
        view.clearAnswerField();
        view.setStatusText("Question " + questionNumber + " of " + MAX_QUESTIONS);

        questionNumber++;
        view.focusAnswerField();
    }
}
