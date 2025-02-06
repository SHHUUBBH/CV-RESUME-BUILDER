import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.util.ArrayList;
import java.util.HashMap;


public class Personal_info {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JLabel headerLabel;
    private String[] steps = {"Personal Info", "Summary", "Experience", "Education", "Skills"};
    private JProgressBar progressBar;
    private ArrayList<Component> allFields = new ArrayList<>();
    private int totalFields = 0;
    private int currentSectionIndex = 0;

    public Personal_info() {
        // Frame setup
        frame = new JFrame("Modern CV Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 750);
        frame.setLocationRelativeTo(null);

        ModernBackgroundPanel backgroundPanel = new ModernBackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        frame.setContentPane(backgroundPanel);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        headerLabel = new JLabel("Professional CV Builder", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(50, 50, 50));
        headerLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 255, 255), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Side Menu
        JPanel sidePanelContainer = new JPanel(new BorderLayout());
        sidePanelContainer.setBackground(new Color(20, 20, 20));
        sidePanelContainer.setPreferredSize(new Dimension(250, 0));
        sidePanelContainer.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        menuPanel = new JPanel(new GridLayout(steps.length, 1, 10, 10));
        menuPanel.setBackground(new Color(30, 30, 30));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < steps.length; i++) {
            String step = steps[i];
            JButton button = new JButton(step);
            styleMenuButton(button);
            final int index = i;
            button.addActionListener(e -> {
                cardLayout.show(mainPanel, step);
                updateMenuHighlight(index);
                updateHeader(index);
                currentSectionIndex = index;
            });
            menuPanel.add(button);
        }
        sidePanelContainer.add(menuPanel, BorderLayout.CENTER);

        // Main Panel (Card Layout)
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        mainPanel.setOpaque(false);

        mainPanel.add(createPersonalInfoPanel(), steps[0]);
        mainPanel.add(createSummaryPanel(), steps[1]);
        mainPanel.add(createExperiencePanel(), steps[2]);
        mainPanel.add(createEducationPanel(), steps[3]);
        mainPanel.add(createSkillsPanel(), steps[4]);

        // Progress Bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(34, 139, 34)); // Green color for progress
        progressBar.setBackground(new Color(20, 20, 20));
        progressBar.setFont(new Font("SansSerif", Font.BOLD, 16));
        progressBar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        progressBar.setUI(new javax.swing.plaf.metal.MetalProgressBarUI() {
            protected Color getSelectionBackground() {
                return Color.BLACK;
            }

            protected Color getSelectionForeground() {
                return Color.GRAY; // Percentage text in grey
            }
        });

