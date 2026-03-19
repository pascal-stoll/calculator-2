package cn.calculator.report;

import java.util.List;

public class ReportBuilder {

    public String buildReport(List<QuestionResult> results, int totalQuestions, int correctAnswers, long durationInSec) {
        StringBuilder builder = new StringBuilder();

        builder.append("Test Report\n");
        builder.append("===========\n\n");

        for (int i = 0; i < results.size(); i++) {
            QuestionResult result = results.get(i);

            builder.append(i + 1).append(") ")
                    .append(result.getQuestionText())
                    .append("       Your Answer: ")
                    .append(result.getUserInput());

            if (!result.isCorrect()) {
                builder.append("        Correct answer: ")
                        .append(result.getCorrectResult());
            }

            builder.append("\n\n");
        }

        builder.append("===========\n\n");

        builder.append("Total correct answers: ")
                .append(correctAnswers)
                .append(" / ")
                .append(totalQuestions)
                .append("\n");

        builder.append("Time: ")
                .append(durationInSec)
                .append(" seconds");

        return builder.toString();
    }
}
