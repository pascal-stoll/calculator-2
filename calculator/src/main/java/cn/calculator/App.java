package cn.calculator;

import cn.calculator.controller.CalculatorController;
import cn.calculator.view.CalculatorView;

import javax.swing.SwingUtilities;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorView view = new CalculatorView();
            CalculatorController controller = new CalculatorController(view);
            controller.start();
        });
    }
}