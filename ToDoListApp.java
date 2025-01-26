import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ToDoListApp {
    private JFrame frame;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskInput;
    private ArrayList<String> tasks;

    public ToDoListApp() {
        // Initialize the task list
        tasks = new ArrayList<>();
        taskListModel = new DefaultListModel<>();

        // Create the main frame
        frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("To-Do List", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(headerLabel, BorderLayout.NORTH);

        // Task input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        taskInput = new JTextField(20);
        JButton addButton = new JButton("Add Task");

        inputPanel.add(taskInput);
        inputPanel.add(addButton);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Task list panel
        taskList = new JList<>(taskListModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        frame.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton deleteButton = new JButton("Delete Task");
        JButton clearButton = new JButton("Clear All");

        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        frame.add(buttonPanel, BorderLayout.EAST);

        // Action listeners
        addButton.addActionListener(e -> addTask());
        deleteButton.addActionListener(e -> deleteTask());
        clearButton.addActionListener(e -> clearTasks());

        // Show the frame
        frame.setVisible(true);
    }

    // Add a task to the list
    private void addTask() {
        String task = taskInput.getText().trim();
        if (!task.isEmpty()) {
            tasks.add(task);
            taskListModel.addElement(task);
            taskInput.setText("");
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a task!");
        }
    }

    // Delete the selected task
    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            tasks.remove(selectedIndex);
            taskListModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a task to delete!");
        }
    }

    // Clear all tasks
    private void clearTasks() {
        tasks.clear();
        taskListModel.clear();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoListApp::new);
    }
}
