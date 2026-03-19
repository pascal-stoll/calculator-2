package cn.calculator.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CalculatorView extends JFrame {

    private final JLabel titleLabel;
    private final JLabel equationLabel;
    private final JTextField answerField;
    private final JButton nextButton;
    private final JButton quitButton;
    private final JLabel statusLabel;

    public CalculatorView() {
        setTitle("Math Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 250);
        setLocationRelativeTo(null);

        titleLabel = new JLabel("Math Test", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        equationLabel = new JLabel("Equation will appear here", SwingConstants.CENTER);
        equationLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        answerField = new JTextField();
        nextButton = new JButton("Next");
        quitButton = new JButton("Quit");

        statusLabel = new JLabel("Question 1", SwingConstants.CENTER);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 10, 10));
        centerPanel.add(equationLabel);
        centerPanel.add(answerField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextButton);
        buttonPanel.add(quitButton);

        centerPanel.add(buttonPanel);

        setLayout(new BorderLayout(10, 10));
        add(titleLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    public void setEquationText(String text) {
        equationLabel.setText(text);
    }

    public String getAnswerText() {
        return answerField.getText();
    }

    public void clearAnswerField() {
        answerField.setText("");
    }

    public void focusAnswerField() {
        answerField.requestFocusInWindow();
    }

    public void setStatusText(String text) {
        statusLabel.setText(text);
    }

    public void addNextListener(ActionListener listener) {
        nextButton.addActionListener(listener);
        answerField.addActionListener(listener); // Enter im Textfeld macht auch "Next"
    }

    public void addQuitListener(ActionListener listener) {
        quitButton.addActionListener(listener);
    }

    public void disableInput() {
        answerField.setEnabled(false);
        nextButton.setEnabled(false);
    }
}