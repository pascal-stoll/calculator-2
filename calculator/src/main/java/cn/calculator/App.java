package cn.calculator;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import cn.calculator.calculation.Equation;

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
        final int maxResultPadding = Equation.maxResultPadding(equations);
        
        for(int j = 0; j<count; j++) {
            String str = equations[j].stringifyEquation(true, maxResultPadding);
            System.out.println(str);
        }

    }
}
