package cn.calculator;


import javax.swing.SwingUtilities;
import cn.calculator.controller.CalculatorController;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorController controller = new CalculatorController();
            controller.startTest();
        });
    }
}