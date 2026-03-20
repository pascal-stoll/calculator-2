package cn.calculator.view;

import javax.swing.*;
import java.awt.*;

/**
 * The graphical user interface of the quiz report.
 * It shows the final result report and a button to close the application.
 */
public class ReportView extends JFrame {

    private final JTextArea reportArea;
    private final JButton quitButton;

    public ReportView(String reportText) {
        // Basic window configuration
        setTitle("Test Report");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(785, 590);
        setLocationRelativeTo(null);

        // Title label displayed at the top of the report window
        JLabel titleLabel = new JLabel("Test Report", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Text area that shows the generated report
        reportArea = new JTextArea(reportText);
        reportArea.setEditable(false);
        reportArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        // Scroll pane allows the user to view longer reports
        JScrollPane scrollPane = new JScrollPane(reportArea);

        // Quit button to close the application after viewing the report
        quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        

        // Panel containing the button at the bottom of the window
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(quitButton);

        // Main layout of the report window
        setLayout(new BorderLayout(10, 10));
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
