import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class CRUD extends JFrame {
    private JTextField textFieldFirstName;
    private JTextField textFieldLastName;
    private JTextField textFieldStudentNumber;
    private JTextField textFieldCourse;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton saveButton;
    private JTable dataTable;
    private DefaultTableModel tableModel;

    public CRUD() {
        // Set up the frame
        setTitle("Student Record");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and configure the layout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        gbc.insets = new Insets(10, 10, 0, 10); // Top padding for the first row

        // Add title or headline text
        JLabel titleLabel = new JLabel("Student Record");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font and size
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align text
        mainPanel.add(titleLabel, gbc);

        // Increment the gridy for the next component
        gbc.gridy++;
        gbc.gridwidth = 1; // Reset gridwidth for other components

        // Labels and text fields for First Name
        JLabel firstNameLabel = new JLabel("First Name:");
        mainPanel.add(firstNameLabel, gbc);

        textFieldFirstName = new JTextField(20); // Adjust the size as needed
        gbc.gridx = 1;
        mainPanel.add(textFieldFirstName, gbc);

        // Increment the gridy for the next component
        gbc.gridy++;
        gbc.gridx = 0;

        // Labels and text fields for Last Name
        JLabel lastNameLabel = new JLabel("Last Name:");
        mainPanel.add(lastNameLabel, gbc);

        textFieldLastName = new JTextField(20); // Adjust the size as needed
        gbc.gridx = 1;
        mainPanel.add(textFieldLastName, gbc);

        // Increment the gridy for the next component
        gbc.gridy++;
        gbc.gridx = 0;

        // Labels and text fields for Student Number
        JLabel studentNumberLabel = new JLabel("Student Number:");
        mainPanel.add(studentNumberLabel, gbc);

        textFieldStudentNumber = new JTextField(20); // Adjust the size as needed
        gbc.gridx = 1;
        mainPanel.add(textFieldStudentNumber, gbc);

        // Increment the gridy for the next component
        gbc.gridy++;
        gbc.gridx = 0;

        // Labels and text fields for Course
        JLabel courseLabel = new JLabel("Course:");
        mainPanel.add(courseLabel, gbc);

        textFieldCourse = new JTextField(20); // Adjust the size as needed
        gbc.gridx = 1;
        mainPanel.add(textFieldCourse, gbc);

        // Increment the gridy for the next component
        gbc.gridy++;
        gbc.gridx = 0;

        // Add buttons
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        searchButton = new JButton("Search");
        saveButton = new JButton("Save");

        Dimension buttonSize = new Dimension(100, addButton.getPreferredSize().height);
        addButton.setPreferredSize(buttonSize);
        updateButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        searchButton.setPreferredSize(buttonSize);
        saveButton.setPreferredSize(buttonSize);

        gbc.gridwidth = 1;
        mainPanel.add(addButton, gbc);
        gbc.gridx = 1;
        mainPanel.add(updateButton, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(deleteButton, gbc);
        gbc.gridx = 1;
        mainPanel.add(searchButton, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(saveButton, gbc);

        // Increment the gridy for the next component
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        // Add table
        String[] columnNames = {"First Name", "Last Name", "Student Number", "Course"};
        tableModel = new DefaultTableModel(columnNames, 0);
        dataTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setPreferredSize(new Dimension(450, 200));
        mainPanel.add(scrollPane, gbc);

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchStudent();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });

        // Load data from file on startup
        loadData();

        // Add the main panel to the frame
        add(mainPanel);

        // Pack the frame to fit its contents
        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void addStudent() {
        String firstName = textFieldFirstName.getText();
        String lastName = textFieldLastName.getText();
        String studentNumber = textFieldStudentNumber.getText();
        String course = textFieldCourse.getText();

        if (isStudentExist(studentNumber)) {
            JOptionPane.showMessageDialog(this, "Student with this number already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tableModel.addRow(new Object[]{firstName, lastName, studentNumber, course});
        clearFields();
    }

    private void updateStudent() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField firstNameField = new JTextField((String) tableModel.getValueAt(selectedRow, 0));
        JTextField lastNameField = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        JTextField studentNumberField = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        JTextField courseField = new JTextField((String) tableModel.getValueAt(selectedRow, 3));

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Student Number:"));
        panel.add(studentNumberField);
        panel.add(new JLabel("Course:"));
        panel.add(courseField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Update Student", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            tableModel.setValueAt(firstNameField.getText(), selectedRow, 0);
            tableModel.setValueAt(lastNameField.getText(), selectedRow, 1);
            tableModel.setValueAt(studentNumberField.getText(), selectedRow, 2);
            tableModel.setValueAt(courseField.getText(), selectedRow, 3);
        }
    }

    private void deleteStudent() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Delete Student", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
        }
    }

    private void searchStudent() {
        JTextField studentNumberField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(new JLabel("Student Number:"));
        panel.add(studentNumberField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Search Student", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String studentNumber = studentNumberField.getText();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 2).equals(studentNumber)) {
                    dataTable.setRowSelectionInterval(i, i);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Student not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean isStudentExist(String studentNumber) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 2).equals(studentNumber)) {
                return true;
            }
        }
        return false;
    }

    private void clearFields() {
        textFieldFirstName.setText("");
        textFieldLastName.setText("");
        textFieldStudentNumber.setText("");
        textFieldCourse.setText("");
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("student_data.csv"))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    writer.write((String) tableModel.getValueAt(i, j));
                    if (j < tableModel.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Data saved successfully!", "Save Data", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("student_data.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                tableModel.addRow(rowData);
            }
        } catch (IOException e) {
            // File not found or other error; just start with an empty table
        }
    }

    public static void main(String[] args) {
        // Run GUI construction in the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new CRUD());
    }
}
