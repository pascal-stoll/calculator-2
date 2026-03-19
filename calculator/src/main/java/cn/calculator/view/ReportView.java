package cn.calculator.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ReportView extends JFrame {

    private final JTextArea reportArea;
    private final JButton quitButton;

    public ReportView(String reportText) {
        setTitle("Test Report");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(785, 590);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Test Report", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        reportArea = new JTextArea(reportText);
        reportArea.setEditable(false);
        reportArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(reportArea);

        quitButton = new JButton("Quit");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(quitButton);

        setLayout(new BorderLayout(10, 10));
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addQuitListener(ActionListener listener) {
        quitButton.addActionListener(listener);
    }
}
