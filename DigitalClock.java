import javax.swing.*; // For GUI components
import java.awt.*; // For layout management
import java.io.File; // For file handling
import java.io.IOException; // For file handling
import java.time.LocalDateTime; // For current date and time
import java.time.format.DateTimeFormatter; // To format date and time

public class DigitalClock extends JFrame { // Create a window for the clock
    private JLabel timeLabel; // Label to display time
    private JLabel dateLabel; // Label to display date
    private JPanel timerPanel; // Panel to hold timer components
    private JButton countdownButton; // Button to select countdown timer
    private JButton stopwatchButton; // Button to select stopwatch
    private JLabel stopwatchLabel; // Label for stopwatch display
    private JLabel countdownLabel; // Label for countdown display
    private JTextField countdownInput; // Input field for countdown
    private Timer stopwatchTimer; // Timer for stopwatch
    private Timer countdownTimer; // Timer for countdown
    private int stopwatchSeconds = 0; // Elapsed seconds for stopwatch
    private int countdownSeconds = 0; // Countdown seconds
    private boolean stopwatchRunning = false; // Stopwatch running state
    private boolean countdownRunning = false; // Countdown running state

    public DigitalClock() {
        // Window setup
        setTitle("Digital Clock"); // Window title
        setSize(400, 400); // Window height and width
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Close app when the window is closed
        setLayout(new GridBagLayout()); // Use GridBagLayout for the window layout

        // Create a GridBagConstraints object for positioning
        GridBagConstraints gbc = new GridBagConstraints(); // Create a new GridBagConstraints object
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the horizontal space

        // Style the labels
        timeLabel = new JLabel(); // Create a label for time
        dateLabel = new JLabel(); // Create a label for date
        styleLabels(); // Apply styles to the labels

        // Add date label to the top of the window
        gbc.gridx = 0; // Set the x position
        gbc.gridy = 0; // Set the y position
        gbc.weighty = 0.1; // Set the weight
        add(dateLabel, gbc); // Place date at the top

        // Add time label to the center of the window
        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        gbc.weighty = 0.3; // Allocate more vertical space for the time label
        gbc.anchor = GridBagConstraints.CENTER; // Center the time label
        add(timeLabel, gbc); // Place time at the center

        // Create buttons for selecting countdown and stopwatch
        countdownButton = new JButton("Countdown Timer");
        countdownButton.addActionListener(e -> toggleCountdownPanel()); // Toggle countdown panel
        gbc.gridy = 2; // Row 2
        add(countdownButton, gbc); // Add countdown button

        stopwatchButton = new JButton("Stopwatch");
        stopwatchButton.addActionListener(e -> toggleStopwatchPanel()); // Toggle stopwatch panel
        gbc.gridy = 3; // Row 3
        add(stopwatchButton, gbc); // Add stopwatch button

        // Timer panel to hold timer components
        timerPanel = new JPanel();
        timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.Y_AXIS)); // Vertical layout

        // Create labels for stopwatch and countdown
        stopwatchLabel = new JLabel("Stopwatch: 00:00");
        countdownLabel = new JLabel("Countdown: 00:00");
        countdownInput = new JTextField(5); // Input field for countdown time (in seconds)

        // Create buttons for countdown control
        JButton startCountdownButton = new JButton("Start Countdown");
        startCountdownButton.addActionListener(e -> startCountdown()); // Start countdown action
        JButton stopCountdownButton = new JButton("Stop Countdown");
        stopCountdownButton.addActionListener(e -> stopCountdown()); // Stop countdown action
        JButton resetCountdownButton = new JButton("Reset Countdown");
        resetCountdownButton.addActionListener(e -> resetCountdown()); // Reset countdown action

        // Create buttons for stopwatch control
        JButton startStopwatchButton = new JButton("Start Stopwatch");
        startStopwatchButton.addActionListener(e -> startStopwatch()); // Start stopwatch action
        JButton stopStopwatchButton = new JButton("Stop Stopwatch");
        stopStopwatchButton.addActionListener(e -> stopStopwatch()); // Stop stopwatch action
        JButton resetStopwatchButton = new JButton("Reset Stopwatch");
        resetStopwatchButton.addActionListener(e -> resetStopwatch()); // Reset stopwatch action

        // Add components for countdown to timer panel
        timerPanel.add(countdownLabel);
        timerPanel.add(countdownInput);
        timerPanel.add(startCountdownButton);
        timerPanel.add(stopCountdownButton);
        timerPanel.add(resetCountdownButton);

        // Add components for stopwatch to timer panel
        timerPanel.add(stopwatchLabel);
        timerPanel.add(startStopwatchButton);
        timerPanel.add(stopStopwatchButton);
        timerPanel.add(resetStopwatchButton);

        // Initially hide all timer panel components
        countdownLabel.setVisible(false);
        countdownInput.setVisible(false);
        startCountdownButton.setVisible(false);
        stopCountdownButton.setVisible(false);
        resetCountdownButton.setVisible(false);
        stopwatchLabel.setVisible(false);
        startStopwatchButton.setVisible(false);
        stopStopwatchButton.setVisible(false);
        resetStopwatchButton.setVisible(false);
        
        timerPanel.setVisible(false); // Hide the entire timer panel initially
        gbc.gridy = 4; // Row 4
        add(timerPanel, gbc); // Add timer panel to the window

        // Create a timer to update the clock
        Timer timer = new Timer(1000, e -> updateClock()); // Create a timer to update the clock every second
        timer.start(); // Start the timer

        updateClock(); // Initial update of the clock
        setVisible(true); // Make the window visible

        getContentPane().setBackground(Color.BLACK); // Set window background color to black
        styleButton(countdownButton);
        styleButton(stopwatchButton);
        timerPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10)); // Add padding
        timerPanel.setBackground(Color.BLACK); // Match the window background
        stopwatchLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        countdownLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around components


        



    }

    private void styleLabels() {
        // Customize the date label
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the date label
        dateLabel.setFont(loadCustomFont("resources/neon_pixel-7.ttf", 24)); // Date Font
        dateLabel.setForeground(new Color(255, 99, 99)); // Date font color
        dateLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding to the date label

        // Customize the time label
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the time label
        timeLabel.setFont(loadCustomFont("resources/digital-7.ttf", 60)); // Large font for time
        timeLabel.setForeground(Color.WHITE); // Time font color
        timeLabel.setOpaque(true); // Make background visible
        timeLabel.setBackground(Color.BLACK); // Background color for time label
        timeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Add padding to the time label
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(60, 63, 65)); // Dark background for buttons
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false); // No focus border
        button.setFont(new Font("SansSerif", Font.BOLD, 14)); // Set button font
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Subtle border
    
        // Optional: Change button appearance when hovered
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(77, 79, 83)); // Lighter background on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 63, 65)); // Revert to default color
            }
        });
    }
    

    private Font loadCustomFont(String path, int size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(path)); // Load font from file
            return font.deriveFont(Font.PLAIN, size); // Return the font with the specified size
        } catch (FontFormatException | IOException e) {
            e.printStackTrace(); // Print any loading errors
            return new Font("Arial", Font.PLAIN, size); // Fallback to Arial
        }
    }

    private void updateClock() {
        LocalDateTime now = LocalDateTime.now(); // Get current time
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss"); // Format for time
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE d, MMMM yyyy"); // Format for date
        timeLabel.setText(now.format(timeFormatter)); // Set formatted time
        dateLabel.setText(now.format(dateFormatter)); // Set formatted date
    }

    private void startStopwatch() {
        stopwatchRunning = true; // Set the stopwatch to running
        stopwatchTimer = new Timer(1000, e -> updateStopwatch()); // Create a new timer for the stopwatch
        stopwatchTimer.start(); // Start the stopwatch timer
    }

    private void stopStopwatch() {
        if (stopwatchRunning) {
            stopwatchRunning = false; // Set the stopwatch to stopped
            stopwatchTimer.stop(); // Stop the stopwatch timer
        }
    }

    private void resetStopwatch() {
        stopwatchRunning = false; // Set the stopwatch to stopped
        stopwatchSeconds = 0; // Reset stopwatch seconds
        stopwatchLabel.setText("Stopwatch: 00:00"); // Update the display
        if (stopwatchTimer != null) {
            stopwatchTimer.stop(); // Stop the timer if running
        }
    }

    private void updateStopwatch() {
        stopwatchSeconds++; // Increment stopwatch seconds
        int minutes = stopwatchSeconds / 60; // Calculate minutes
        int seconds = stopwatchSeconds % 60; // Calculate remaining seconds
        stopwatchLabel.setText(String.format("Stopwatch: %02d:%02d", minutes, seconds)); // Update the display
    }

    private void startCountdown() {
        try {
            countdownSeconds = Integer.parseInt(countdownInput.getText()); // Get the countdown time
            if (countdownSeconds > 0) {
                countdownRunning = true; // Set the countdown to running
                countdownTimer = new Timer(1000, e -> updateCountdown()); // Create a new timer for countdown
                countdownTimer.start(); // Start the countdown timer
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a positive number."); // Alert if not positive
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a number."); // Alert if input is invalid
        }
    }

    private void stopCountdown() {
        if (countdownRunning) {
            countdownRunning = false; // Set the countdown to stopped
            countdownTimer.stop(); // Stop the countdown timer
        }
    }

    private void resetCountdown() {
        countdownRunning = false; // Set the countdown to stopped
        countdownSeconds = 0; // Reset countdown seconds
        countdownLabel.setText("Countdown: 00:00"); // Update the display
        if (countdownTimer != null) {
            countdownTimer.stop(); // Stop the timer if running
        }
    }

    private void updateCountdown() {
        if (countdownSeconds > 0) {
            countdownSeconds--; // Decrement countdown seconds
            int minutes = countdownSeconds / 60; // Calculate minutes
            int seconds = countdownSeconds % 60; // Calculate remaining seconds
            countdownLabel.setText(String.format("Countdown: %02d:%02d", minutes, seconds)); // Update the display
        } else {
            stopCountdown(); // Stop countdown when it reaches zero
            JOptionPane.showMessageDialog(this, "Countdown finished!"); // Notify user
        }
    }

    private void toggleCountdownPanel() {
        // If countdown is currently visible, reset and hide it
        if (timerPanel.isVisible() && countdownLabel.isVisible()) {
            resetCountdown(); // Reset countdown
            timerPanel.setVisible(false); // Hide the timer panel
        } else {
            // Hide stopwatch components if countdown is being shown
            stopwatchLabel.setVisible(false); // Hide stopwatch label
            countdownLabel.setVisible(true); // Show countdown label
            countdownInput.setVisible(true); // Show countdown input
            timerPanel.setVisible(true); // Show timer panel
            resetStopwatch(); // Reset stopwatch if it's running
            // Show only countdown buttons
            for (Component comp : timerPanel.getComponents()) {
                comp.setVisible(false); // Hide all components initially
            }
            countdownLabel.setVisible(true); // Show countdown label
            countdownInput.setVisible(true); // Show countdown input
            timerPanel.getComponent(2).setVisible(true); // Start countdown button
            timerPanel.getComponent(3).setVisible(true); // Stop countdown button
            timerPanel.getComponent(4).setVisible(true); // Reset countdown button
        }
    }

    private void toggleStopwatchPanel() {
        // If stopwatch is currently visible, reset and hide it
        if (timerPanel.isVisible() && stopwatchLabel.isVisible()) {
            resetStopwatch(); // Reset stopwatch
            timerPanel.setVisible(false); // Hide the timer panel
        } else {
            // Hide countdown components if stopwatch is being shown
            countdownLabel.setVisible(false); // Hide countdown label
            countdownInput.setVisible(false); // Hide countdown input
            stopwatchLabel.setVisible(true); // Show stopwatch label
            timerPanel.setVisible(true); // Show timer panel
            resetCountdown(); // Reset countdown if it's running
            // Show only stopwatch buttons
            for (Component comp : timerPanel.getComponents()) {
                comp.setVisible(false); // Hide all components initially
            }
            stopwatchLabel.setVisible(true); // Show stopwatch label
            timerPanel.getComponent(6).setVisible(true); // Start stopwatch button
            timerPanel.getComponent(7).setVisible(true); // Stop stopwatch button
            timerPanel.getComponent(8).setVisible(true); // Reset stopwatch button

            
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DigitalClock::new); // Create a new instance of the clock
    }
}//back




