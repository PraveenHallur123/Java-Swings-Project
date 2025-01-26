import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentGradeManagementSystem {

    private JFrame frame;
    private JPanel panel;
    private JTextField studentNameField, subjectField, gradeField;
    private JTextArea resultArea;
    private JButton addButton, calculateButton, showReportButton;
    private ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGradeManagementSystem().createAndShowGUI());
    }

    public void createAndShowGUI() {
        frame = new JFrame("Student Grade Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        inputPanel.add(new JLabel("Student Name:"));
        studentNameField = new JTextField();
        inputPanel.add(studentNameField);

        inputPanel.add(new JLabel("Subject:"));
        subjectField = new JTextField();
        inputPanel.add(subjectField);

        inputPanel.add(new JLabel("Grade (0-100):"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Grade");
        calculateButton = new JButton("Calculate Averages");
        showReportButton = new JButton("Show Report");

        addButton.addActionListener(e -> addGrade());
        calculateButton.addActionListener(e -> calculateAverages());
        showReportButton.addActionListener(e -> showReport());

        buttonPanel.add(addButton);
        buttonPanel.add(calculateButton);
        buttonPanel.add(showReportButton);
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Result display area
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        frame.add(scrollPane, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Adds a grade to the student
    private void addGrade() {
        String name = studentNameField.getText().trim();
        String subject = subjectField.getText().trim();
        String gradeText = gradeField.getText().trim();

        if (name.isEmpty() || subject.isEmpty() || gradeText.isEmpty()) {
            resultArea.setText("Please fill all fields.");
            return;
        }

        try {
            int grade = Integer.parseInt(gradeText);
            if (grade < 0 || grade > 100) {
                resultArea.setText("Grade must be between 0 and 100.");
                return;
            }

            // Check if student already exists, if yes, add the grade to their list of subjects
            Student student = findStudentByName(name);
            if (student == null) {
                student = new Student(name);
                students.add(student);
            }

            student.addGrade(subject, grade);
            resultArea.setText("Grade added for " + name + " in " + subject + ".");
            studentNameField.setText("");
            subjectField.setText("");
            gradeField.setText("");
        } catch (NumberFormatException e) {
            resultArea.setText("Invalid grade input.");
        }
    }

    // Finds a student by name
    private Student findStudentByName(String name) {
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }

    // Calculates and displays the averages for each student
    private void calculateAverages() {
        StringBuilder report = new StringBuilder();
        for (Student student : students) {
            report.append(student.getName()).append(" - Average: ").append(student.calculateAverage()).append("\n");
        }
        resultArea.setText(report.toString());
    }

    // Displays a report for the student or subject
    private void showReport() {
        StringBuilder report = new StringBuilder();
        for (Student student : students) {
            report.append(student.getName()).append(" Grades:\n");
            for (String subject : student.getGrades().keySet()) {
                report.append("  ").append(subject).append(": ").append(student.getGrades().get(subject))
                        .append(" - ").append(student.isPassed(subject) ? "Pass" : "Fail").append("\n");
            }
        }
        resultArea.setText(report.toString());
    }

    // Student class to hold student data and grades
    static class Student {
        private String name;
        private ArrayList<SubjectGrade> subjectGrades;

        public Student(String name) {
            this.name = name;
            subjectGrades = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void addGrade(String subject, int grade) {
            subjectGrades.add(new SubjectGrade(subject, grade));
        }

        public ArrayList<String> getSubjects() {
            ArrayList<String> subjects = new ArrayList<>();
            for (SubjectGrade subjectGrade : subjectGrades) {
                subjects.add(subjectGrade.getSubject());
            }
            return subjects;
        }

        public java.util.Map<String, Integer> getGrades() {
            java.util.Map<String, Integer> grades = new java.util.HashMap<>();
            for (SubjectGrade subjectGrade : subjectGrades) {
                grades.put(subjectGrade.getSubject(), subjectGrade.getGrade());
            }
            return grades;
        }

        public double calculateAverage() {
            if (subjectGrades.isEmpty()) {
                return 0;
            }
            int total = 0;
            for (SubjectGrade subjectGrade : subjectGrades) {
                total += subjectGrade.getGrade();
            }
            return total / (double) subjectGrades.size();
        }

        public boolean isPassed(String subject) {
            for (SubjectGrade subjectGrade : subjectGrades) {
                if (subjectGrade.getSubject().equalsIgnoreCase(subject)) {
                    return subjectGrade.getGrade() >= 50; // Assume 50 is passing grade
                }
            }
            return false;
        }
    }

    // SubjectGrade class to hold subject and grade
    static class SubjectGrade {
        private String subject;
        private int grade;

        public SubjectGrade(String subject, int grade) {
            this.subject = subject;
            this.grade = grade;
        }

        public String getSubject() {
            return subject;
        }

        public int getGrade() {
            return grade;
        }
    }
}
