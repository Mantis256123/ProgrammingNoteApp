/**
 * GUI interface for the Note App program
 */
package AppGui;

import javax.swing.*;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import notes.*;
import notes.TestingNote.TestStatus;

/**
 * Main GUI class for the Note Management System providing user interface for creating, viewing, editing and managing notes.
 */
public class NoteAppGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Note currentNote;
    private List<Note> notes = new ArrayList<>();

    private JTextField titleField, descField, authorField;
    private JTextArea textArea;
    private JSpinner deadline;
    private JTextField languageField, snippetField;
    private JTextField testcaseField;
    private JCheckBox isMandatoryField;
    private JComboBox<TestingNote.TestStatus> testStatusCombo;
    private JComboBox<String> noteTypeCombo;
    private JLabel successMessage;

    /**
     * Constructs the main GUI window and initializes all panels.
     */
    public NoteAppGUI() {
        setTitle("Note App");
        setSize(500, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createWelcomePanel(), "welcome");
        mainPanel.add(createFormPanel(), "create");
        mainPanel.add(createViewPanel(), "view");

        add(mainPanel);
        cardLayout.show(mainPanel, "welcome");
        setVisible(true);
    }

    /**
     * Creates the welcome panel with navigation buttons.
     */
    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Note App", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel welcome = new JLabel("Choose an action:", SwingConstants.CENTER);
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton createBtn = new JButton("Create Note");
        JButton viewBtn = new JButton("View Notes");

        createBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        readFromFile();

        createBtn.addActionListener(e -> {
            resetFields();
            noteTypeCombo.setSelectedItem("Note");
            mainPanel.remove(mainPanel.getComponent(1));
            mainPanel.add(createFormPanel(), "create");
            cardLayout.show(mainPanel, "create");
        });

        viewBtn.addActionListener(e -> {
            readFromFile();
            mainPanel.remove(mainPanel.getComponent(2));
            mainPanel.add(createViewPanel(), "view");
            cardLayout.show(mainPanel, "view");

        });

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(35));
        panel.add(welcome);
        panel.add(Box.createVerticalStrut(15));
        panel.add(createBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(viewBtn);

        return panel;
    }
    
    /**
     * Resets all form fields to their default values.
     */
    public void resetFields() {
        titleField.setText("");
        descField.setText("");
        textArea.setText("");
        authorField.setText("");
        successMessage.setText("");
        deadline.setValue(new Date());
        if (languageField != null)
            languageField.setText("");
        if (snippetField != null)
            snippetField.setText("");
        if (testcaseField != null)
            testcaseField.setText("");
        if (isMandatoryField != null)
            isMandatoryField.setSelected(false);
    }
    
    /**
     * Switches to the view panel and refreshes the display.
     */
    private void switchToViewPanel() {
        SwingUtilities.invokeLater(() -> {
            mainPanel.remove(2);
            JPanel newViewPanel = createViewPanel();
            mainPanel.add(newViewPanel, "view");
            cardLayout.show(mainPanel, "view");
            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }

    /**
     * Creates the form panel for note creation and editing.
     */
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        noteTypeCombo = new JComboBox<>(new String[] { "Note", "ProgrammingNote", "TestingNote" });
        titleField = new JTextField();
        descField = new JTextField();
        textArea = new JTextArea(3, 10);
        authorField = new JTextField();
        successMessage = new JLabel();

        formPanel.add(new JLabel("Note Type:"));
        formPanel.add(noteTypeCombo);
        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descField);
        formPanel.add(new JLabel("Text:"));
        formPanel.add(textArea);
        formPanel.add(new JLabel("Author:"));
        formPanel.add(authorField);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date today = calendar.getTime();

        SpinnerDateModel deadlineModel = new SpinnerDateModel(today, today, null, Calendar.DAY_OF_MONTH);
        deadline = new JSpinner(deadlineModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(deadline, "yyyy-MM-dd");
        JFormattedTextField textField = editor.getTextField();
        textField.setEditable(false);
        deadline.setEditor(editor);

        formPanel.add(new JLabel("Deadline:"));
        formPanel.add(deadline);

        panel.add(formPanel);

        JPanel extraPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        noteTypeCombo.addActionListener(e -> {
            extraPanel.removeAll();
            if ("ProgrammingNote".equals(noteTypeCombo.getSelectedItem())) {
                languageField = new JTextField();
                snippetField = new JTextField();
                extraPanel.add(new JLabel("Language:"));
                extraPanel.add(languageField);
                extraPanel.add(new JLabel("Snippet:"));
                extraPanel.add(snippetField);
            } else if ("TestingNote".equals(noteTypeCombo.getSelectedItem())) {
                testcaseField = new JTextField();
                testStatusCombo = new JComboBox<>(TestingNote.TestStatus.values());
                isMandatoryField = new JCheckBox("Is Mandatory");
                extraPanel.add(new JLabel("Testcase:"));
                extraPanel.add(testcaseField);
                extraPanel.add(new JLabel("Test Status:"));
                extraPanel.add(testStatusCombo);
                extraPanel.add(isMandatoryField);
            }
            extraPanel.revalidate();
            extraPanel.repaint();
        });
        panel.add(extraPanel);

        successMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(10));
        panel.add(successMessage);

        JButton addBtn = new JButton("Add Note");
        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addBtn.addActionListener(e -> addNote());
        panel.add(Box.createVerticalStrut(10));
        panel.add(addBtn);

        JButton saveBtn = new JButton("Save Note");
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveBtn.addActionListener(e -> {
            saveToFile("Note saved");
        });
        panel.add(saveBtn);

        JButton loadBtn = new JButton("Load Note");
        loadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadBtn.addActionListener(e -> {
            readFromFile();
            resetFields();
            mainPanel.remove(mainPanel.getComponent(2));
            mainPanel.add(createViewPanel(), "view");
            cardLayout.show(mainPanel, "view");

        });
        panel.add(loadBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "welcome");
            resetFields();
        });
        panel.add(backBtn);

        return panel;
    }

    /**
     * Creates the view panel displaying all saved notes with edit and delete options.
     */
    private JPanel createViewPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel notesContainer = new JPanel();
        notesContainer.setLayout(new BoxLayout(notesContainer, BoxLayout.Y_AXIS));
        notesContainer.setBackground(Color.WHITE);

        if (notes.isEmpty()) {
            JLabel noNotesText = new JLabel("You have not created any notes", SwingConstants.CENTER);
            noNotesText.setFont(new Font("Arial", Font.BOLD, 14));
            noNotesText.setAlignmentX(Component.CENTER_ALIGNMENT);
            notesContainer.add(noNotesText);
            notesContainer.add(Box.createVerticalStrut(15));
        } else {
            for (int i = notes.size() - 1; i >= 0; i--) {
                Note n = notes.get(i);

                JPanel singleNotePanel = new JPanel(new BorderLayout(10, 10));
                singleNotePanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                singleNotePanel.setBackground(Color.WHITE);

                JLabel noteTitleLabel = new JLabel(n.getTitle());
                noteTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));

                JButton editBtn = new JButton("Edit");
                editBtn.addActionListener(e -> {
                    for (int j = 0; j < notes.size(); j++) {
                        if (notes.get(j).isSameNote(n)) {
                            currentNote = notes.get(j);
                            break;
                        }
                    }
                    JPanel editPanel = createEditNotePanel();
                    mainPanel.add(editPanel, "update");
                    cardLayout.show(mainPanel, "update");
                });

                JButton deleteBtn = new JButton("Delete");
                deleteBtn.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this note?",
                            "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        notes.remove(n);
                        for (int j = 0; j < notes.size(); j++) {
                            if (notes.get(j).isSameNote(n)) {
                                notes.remove(j);
                                break;
                            }
                        }
                        saveToFile("");
                        switchToViewPanel();
                    }
                });

                JPanel headerPanel = new JPanel(new BorderLayout());
                headerPanel.setOpaque(false);
                headerPanel.add(noteTitleLabel, BorderLayout.WEST);

                JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
                buttonsPanel.setOpaque(false);
                buttonsPanel.add(editBtn);
                buttonsPanel.add(deleteBtn);

                headerPanel.add(buttonsPanel, BorderLayout.EAST);

                JTextArea noteArea = new JTextArea(n.toString());
                noteArea.setLineWrap(true);
                noteArea.setWrapStyleWord(true);
                noteArea.setEditable(false);
                noteArea.setBackground(new Color(245, 245, 245));
                noteArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                singleNotePanel.add(headerPanel, BorderLayout.NORTH);
                singleNotePanel.add(noteArea, BorderLayout.CENTER);

                notesContainer.add(singleNotePanel);
                notesContainer.add(Box.createVerticalStrut(10));
            }
        }

        JScrollPane scrollPane = new JScrollPane(notesContainer);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));

        JPanel btnPanel = new JPanel();
        btnPanel.add(backBtn);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    /**
     * Creates the edit panel for modifying existing notes.
     */
    private JPanel createEditNotePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        //Set values from currentNote
        titleField.setText(currentNote.getTitle());
        descField.setText(currentNote.getDescription());
        textArea.setText(currentNote.getText());
        authorField.setText(currentNote.getAuthor());

        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descField);
        formPanel.add(new JLabel("Text:"));
        formPanel.add(new JScrollPane(textArea));
        formPanel.add(new JLabel("Author:"));
        formPanel.add(authorField);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date today = calendar.getTime();

        Date currentDeadline = currentNote.getDeadline().getTime();
        if (currentDeadline.before(today))
            currentDeadline = today;

        SpinnerDateModel deadlineModel = new SpinnerDateModel(currentDeadline, today, null, Calendar.DAY_OF_MONTH);
        deadline = new JSpinner(deadlineModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(deadline, "yyyy-MM-dd");
        deadline.setEditor(editor);
        editor.getTextField().setEditable(false);

        formPanel.add(new JLabel("Deadline:"));
        formPanel.add(deadline);

        panel.add(formPanel);

        //Extra fields
        JPanel extraPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        languageField = new JTextField();
        snippetField = new JTextField();
        testcaseField = new JTextField();
        isMandatoryField = new JCheckBox("Is Manditory");
        testStatusCombo = new JComboBox<>();
        languageField.setText("");
        snippetField.setText("");
        testcaseField.setText("");
        isMandatoryField.setSelected(false);
        testStatusCombo.setModel(new DefaultComboBoxModel<>(TestingNote.TestStatus.values()));

        if (currentNote instanceof ProgrammingNote progNote) {
            languageField.setText(progNote.getLanguage());
            snippetField.setText(progNote.getCodingSnippet());

            extraPanel.add(new JLabel("Language:"));
            extraPanel.add(languageField);
            extraPanel.add(new JLabel("Snippet:"));
            extraPanel.add(snippetField);

        } else if (currentNote instanceof TestingNote testNote) {
            testcaseField.setText(testNote.getTestcase());
            isMandatoryField.setSelected(testNote.getIsMandatoryTest());
            testStatusCombo.setSelectedItem(testNote.getTestStatus());

            extraPanel.add(new JLabel("Testcase:"));
            extraPanel.add(testcaseField);
            extraPanel.add(new JLabel("Test Status:"));
            extraPanel.add(testStatusCombo);
            extraPanel.add(isMandatoryField);
        }

        panel.add(extraPanel);

        successMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(10));
        panel.add(successMessage);

        //Update button
        JButton updateBtn = new JButton("Update Note");
        updateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateBtn.addActionListener(e -> {
            try {
                currentNote.saveVersion(currentNote.clone());

                try {
                    currentNote.setTitle(titleField.getText());
                } catch (TitleLengthExceededException ex) {
                    System.out.println(ex.getMessage());
                }

                currentNote.setDescription(descField.getText());
                currentNote.setText(textArea.getText());
                currentNote.setAuthor(authorField.getText());

                Date selectedDate = (Date) deadline.getValue();
                Calendar deadlineCal = Calendar.getInstance();
                deadlineCal.setTime(selectedDate);
                currentNote.setDeadline(deadlineCal);

                if (currentNote instanceof ProgrammingNote progNoteUpdate) {
                    progNoteUpdate.setLanguage(languageField.getText());
                    progNoteUpdate.setCodingSnippet(snippetField.getText());
                } else if (currentNote instanceof TestingNote testNoteUpdate) {
                    testNoteUpdate.setTestcase(testcaseField.getText());
                    testNoteUpdate.setIsMandatoryTest(isMandatoryField.isSelected());
                    testNoteUpdate.updateStatus((TestingNote.TestStatus) testStatusCombo.getSelectedItem());
                }

                saveToFile("Note updated successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating note: " + ex.getMessage());
            }
        });

        panel.add(Box.createVerticalStrut(10));
        panel.add(updateBtn);

        //Back button
        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> {
            readFromFile();
            resetFields();
            mainPanel.remove(mainPanel.getComponent(2));
            mainPanel.add(createViewPanel(), "view");
            cardLayout.show(mainPanel, "view");
        });

        panel.add(backBtn);

        return panel;
    }
    
    /**
     * Creates a new note from form data and adds it to the notes list.
     */
    private void addNote() {
        try {
            String title = titleField.getText();
            String desc = descField.getText();
            String text = textArea.getText();
            String author = authorField.getText();
            Calendar now = Calendar.getInstance();
            Date selectedDate = (Date) deadline.getValue();
            Calendar selectedDeadline = Calendar.getInstance();
            selectedDeadline.setTime(selectedDate);
            String type = (String) noteTypeCombo.getSelectedItem();

            switch (type) {
                case "ProgrammingNote" -> {
                    String snippet = snippetField.getText();
                    String language = languageField.getText();
                    currentNote = new ProgrammingNote(title, desc, text, author, now, selectedDeadline, snippet,
                            language);
                }
                case "TestingNote" -> {
                    String testcase = testcaseField.getText();
                    TestStatus testStatus = (TestingNote.TestStatus) testStatusCombo.getSelectedItem();
                    boolean isMandatory = isMandatoryField.isSelected();
                    currentNote = new TestingNote(title, desc, text, author, now, selectedDeadline, testcase,
                            testStatus,
                            isMandatory);
                }
                default -> currentNote = new Note(title, desc, text, author, now, selectedDeadline);
            }
            notes.add(currentNote);
            resetFields();
            ;
            successMessage.setText("Note added to list");
        } catch (TitleLengthExceededException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    /**
     * Saves the notes list to a serialized file in a separate thread.
     */
    public void saveToFile(String message) {
        Thread saveThread = new Thread(() -> {
            try {
                ObjectOutput out = new ObjectOutputStream(new FileOutputStream("note.ser"));
                out.writeObject(notes);

                SwingUtilities.invokeLater(() -> successMessage.setText(message));
                out.close();
            } catch (IOException e) {
                SwingUtilities.invokeLater(
                        () -> JOptionPane.showMessageDialog(this, "Failed to save to file: " + e.getMessage()));
            }

        });
        saveThread.start();
    }
    
    /**
     * Reads notes from a serialized file in a separate thread and updates the notes list.
     */
    public void readFromFile() {
        Thread loadThread = new Thread(() -> {
            try {
                ObjectInput in = new ObjectInputStream(new FileInputStream("note.ser"));
                Object data = in.readObject();
                List<?> noteList = (List<?>) data;
                List<Note> loadedNotes = new ArrayList<>();
                for (Object note : noteList) {
                    loadedNotes.add((Note) note);
                }
                SwingUtilities.invokeLater(() -> {
                    notes = loadedNotes;
                });   

                in.close();
            } catch (IOException | ClassNotFoundException e) {
                SwingUtilities.invokeLater(() -> cardLayout.show(mainPanel, "view"));
            }
        });
        loadThread.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NoteAppGUI::new);
    }
}
