package cn.calculator;

import cn.calculator.controller.CalculatorController;

import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorController controller = new CalculatorController();
            controller.startTest();
        });
    }
}