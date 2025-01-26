import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizGame {
    private JFrame frame;
    private JLabel questionLabel;
    private JRadioButton option1, option2, option3, option4;
    private ButtonGroup optionsGroup;
    private JButton nextButton;
    private int currentQuestion = 0, score = 0;

    // Array to hold questions, options, and answers
    private String[][] questions = {
            {"What is the capital of France?", "Berlin", "Madrid", "Paris", "Rome", "3"},
            {"Which planet is known as the Red Planet?", "Earth", "Mars", "Jupiter", "Venus", "2"},
            {"What is the largest mammal?", "Elephant", "Blue Whale", "Giraffe", "Shark", "2"},
            {"Who wrote 'Hamlet'?", "Charles Dickens", "William Shakespeare", "Leo Tolstoy", "J.K. Rowling", "2"},
            {"What is the square root of 64?", "6", "8", "10", "12", "2"}
    };

    public QuizGame() {
        // Create the main frame
        frame = new JFrame("Quiz Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

        // Create a panel for the question
        JPanel questionPanel = new JPanel();
        questionLabel = new JLabel("Question will appear here");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionPanel.add(questionLabel);
        frame.add(questionPanel, BorderLayout.NORTH);

        // Create a panel for the options
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1, 10, 10));
        option1 = new JRadioButton("Option 1");
        option2 = new JRadioButton("Option 2");
        option3 = new JRadioButton("Option 3");
        option4 = new JRadioButton("Option 4");
        optionsGroup = new ButtonGroup();
        optionsGroup.add(option1);
        optionsGroup.add(option2);
        optionsGroup.add(option3);
        optionsGroup.add(option4);
        optionsPanel.add(option1);
        optionsPanel.add(option2);
        optionsPanel.add(option3);
        optionsPanel.add(option4);
        frame.add(optionsPanel, BorderLayout.CENTER);

        // Create a panel for the next button
        JPanel buttonPanel = new JPanel();
        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        buttonPanel.add(nextButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Load the first question
        loadQuestion();

        // Add action listener to the next button
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                currentQuestion++;
                if (currentQuestion < questions.length) {
                    loadQuestion();
                } else {
                    showResult();
                }
            }
        });

        frame.setVisible(true);
    }

    private void loadQuestion() {
        // Set the question and options
        questionLabel.setText((currentQuestion + 1) + ". " + questions[currentQuestion][0]);
        option1.setText(questions[currentQuestion][1]);
        option2.setText(questions[currentQuestion][2]);
        option3.setText(questions[currentQuestion][3]);
        option4.setText(questions[currentQuestion][4]);
        optionsGroup.clearSelection();
    }

    private void checkAnswer() {
        // Get the correct answer
        int correctAnswer = Integer.parseInt(questions[currentQuestion][5]);

        // Check if the selected answer is correct
        if ((option1.isSelected() && correctAnswer == 1) ||
            (option2.isSelected() && correctAnswer == 2) ||
            (option3.isSelected() && correctAnswer == 3) ||
            (option4.isSelected() && correctAnswer == 4)) {
            score++;
        }
    }

    private void showResult() {
        // Show the final score
        JOptionPane.showMessageDialog(frame, "Quiz Over! Your score is: " + score + "/" + questions.length);
        frame.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizGame::new);
    }
}
