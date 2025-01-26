import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class VotingSystem {
    private JFrame frame;
    private JLabel titleLabel;
    private JRadioButton candidate1, candidate2, candidate3, candidate4;
    private ButtonGroup candidatesGroup;
    private JButton voteButton;
    private HashMap<String, Integer> votes;

    public VotingSystem() {
        // Initialize the vote counts
        votes = new HashMap<>();
        votes.put("Candidate 1", 0);
        votes.put("Candidate 2", 0);
        votes.put("Candidate 3", 0);
        votes.put("Candidate 4", 0);

        // Create the main frame
        frame = new JFrame("Voting System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Create the title label
        titleLabel = new JLabel("Vote for Your Favorite Candidate!", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Create the panel for candidates
        JPanel candidatesPanel = new JPanel();
        candidatesPanel.setLayout(new GridLayout(4, 1, 10, 10));
        candidate1 = new JRadioButton("Candidate 1");
        candidate2 = new JRadioButton("Candidate 2");
        candidate3 = new JRadioButton("Candidate 3");
        candidate4 = new JRadioButton("Candidate 4");
        candidatesGroup = new ButtonGroup();
        candidatesGroup.add(candidate1);
        candidatesGroup.add(candidate2);
        candidatesGroup.add(candidate3);
        candidatesGroup.add(candidate4);
        candidatesPanel.add(candidate1);
        candidatesPanel.add(candidate2);
        candidatesPanel.add(candidate3);
        candidatesPanel.add(candidate4);
        frame.add(candidatesPanel, BorderLayout.CENTER);

        // Create the vote button
        voteButton = new JButton("Vote");
        voteButton.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(voteButton, BorderLayout.SOUTH);

        // Add action listener for voting
        voteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitVote();
            }
        });

        // Display the frame
        frame.setVisible(true);
    }

    private void submitVote() {
        String selectedCandidate = null;

        // Get the selected candidate
        if (candidate1.isSelected()) {
            selectedCandidate = "Candidate 1";
        } else if (candidate2.isSelected()) {
            selectedCandidate = "Candidate 2";
        } else if (candidate3.isSelected()) {
            selectedCandidate = "Candidate 3";
        } else if (candidate4.isSelected()) {
            selectedCandidate = "Candidate 4";
        }

        if (selectedCandidate == null) {
            JOptionPane.showMessageDialog(frame, "Please select a candidate before voting!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Increment the vote count
        votes.put(selectedCandidate, votes.get(selectedCandidate) + 1);

        // Display a thank-you message and end voting
        JOptionPane.showMessageDialog(frame, "Thank you for voting!", "Vote Submitted", JOptionPane.INFORMATION_MESSAGE);

        // Show results
        showResults();
    }

    private void showResults() {
        // Calculate the winner
        String winner = null;
        int maxVotes = 0;
        for (String candidate : votes.keySet()) {
            int candidateVotes = votes.get(candidate);
            if (candidateVotes > maxVotes) {
                maxVotes = candidateVotes;
                winner = candidate;
            }
        }

        // Display the winner
        StringBuilder resultMessage = new StringBuilder("Voting Results:\n");
        for (String candidate : votes.keySet()) {
            resultMessage.append(candidate).append(": ").append(votes.get(candidate)).append(" votes\n");
        }
        resultMessage.append("\nWinner: ").append(winner);

        JOptionPane.showMessageDialog(frame, resultMessage.toString(), "Voting Results", JOptionPane.INFORMATION_MESSAGE);

        // Close the application
        frame.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VotingSystem::new);
    }
}
