import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class StudentAttendanceSystem {

    private JFrame frame;
    private JPanel panel;
    private JComboBox<String> studentComboBox;
    private JButton presentButton, absentButton, showReportButton;
    private JTextArea resultArea;
    private ArrayList<Student> students;
    private HashMap<String, HashMap<String, String>> attendanceRecords;
    private JTextField dateField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentAttendanceSystem().createAndShowGUI());
    }

    public void createAndShowGUI() {
        students = new ArrayList<>();
        attendanceRecords = new HashMap<>();
        
        // Sample students data
        students.add(new Student("John Doe"));
        students.add(new Student("Jane Smith"));
        students.add(new Student("David Brown"));

        frame = new JFrame("Student Attendance System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Input panel for selecting student and date
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        inputPanel.add(new JLabel("Select Student:"));
        studentComboBox = new JComboBox<>();
        for (Student student : students) {
            studentComboBox.addItem(student.getName());
        }
        inputPanel.add(studentComboBox);

        inputPanel.add(new JLabel("Enter Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Buttons for marking attendance and showing report
        JPanel buttonPanel = new JPanel();
        presentButton = new JButton("Mark Present");
        absentButton = new JButton("Mark Absent");
        showReportButton = new JButton("Show Attendance Report");

        presentButton.addActionListener(e -> markAttendance("Present"));
        absentButton.addActionListener(e -> markAttendance("Absent"));
        showReportButton.addActionListener(e -> showAttendanceReport());

        buttonPanel.add(presentButton);
        buttonPanel.add(absentButton);
        buttonPanel.add(showReportButton);
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Result display area
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        frame.add(scrollPane, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void markAttendance(String status) {
        String studentName = (String) studentComboBox.getSelectedItem();
        String date = dateField.getText().trim();

        if (date.isEmpty()) {
            resultArea.setText("Please enter a valid date.");
            return;
        }

        if (!attendanceRecords.containsKey(date)) {
            attendanceRecords.put(date, new HashMap<>());
        }

        attendanceRecords.get(date).put(studentName, status);

        resultArea.setText(studentName + " marked as " + status + " on " + date);
    }

    private void showAttendanceReport() {
        StringBuilder report = new StringBuilder();
        String date = dateField.getText().trim();

        if (date.isEmpty()) {
            resultArea.setText("Please enter a valid date.");
            return;
        }

        if (attendanceRecords.containsKey(date)) {
            HashMap<String, String> attendance = attendanceRecords.get(date);
            for (String studentName : attendance.keySet()) {
                report.append(studentName).append(" was ").append(attendance.get(studentName)).append(" on ").append(date).append("\n");
            }
        } else {
            report.append("No attendance records found for the date ").append(date).append(".");
        }

        resultArea.setText(report.toString());
    }

    // Student class to hold student data
    static class Student {
        private String name;

        public Student(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
