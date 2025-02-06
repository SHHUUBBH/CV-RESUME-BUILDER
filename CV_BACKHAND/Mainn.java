import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Mainn {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private DatabaseConnection dbConnection = new DatabaseConnection(); // Database connection instance

    public Mainn() {
        // Initialize the frame
        frame = new JFrame("CV Resume Builder - Login/Sign-Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(new BorderLayout());
        frame.addComponentListener(new ResizeListener());

        // Main panel using CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Custom background panel with half black and half white
        SplitBackgroundPanel backgroundPanel = new SplitBackgroundPanel();
        backgroundPanel.setLayout(new GridBagLayout());  // Center content
        backgroundPanel.add(mainPanel);

        // Add login and sign-up panels
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createSignUpPanel(), "SignUp");

        // Add the main panel to the frame
        frame.add(backgroundPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);

        // Add a shutdown hook to close the database connection when the application exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            dbConnection.closeConnection();
        }));
    }


    private JPanel createLoginPanel() {
        JPanel container = createContainerPanel();
        container.setLayout(null);

        JLabel welcomeLabel = new JLabel("Log In", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBounds(100, 20, 300, 30);
        welcomeLabel.setForeground(Color.WHITE);
        container.add(welcomeLabel);

        JTextField emailField = new JTextField();
        styleTextField(emailField);
        emailField.setBounds(100, 80, 300, 30);
        container.add(emailField);

        JPasswordField passwordField = new JPasswordField();
        stylePasswordField(passwordField);
        passwordField.setBounds(100, 130, 300, 30);
        container.add(passwordField);

        JLabel forgotPassword = new JLabel("Forgot your password?", SwingConstants.RIGHT);
        forgotPassword.setBounds(200, 170, 200, 20);
        forgotPassword.setForeground(new Color(180, 180, 180));
        container.add(forgotPassword);

        JButton loginButton = new JButton("LOG IN");
        styleButton(loginButton);
        loginButton.setBounds(150, 210, 200, 40);

        // Add login action listener
        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both email and password.", "Login Failed", JOptionPane.WARNING_MESSAGE);
            } else if (dbConnection.loginUser(email, password)) {
                JOptionPane.showMessageDialog(frame, "Login successful!");
                frame.dispose(); // Close the login frame
                new Menu(); // Open the Menu window
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });


        container.add(loginButton);

        JLabel signUpLink = new JLabel("Sign Up", SwingConstants.CENTER);
        signUpLink.setBounds(200, 270, 100, 30);
        signUpLink.setForeground(Color.LIGHT_GRAY);
        signUpLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signUpLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "SignUp");
            }
        });
        container.add(signUpLink);

        return wrapInBoxWithBlurredShadow(container);
    }


    private JPanel createSignUpPanel() {
        JPanel container = createContainerPanel();
        container.setLayout(null);

        JLabel welcomeLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBounds(100, 20, 300, 30);
        welcomeLabel.setForeground(Color.WHITE);
        container.add(welcomeLabel);

        JTextField emailField = new JTextField();
        styleTextField(emailField);
        emailField.setBounds(100, 80, 300, 30);
        container.add(emailField);

        JPasswordField passwordField = new JPasswordField();
        stylePasswordField(passwordField);
        passwordField.setBounds(100, 130, 300, 30);
        container.add(passwordField);

        JButton registerButton = new JButton("REGISTER");
        styleButton(registerButton);
        registerButton.setBounds(150, 210, 200, 40);

        // Add registration action listener
        registerButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Registration Failed", JOptionPane.WARNING_MESSAGE);
            } else if (dbConnection.registerUser(email, password)) {
                JOptionPane.showMessageDialog(frame, "Registration successful!");
                cardLayout.show(mainPanel, "Login");
            } else {
                JOptionPane.showMessageDialog(frame, "Registration failed. Email might already be in use.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        container.add(registerButton);

        JLabel loginLink = new JLabel("Log In", SwingConstants.CENTER);
        loginLink.setBounds(200, 270, 100, 30);
        loginLink.setForeground(Color.LIGHT_GRAY);
        loginLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "Login");
            }
        });
        container.add(loginLink);

        return wrapInBoxWithBlurredShadow(container);
    }

    private JPanel createContainerPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(45, 45, 45));
        return panel;
    }

    private JPanel wrapInBoxWithBlurredShadow(JPanel content) {
        JPanel shadowPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.drawImage(createShadowImage(content.getPreferredSize(), 20), 0, 0, null);
                g2d.dispose();
            }
        };

        shadowPanel.setLayout(new BorderLayout());
        shadowPanel.setOpaque(false);
        shadowPanel.add(content, BorderLayout.CENTER);
        content.setBorder(new EmptyBorder(10, 10, 10, 10));
        content.setPreferredSize(new Dimension(500, 350));

        return shadowPanel;
    }

    private BufferedImage createShadowImage(Dimension size, int blurRadius) {
        int width = size.width + blurRadius * 2;
        int height = size.height + blurRadius * 2;
        BufferedImage shadowImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = shadowImage.createGraphics();

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(blurRadius, blurRadius, size.width, size.height, 20, 20);
        g2.dispose();

        return applyGaussianBlur(shadowImage, blurRadius);
    }

    private BufferedImage applyGaussianBlur(BufferedImage image, int radius) {
        int size = radius * 2 + 1;
        float[] data = new float[size * size];
        float sigma = radius / 3.0f;
        float normalization = 1 / (2 * (float) Math.PI * sigma * sigma);
        float sum = 0;

        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                float value = normalization * (float) Math.exp(-(x * x + y * y) / (2 * sigma * sigma));
                data[(y + radius) * size + (x + radius)] = value;
                sum += value;
            }
        }

        for (int i = 0; i < data.length; i++) {
            data[i] /= sum;
        }

        Kernel kernel = new Kernel(size, size, data);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return op.filter(image, null);
    }

    private void styleTextField(JTextField field) {
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Color.WHITE);
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        field.setCaretColor(Color.WHITE);
    }

    private void stylePasswordField(JPasswordField field) {
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Color.WHITE);
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        field.setCaretColor(Color.WHITE);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 70, 70));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private class SplitBackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, width / 2, height);

            g2d.setColor(Color.WHITE);
            g2d.fillRect(width / 2, 0, width / 2, height);
        }
    }

    private class ResizeListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            mainPanel.revalidate();
            mainPanel.repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Mainn::new);
    }
}