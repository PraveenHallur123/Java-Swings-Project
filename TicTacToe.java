import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe {
    private JFrame frame;
    private JButton[][] buttons;
    private boolean isPlayerXTurn;
    private boolean isSinglePlayerMode;
    private char[][] board;

    public TicTacToe() {
        // Initialize the board
        board = new char[3][3];
        resetBoard();

        // Create the main frame
        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Tic-Tac-Toe", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(headerLabel, BorderLayout.NORTH);

        // Game Grid
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 3));
        buttons = new JButton[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                buttons[i][j].setFocusPainted(false);
                int row = i, col = j;
                buttons[i][j].addActionListener(e -> handleButtonClick(row, col));
                gridPanel.add(buttons[i][j]);
            }
        }
        frame.add(gridPanel, BorderLayout.CENTER);

        // Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetGame());

        JButton singlePlayerButton = new JButton("Single Player");
        singlePlayerButton.addActionListener(e -> startSinglePlayerMode());

        JButton multiPlayerButton = new JButton("Multiplayer");
        multiPlayerButton.addActionListener(e -> startMultiplayerMode());

        controlPanel.add(singlePlayerButton);
        controlPanel.add(multiPlayerButton);
        controlPanel.add(resetButton);
        frame.add(controlPanel, BorderLayout.SOUTH);

        // Initialize game state
        isPlayerXTurn = true;
        isSinglePlayerMode = false;

        // Show the frame
        frame.setVisible(true);
    }

    private void handleButtonClick(int row, int col) {
        if (board[row][col] == '\0' && !isGameOver()) {
            board[row][col] = isPlayerXTurn ? 'X' : 'O';
            buttons[row][col].setText(String.valueOf(board[row][col]));
            isPlayerXTurn = !isPlayerXTurn;

            if (checkWin('X')) {
                showResult("Player X wins!");
                return;
            } else if (checkWin('O')) {
                showResult("Player O wins!");
                return;
            } else if (isBoardFull()) {
                showResult("It's a draw!");
                return;
            }

            if (isSinglePlayerMode && !isPlayerXTurn) {
                makeComputerMove();
            }
        }
    }

    private void makeComputerMove() {
        // Simple AI: Find the first empty cell and play
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\0') {
                    board[i][j] = 'O';
                    buttons[i][j].setText("O");
                    isPlayerXTurn = true;

                    if (checkWin('O')) {
                        showResult("Computer wins!");
                    }
                    return;
                }
            }
        }
    }

    private boolean checkWin(char player) {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) || // Row
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) { // Column
                return true;
            }
        }
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) || // Diagonal 1
               (board[0][2] == player && board[1][1] == player && board[2][0] == player);   // Diagonal 2
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isGameOver() {
        return checkWin('X') || checkWin('O') || isBoardFull();
    }

    private void showResult(String message) {
        JOptionPane.showMessageDialog(frame, message);
        resetGame();
    }

    private void resetGame() {
        resetBoard();
        isPlayerXTurn = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '\0';
            }
        }
    }

    private void startSinglePlayerMode() {
        resetGame();
        isSinglePlayerMode = true;
        JOptionPane.showMessageDialog(frame, "Single Player Mode Activated! You are Player X.");
    }

    private void startMultiplayerMode() {
        resetGame();
        isSinglePlayerMode = false;
        JOptionPane.showMessageDialog(frame, "Multiplayer Mode Activated!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
