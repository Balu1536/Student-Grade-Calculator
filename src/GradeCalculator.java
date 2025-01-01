import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradeCalculator extends JFrame {
    private JTextField numGradesField;
    private JPanel gradePanel;
    private JButton submitButton;
    private JButton calculateButton;
    private JButton clearButton; // New clear button
    private int numGrades;

    public GradeCalculator() {
        setTitle("Student Grade Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background

        JLabel label = new JLabel("Enter the number of grades:");
        label.setForeground(new Color(51, 51, 51)); // Dark gray text
        add(label);

        numGradesField = new JTextField(10);
        add(numGradesField);

        submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(76, 175, 80)); // Green background
        submitButton.setForeground(Color.WHITE); // White text
        add(submitButton);

        gradePanel = new JPanel();
        gradePanel.setLayout(new BoxLayout(gradePanel, BoxLayout.Y_AXIS));
        gradePanel.setBackground(new Color(255, 255, 255)); // White background
        add(gradePanel);

        calculateButton = new JButton("Calculate Average");
        calculateButton.setEnabled(false);
        calculateButton.setBackground(new Color(39, 200, 21));
        calculateButton.setForeground(Color.WHITE); // White text
        // Do not add the button here; it will be added later

        clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(255, 87, 34)); // Red background
        clearButton.setForeground(Color.WHITE); // White text
        add(clearButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateAverage();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    private void handleSubmit() {
        try {
            numGrades = Integer.parseInt(numGradesField.getText());
            if (numGrades <= 0) {
                throw new NumberFormatException();
            }
            gradePanel.removeAll(); // Clear previous grade fields
            for (int i = 0; i < numGrades; i++) {
                JTextField gradeField = new JTextField(10);
                gradePanel.add(new JLabel("Grade " + (i + 1) + ":"));
                gradePanel.add(gradeField);
            }

            // Add the Calculate Average button after all grades
            gradePanel.add(calculateButton); // Add the button to the grade panel
            calculateButton.setEnabled(true); // Enable the button

            gradePanel.revalidate(); // Refresh the panel
            gradePanel.repaint(); // Repaint the panel
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid positive number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateAverage() {
        double total = 0;
        int count = 0;

        for (Component comp : gradePanel.getComponents()) {
            if (comp instanceof JTextField) {
                try {
                    double grade = Double.parseDouble(((JTextField) comp).getText());
                    total += grade;
                    count++;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter valid grades.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        if (count > 0) {
            double average = total / count;
            JOptionPane.showMessageDialog(this, "The average grade is: " + String.format("%.2f", average), "Average Grade", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No valid grades entered.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearFields() {
        numGradesField.setText("");
        gradePanel.removeAll();
        gradePanel.revalidate();
        gradePanel.repaint();
        calculateButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GradeCalculator calculator = new GradeCalculator();
            calculator.setVisible(true);
        });
    }
}