import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CalendarApp {

    private JFrame frame;
    private JPanel calendarPanel, eventPanel;
    private JTextArea eventTextArea;
    private JTextField eventField;
    private JButton addEventButton, showEventsButton;
    private JComboBox<String> viewModeComboBox;
    private HashMap<String, String> eventsMap; // Store events by date

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalendarApp().createAndShowGUI());
    }

    public void createAndShowGUI() {
        eventsMap = new HashMap<>();

        frame = new JFrame("Calendar with Event Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Calendar Panel
        calendarPanel = new JPanel();
        calendarPanel.setLayout(new GridLayout(7, 7)); // 7 rows for days of the week

        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : daysOfWeek) {
            calendarPanel.add(new JLabel(day, SwingConstants.CENTER));
        }

        // Populate the calendar with days
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        for (int i = 1; i <= 31; i++) {
            calendarPanel.add(createDayButton(i));
        }

        JScrollPane calendarScroll = new JScrollPane(calendarPanel);
        frame.add(calendarScroll, BorderLayout.NORTH);

        // Event Panel
        eventPanel = new JPanel();
        eventPanel.setLayout(new BorderLayout());

        eventField = new JTextField();
        eventPanel.add(new JLabel("Event Description: "), BorderLayout.NORTH);
        eventPanel.add(eventField, BorderLayout.CENTER);

        addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> addEvent());

        eventPanel.add(addEventButton, BorderLayout.SOUTH);

        frame.add(eventPanel, BorderLayout.WEST);

        // Event View Panel
        JPanel viewPanel = new JPanel();
        viewModeComboBox = new JComboBox<>(new String[]{"Day View", "Week View", "Month View"});
        viewModeComboBox.addActionListener(e -> showEventsByView());
        viewPanel.add(viewModeComboBox);

        showEventsButton = new JButton("Show Events");
        showEventsButton.addActionListener(e -> showEventsByView());
        viewPanel.add(showEventsButton);

        frame.add(viewPanel, BorderLayout.SOUTH);

        // Event Text Area
        eventTextArea = new JTextArea(10, 30);
        eventTextArea.setEditable(false);
        JScrollPane eventScroll = new JScrollPane(eventTextArea);
        frame.add(eventScroll, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JButton createDayButton(int day) {
        JButton dayButton = new JButton(String.valueOf(day));
        dayButton.addActionListener(e -> showEventDetails(day));
        return dayButton;
    }

    private void addEvent() {
        String eventDescription = eventField.getText().trim();
        if (!eventDescription.isEmpty()) {
            String selectedDate = new SimpleDateFormat("dd").format(new Date());
            eventsMap.put(selectedDate, eventDescription);
            eventField.setText("");
            JOptionPane.showMessageDialog(frame, "Event added successfully.");
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a valid event description.");
        }
    }

    private void showEventDetails(int day) {
        String dayStr = String.valueOf(day);
        if (eventsMap.containsKey(dayStr)) {
            eventTextArea.setText("Event on " + dayStr + ": " + eventsMap.get(dayStr));
        } else {
            eventTextArea.setText("No events for this day.");
        }
    }

    private void showEventsByView() {
        String viewMode = (String) viewModeComboBox.getSelectedItem();
        StringBuilder events = new StringBuilder();
        events.append(viewMode).append(":\n");

        for (String date : eventsMap.keySet()) {
            events.append("Date: ").append(date).append(", Event: ").append(eventsMap.get(date)).append("\n");
        }

        eventTextArea.setText(events.toString());
    }
}
