package cn.calculator.report;

import cn.calculator.MathTest.AnswerStatus;
import cn.calculator.MathTest.MathTest;
import cn.calculator.MathTest.Question;
import cn.calculator.calculation.Equation;
import cn.calculator.view.EquationPrinter;

/**
 * Builds the final report of the quiz.
 * It formats all recorded question results, the total score
 * and the elapsed time into a readable text report.
 */
public class MathTestReport {

    private final MathTest test;
    private final long duration;

    public MathTestReport(final MathTest test, final long durationInSec) {
        this.test = test;
        this.duration = durationInSec;
    }

    public String buildReport() {
        StringBuilder builder = new StringBuilder();

        builder.append("Test Report\n");
        builder.append("===========\n\n");

        final Equation[] equations = test.questions
            .stream()
            .map(question -> question.equation)
            .toArray(Equation[]::new);

        final int maxOperatorCount = EquationPrinter.maxOperatorCount(equations);
        final int maxResultLength = EquationPrinter.maxResultLength(equations);

        final EquationPrinter eqPrinter = new EquationPrinter(maxOperatorCount, maxResultLength);
        for (Question question : test.questions) {
            String answerString = null;
            switch (question.status) {
                case ANSWERED:
                    answerString = question.answer.get();
                    break;
                case SKIPPED:
                    answerString = "[skipped]";
                    break;
                case INPUT_ERROR:
                    answerString = "[input error]";
                    break;
                case NOT_ANSWERED:
                    break;
            }
            
            if(answerString == null) {
                throw new IllegalStateException("Should not show report if unanswered.");
            }

            builder.append(question.questionNumber).append(") ")
                    .append(eqPrinter.print(question.equation, true))
                    .append("       Your Answer: ")
                    .append(answerString)
                    .append("        Correct answer: ")
                    .append(question.equation.getResult())
                    .append("\n\n");
        }

        builder.append("===========\n\n");

        final int correctAnswers = this.test.questions.stream()
            .filter(question -> question.status == AnswerStatus.ANSWERED && question.correctAnswer.get())
            .mapToInt(e -> 1)
            .sum();

        builder.append("Total correct answers: ")
                .append(correctAnswers)
                .append(" / ")
                .append(this.test.questions.size())
                .append("\n");

        builder.append("Time: ")
                .append(this.duration)
                .append(" seconds");

        return builder.toString();
    }
}
