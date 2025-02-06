import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu {
    private JFrame frame;
    private JPanel menuPanel;


    public Menu() {
        // Initialize the frame
        frame = new JFrame("CV Resume Builder - Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);

        // Gradient Background
        MonochromeBackgroundPanel backgroundPanel = new MonochromeBackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        frame.add(backgroundPanel);

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Resume Builder");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Begin building your professional CV now.");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(200, 200, 200));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(Box.createRigidArea(new Dimension(0, 50))); // Spacer
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        titlePanel.add(subtitleLabel);

        // Menu Panel for Buttons
        menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new GridLayout(5, 1, 20, 20));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200)); // Padding around buttons

        // Add Menu Buttons
        String[] menuOptions = {"Personal Info", "Experience", "Education", "Skills", "Summary"};
        for (String option : menuOptions) {
            JButton menuButton = createMenuButton(option);
            menuPanel.add(menuButton);
        }

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setOpaque(false);

        JLabel footerLabel = new JLabel("© 2024 Resume Builder | Monochrome Edition");
        footerLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(180, 180, 180));
        footerPanel.add(footerLabel);

        // Add Components to Background Panel
        backgroundPanel.add(titlePanel, BorderLayout.NORTH);
        backgroundPanel.add(menuPanel, BorderLayout.CENTER);
        backgroundPanel.add(footerPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Create a styled menu button
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(40, 40, 40)); // Dark gray
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20)); // Padding

        // Rounded corners
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Hover Effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(80, 80, 80)); // Lighter gray
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(40, 40, 40)); // Dark gray
                button.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70), 2));
            }
        });

        // Open the relevant section and close the menu
        button.addActionListener(e -> {
            frame.dispose(); // Close the menu window
            new Personal_info(); // Open the relevant section
        });

        return button;
    }

    // Custom Monochrome Background Panel
    private class MonochromeBackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            // Black gradient background
            GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, 0, height, new Color(20, 20, 20));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, width, height);

            // Subtle white grid
            g2d.setColor(new Color(255, 255, 255, 10));
            for (int i = 0; i < width; i += 40) {
                g2d.drawLine(i, 0, i, height); // Vertical lines
            }
            for (int j = 0; j < height; j += 40) {
                g2d.drawLine(0, j, width, j); // Horizontal lines
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Menu::new);
    }
}