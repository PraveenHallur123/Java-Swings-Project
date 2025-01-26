import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MovieTicketBookingSystem {
    private JFrame frame;
    private JComboBox<String> movieComboBox;
    private JComboBox<String> timeComboBox;
    private JPanel seatPanel;
    private JButton confirmButton;
    private JLabel statusLabel;
    private ArrayList<ArrayList<JButton>> seats;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MovieTicketBookingSystem().createAndShowGUI());
    }

    public void createAndShowGUI() {
        frame = new JFrame("Movie Ticket Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));

        JLabel movieLabel = new JLabel("Select Movie:");
        movieComboBox = new JComboBox<>(new String[] {"Choo Mantar", "Sanju Weds Geetha 2", "Forest", "UI","Max","Bagheera","Martin","Krishnam Pranaya Sakhi","Bhairathi Ranagal","Bheema"});
        JLabel timeLabel = new JLabel("Select Showtime:");
        timeComboBox = new JComboBox<>(new String[] {"10:00 AM", "1:00 PM", "4:00 PM", "7:00 PM"});

        topPanel.add(movieLabel);
        topPanel.add(movieComboBox);
        topPanel.add(timeLabel);
        topPanel.add(timeComboBox);

        frame.add(topPanel, BorderLayout.NORTH);

        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(5, 5));  // 5x5 grid of seats
        seats = new ArrayList<>();

        // Create 5x5 grid of seats
        for (int i = 0; i < 5; i++) {
            ArrayList<JButton> row = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                JButton seatButton = new JButton("Seat " + (i * 5 + j + 1));
                seatButton.setBackground(Color.GREEN);
                seatButton.setForeground(Color.WHITE);
                seatButton.setEnabled(true);
                int finalI = i;
                int finalJ = j;
                seatButton.addActionListener(e -> toggleSeat(finalI, finalJ));
                row.add(seatButton);
                seatPanel.add(seatButton);
            }
            seats.add(row);
        }

        JScrollPane seatScrollPane = new JScrollPane(seatPanel);
        frame.add(seatScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        confirmButton = new JButton("Confirm Booking");
        statusLabel = new JLabel("Select seats to book");
        bottomPanel.add(statusLabel);
        bottomPanel.add(confirmButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Confirm booking action
        confirmButton.addActionListener(e -> confirmBooking());

        frame.setVisible(true);
    }

    private void toggleSeat(int row, int col) {
        JButton seatButton = seats.get(row).get(col);
        if (seatButton.getBackground() == Color.GREEN) {
            seatButton.setBackground(Color.RED);  // Mark seat as selected
            statusLabel.setText("Selected: " + seatButton.getText());
        } else {
            seatButton.setBackground(Color.GREEN);  // Deselect seat
            statusLabel.setText("Select seats to book");
        }
    }

    private void confirmBooking() {
        String movie = (String) movieComboBox.getSelectedItem();
        String time = (String) timeComboBox.getSelectedItem();
        StringBuilder selectedSeats = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                JButton seatButton = seats.get(i).get(j);
                if (seatButton.getBackground() == Color.RED) {
                    selectedSeats.append(seatButton.getText()).append(", ");
                }
            }
        }

        if (selectedSeats.length() == 0) {
            JOptionPane.showMessageDialog(frame, "No seats selected. Please select at least one seat.");
            return;
        }

        // Remove last comma and space
        selectedSeats.setLength(selectedSeats.length() - 2);

        int confirmation = JOptionPane.showConfirmDialog(frame, "You are booking the following seats for " +
                movie + " at " + time + ": " + selectedSeats.toString() + ". Confirm?", "Confirm Booking", JOptionPane.YES_NO_OPTION);
        
        if (confirmation == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(frame, "Booking confirmed! Enjoy your movie.");
            resetSeats();  // Reset seats after confirmation
        }
    }

    private void resetSeats() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                seats.get(i).get(j).setBackground(Color.GREEN);
            }
        }
        statusLabel.setText("Select seats to book");
    }
}
