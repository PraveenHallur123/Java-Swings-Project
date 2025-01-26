import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class PasswordStrengthChecker {
    private JFrame frame;
    private JTextField passwordField;
    private JLabel strengthLabel;
    private JButton checkButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PasswordStrengthChecker().createAndShowGUI());
    }

    public void createAndShowGUI() {
        frame = new JFrame("Password Strength Checker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel passwordLabel = new JLabel("Enter Password:");
        passwordField = new JTextField();
        checkButton = new JButton("Check Strength");

        strengthLabel = new JLabel("Strength: ");
        strengthLabel.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(checkButton);
        panel.add(strengthLabel);

        frame.add(panel, BorderLayout.CENTER);

        checkButton.addActionListener(e -> checkPasswordStrength());

        frame.setVisible(true);
    }

    private void checkPasswordStrength() {
        String password = passwordField.getText();

        if (password.isEmpty()) {
            strengthLabel.setText("Strength: Enter a password");
            strengthLabel.setForeground(Color.RED);
            return;
        }

        int strengthScore = 0;

        // Check password length
        if (password.length() >= 8) {
            strengthScore++;
        }

        // Check for uppercase letters
        if (password.matches(".*[A-Z].*")) {
            strengthScore++;
        }

        // Check for lowercase letters
        if (password.matches(".*[a-z].*")) {
            strengthScore++;
        }

        // Check for digits
        if (password.matches(".*\\d.*")) {
            strengthScore++;
        }

        // Check for special characters
        if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            strengthScore++;
        }

        // Determine password strength
        if (strengthScore <= 2) {
            strengthLabel.setText("Strength: Weak");
            strengthLabel.setForeground(Color.RED);
        } else if (strengthScore == 3) {
            strengthLabel.setText("Strength: Medium");
            strengthLabel.setForeground(Color.ORANGE);
        } else {
            strengthLabel.setText("Strength: Strong");
            strengthLabel.setForeground(Color.GREEN);
        }
    }
}
