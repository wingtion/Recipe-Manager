import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginScreen {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public LoginScreen() {
        RecipeDAO recipeDAO = new RecipeDAO();

        // Create the login frame
        frame = new JFrame("Login");
        frame.setSize(300, 345);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.setIconImage(new ImageIcon(getClass().getResource("icons8-login-48.png")).getImage());

        // Image label
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("295128.png")); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(180, 160,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back
        JLabel imageLabel = new JLabel(imageIcon);
        frame.add(imageLabel, BorderLayout.NORTH);


        // Panel for login components
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 20, 80, 25);
        loginPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 160, 25);
        loginPanel.add(usernameField);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 50, 80, 25);
        loginPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 50, 160, 25);
        loginPanel.add(passwordField);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 80, 80, 25);
        loginPanel.add(loginButton);

        // Message label
        messageLabel = new JLabel("");
        messageLabel.setBounds(20, 110, 250, 25);
        loginPanel.add(messageLabel);

        frame.add(loginPanel, BorderLayout.CENTER);

        // Action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = usernameField.getText();
                String enteredPassword = new String(passwordField.getPassword());

                boolean loginSuccessful = recipeDAO.login(enteredUsername, enteredPassword);

                if (loginSuccessful) {
                    RecipeScreen recipeScreen = new RecipeScreen(frame, true);
                    frame.setVisible(false);

                    recipeScreen.setVisible(true);
                    frame.dispose();

                } else {
                    messageLabel.setForeground(Color.red);
                    messageLabel.setText("Invalid username or password.");
                }
            }
        });

        JButton signInButton = new JButton("Sign In");
        signInButton.setBounds(185, 80, 80, 25);
        loginPanel.add(signInButton);
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = usernameField.getText();
                String enteredPassword = new String(passwordField.getPassword());

                try {
                    String sql = "SELECT * FROM users WHERE username=?";
                    PreparedStatement preparedStatement = recipeDAO.getConnection().prepareStatement(sql);
                    preparedStatement.setString(1, enteredUsername);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        messageLabel.setForeground(Color.red);
                        messageLabel.setText("The username is already taken.");
                    } else {
                        recipeDAO.signIn(enteredUsername, enteredPassword);
                        messageLabel.setForeground(new Color(50, 164, 85, 255));
                        messageLabel.setText("Signed In Successfully");
                    }
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                }
            }
        });

        // Display the frame
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            // Use either light or dark theme
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new LoginScreen());
    }
}