        // Add panels to the frame
        backgroundPanel.add(headerPanel, BorderLayout.NORTH);
        backgroundPanel.add(sidePanelContainer, BorderLayout.WEST);
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);
        backgroundPanel.add(progressBar, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public Personal_info(String section) {
        this();
        cardLayout.show(mainPanel, section);
    }

    private JPanel createPersonalInfoPanel() {
        return createFormPanel(
                new String[]{"First Name", "Last Name", "Phone", "E-mail", "Date of Birth (dd/MM/yyyy)", "LinkedIn"},
                new String[]{"text", "text", "phone", "email", "date", "text"},
                "Enter your personal information below."
        );
    }

    private JPanel createSummaryPanel() {
        return createFormPanel(
                new String[]{"Summary (Brief Description)"},
                new String[]{"textarea"},
                "Write a brief summary about yourself."
        );
    }

    private JPanel createExperiencePanel() {
        return createFormPanel(
                new String[]{"Company Name", "Job Title", "Description of Responsibilities"},
                new String[]{"text", "text", "textarea"},
                "Add your work experience."
        );
    }

    private JPanel createEducationPanel() {
        return createFormPanel(
                new String[]{"10th School Name", "10th Year", "10th Percentage",
                        "12th School Name", "12th Year", "12th Percentage",
                        "Graduation College", "Graduation Year", "Graduation CGPA"},
                new String[]{"text", "dropdown", "percentage",
                        "text", "dropdown", "percentage",
                        "text", "dropdown", "percentage"},
                "Enter your education details."
        );
    }

    private JPanel createSkillsPanel() {
        // Create the base panel with fields
        JPanel panel = createFormPanel(
                new String[]{"Skill 1", "Skill 2", "Skill 3", "Skill 4", "Skill 5"},
                new String[]{"text", "text", "text", "text", "text"},
                "Add your skills below."
        );

        // Extract formPanel from the wrapperPanel created by createFormPanel
        JPanel formPanel = (JPanel) ((JScrollPane) ((BorderLayout) panel.getLayout()).getLayoutComponent(BorderLayout.CENTER)).getViewport().getView();

        // Adding the "Generate CV" button below the last field
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; // Align with other labels
        gbc.gridy = formPanel.getComponentCount(); // Position below the last component
        gbc.gridwidth = 2; // Span across both columns

        JButton generateCVButton = new JButton("Generate CV");
        styleButton(generateCVButton);
        generateCVButton.addActionListener(e -> {
            HashMap<String, String> userData = new HashMap<>();
            for (Component field : allFields) {
                if (field instanceof JTextField) {
                    JTextField textField = (JTextField) field;
                    userData.put(textField.getName(), textField.getText().trim());
                } else if (field instanceof JTextArea) {
                    JTextArea textArea = (JTextArea) field;
                    userData.put(textArea.getName(), textArea.getText().trim());
                } else if (field instanceof JComboBox) {
                    JComboBox<?> comboBox = (JComboBox<?>) field;
                    userData.put(comboBox.getName(), comboBox.getSelectedItem().toString());
                }
            }
            GenerateCV.generateCV(userData);
        });


        formPanel.add(generateCVButton, gbc);

        // Revalidate and repaint to ensure UI updates
        formPanel.revalidate();
        formPanel.repaint();

        return panel;
    }


    private JPanel createFormPanel(String[] labels, String[] types, String description) {
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setOpaque(false);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        JLabel descriptionLabel = new JLabel(description, SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        descriptionLabel.setForeground(Color.CYAN);
        wrapperPanel.add(descriptionLabel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        wrapperPanel.add(scrollPane, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("SansSerif", Font.BOLD, 18));
            label.setForeground(Color.CYAN);
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(label, gbc);

            gbc.gridx = 1;
            if (types[i].equals("dropdown")) {
                JComboBox<Integer> yearDropdown = new JComboBox<>();
                for (int year = 1980; year <= 2030; year++) {
                    yearDropdown.addItem(year);
                }
                formPanel.add(yearDropdown, gbc);
                allFields.add(yearDropdown);
            } else if (types[i].equals("percentage")) {
                JFormattedTextField percentageField = new JFormattedTextField(new DecimalFormat("#0.0"));
                percentageField.setColumns(10);
                percentageField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        try {
                            double value = Double.parseDouble(percentageField.getText());
                            if (value < 0 || value > 100) {
                                throw new NumberFormatException();
                            }
                        } catch (NumberFormatException ex) {
                            percentageField.setText("");
                        }
                    }
                });
                formPanel.add(percentageField, gbc);
                allFields.add(percentageField);
            } else if (types[i].equals("textarea")) {
                JTextArea textArea = new JTextArea(4, 30);
                textArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
                formPanel.add(new JScrollPane(textArea), gbc);
                allFields.add(textArea);
            } else if (types[i].equals("phone")) {
                JTextField phoneField = new JTextField(15);
                phoneField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if (!Character.isDigit(c) || phoneField.getText().length() >= 10) {
                            e.consume();
                        }
                    }
                });
                formPanel.add(phoneField, gbc);
                allFields.add(phoneField);
            } else if (types[i].equals("email")) {
                JTextField emailField = new JTextField(30);
                emailField.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        String email = emailField.getText();
                        if (!email.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
                            emailField.setText("");
                        }
                    }
                });
                formPanel.add(emailField, gbc);
                allFields.add(emailField);
            } else if (types[i].equals("date")) {
                MaskFormatter dateFormatter = null;
                try {
                    dateFormatter = new MaskFormatter("##/##/####");
                    dateFormatter.setPlaceholderCharacter('_');
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                JFormattedTextField dateField = new JFormattedTextField(dateFormatter);
                dateField.setFont(new Font("SansSerif", Font.PLAIN, 16));
                formPanel.add(dateField, gbc);
                allFields.add(dateField);
            } else {
                JTextField textField = new JTextField(30);
                textField.setFont(new Font("SansSerif", Font.PLAIN, 16));
                formPanel.add(textField, gbc);
                allFields.add(textField);
            }
        }

        totalFields += labels.length;

        JButton nextButton = new JButton("Next");
        styleButton(nextButton);
        nextButton.addActionListener(e -> {
            cardLayout.next(mainPanel);
            currentSectionIndex = Math.min(currentSectionIndex + 1, steps.length - 1);
            updateHeader(currentSectionIndex); // Update header dynamically
            updateMenuHighlight(currentSectionIndex);
            updateProgress();
        });
        gbc.gridx = 1;
        gbc.gridy = labels.length;
        formPanel.add(nextButton, gbc);

        return wrapperPanel;
    }

    private void styleMenuButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setForeground(Color.LIGHT_GRAY);
        button.setBackground(new Color(50, 50, 50));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private class ModernBackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            GradientPaint gradient = new GradientPaint(0, 0, new Color(30, 30, 30), width, height, new Color(10, 10, 10));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, width, height);

            g2d.setColor(new Color(255, 255, 255, 20));
            for (int i = 0; i < width; i += 40) {
                g2d.drawLine(i, 0, i, height);
            }
            for (int j = 0; j < height; j += 40) {
                g2d.drawLine(0, j, width, j);
            }
        }
    }

    private void updateProgress() {
        int filledFields = 0;

        for (Component field : allFields) {
            if (field instanceof JTextField) {
                if (!((JTextField) field).getText().trim().isEmpty()) {
                    filledFields++;
                }
            } else if (field instanceof JTextArea) {
                if (!((JTextArea) field).getText().trim().isEmpty()) {
                    filledFields++;
                }
            } else if (field instanceof JComboBox) {
                if (((JComboBox<?>) field).getSelectedItem() != null) {
                    filledFields++;
                }
            }
        }

        int progressPercentage = (int) (((double) filledFields / totalFields) * 100);
        progressBar.setValue(progressPercentage);
    }

    private void updateMenuHighlight(int activeIndex) {
        for (int i = 0; i < menuPanel.getComponentCount(); i++) {
            JButton button = (JButton) menuPanel.getComponent(i);
            if (i == activeIndex) {
                button.setBackground(new Color(70, 130, 180));
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(new Color(50, 50, 50));
                button.setForeground(Color.LIGHT_GRAY);
            }
        }
    }

    private void updateHeader(int activeIndex) {
        headerLabel.setText("Professional CV Builder - " + steps[activeIndex]);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Personal_info::new);
    }
}