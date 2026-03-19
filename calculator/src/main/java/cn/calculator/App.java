package cn.calculator;

import cn.calculator.calculation.Equation;
import cn.calculator.view.EquationPrinter;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        int count = 5;
        final Equation[] equations = new Equation[count];
        for(int i = 0; i<count; i++) {
            Equation equation = Equation.randomEquation();
            equations[i] = equation;
        }
        final int maxResultPadding = EquationPrinter.maxResultLength(equations);
        final int maxOperators = EquationPrinter.maxOperatorCount(equations);
        final EquationPrinter printer = new EquationPrinter(maxOperators, maxResultPadding);
        
        for(int j = 0; j<count; j++) {
            final String str = printer.print(equations[j], true);
            System.out.println(str);
        }

    }
}
