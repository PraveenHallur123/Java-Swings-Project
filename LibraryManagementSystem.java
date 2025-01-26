import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class LibraryManagementSystem {
    private JFrame frame;
    private JList<String> bookList;
    private DefaultListModel<String> bookListModel;
    private JButton borrowButton;
    private JButton returnButton;
    private JLabel statusLabel;
    private Map<String, Boolean> books;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryManagementSystem().createAndShowGUI());
    }

    public void createAndShowGUI() {
        frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Initialize books
        books = new HashMap<>();
        books.put("The Great Gatsby", true);
        books.put("To Kill a Mockingbird", true);
        books.put("1984", true);
        books.put("Pride and Prejudice", true);
        books.put("The Catcher in the Rye", true);

        // Initialize book list model and JList
        bookListModel = new DefaultListModel<>();
        for (String book : books.keySet()) {
            bookListModel.addElement(book + (books.get(book) ? " - Available" : " - Borrowed"));
        }

        bookList = new JList<>(bookListModel);
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(bookList);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Borrow and return buttons
        JPanel buttonPanel = new JPanel();
        borrowButton = new JButton("Borrow");
        returnButton = new JButton("Return");
        buttonPanel.add(borrowButton);
        buttonPanel.add(returnButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Status label
        statusLabel = new JLabel("Select a book to borrow or return.");
        frame.add(statusLabel, BorderLayout.NORTH);

        // Button actions
        borrowButton.addActionListener(e -> borrowBook());
        returnButton.addActionListener(e -> returnBook());

        frame.setVisible(true);
    }

    private void borrowBook() {
        String selectedBook = bookList.getSelectedValue();
        if (selectedBook == null) {
            statusLabel.setText("No book selected.");
            return;
        }

        String bookTitle = selectedBook.split(" - ")[0];
        if (books.get(bookTitle)) {
            books.put(bookTitle, false);  // Mark book as borrowed
            updateBookList();
            statusLabel.setText("You have borrowed: " + bookTitle);
        } else {
            statusLabel.setText("Sorry, this book is already borrowed.");
        }
    }

    private void returnBook() {
        String selectedBook = bookList.getSelectedValue();
        if (selectedBook == null) {
            statusLabel.setText("No book selected.");
            return;
        }

        String bookTitle = selectedBook.split(" - ")[0];
        if (!books.get(bookTitle)) {
            books.put(bookTitle, true);  // Mark book as available
            updateBookList();
            statusLabel.setText("You have returned: " + bookTitle);
        } else {
            statusLabel.setText("This book was not borrowed.");
        }
    }

    private void updateBookList() {
        bookListModel.clear();
        for (String book : books.keySet()) {
            bookListModel.addElement(book + (books.get(book) ? " - Available" : " - Borrowed"));
        }
    }
}
