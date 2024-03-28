import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RecipeDetailsDialog extends JDialog {
    public RecipeDetailsDialog(Frame parent, String title, boolean modal, String recipeName, String ingredients, String instructions, String category) {
        super(parent, title, modal);

        setIconImage(new ImageIcon(getClass().getResource("recipe.png")).getImage());

        initComponents(recipeName, ingredients, instructions, category);
    }

    private void initComponents(String recipeName, String ingredients, String instructions, String category) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(recipeName);
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        Font recipeNameFont = new Font("Inter", Font.BOLD, 22);
        nameLabel.setFont(recipeNameFont);
        nameLabel.setBorder(new EmptyBorder(0, 180, 0, 0));

        // Ingredients Label
        JLabel ingredientsLabel = new JLabel("Ingredients:");
        Font labelFont = new Font("Inter", Font.BOLD, 16);
        ingredientsLabel.setFont(labelFont);
        ingredientsLabel.setForeground(new Color(50, 100, 150)); // Adjust color as needed

        JTextArea ingredientsArea = new JTextArea(ingredients);
        ingredientsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);
        Font textFont = new Font("Inter ", Font.PLAIN, 14);
        ingredientsArea.setFont(textFont);
        ingredientsArea.setBackground(new Color(255, 255, 200));

        // Instructions Label
        JLabel instructionsLabel = new JLabel("Instructions:");
        instructionsLabel.setFont(labelFont);
        instructionsLabel.setForeground(new Color(50, 100, 150)); // Adjust color as needed

        JTextArea instructionsArea = new JTextArea(instructions);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        instructionsArea.setFont(textFont);
        instructionsArea.setBackground(new Color(200, 255, 200));

        JLabel categoryLabel = new JLabel("Category: " + category);

        // Set font styles
        nameLabel.setFont(recipeNameFont);
        categoryLabel.setFont(textFont);

        // Set colors
        panel.setBackground(new Color(240, 240, 240));
        nameLabel.setForeground(Color.black);

        // Add scrolling capability to JTextAreas
        JScrollPane ingredientsScrollPane = new JScrollPane(ingredientsArea);
        JScrollPane instructionsScrollPane = new JScrollPane(instructionsArea);

        // Add components to the panel
        panel.add(nameLabel);
        panel.add(ingredientsLabel);
        panel.add(ingredientsScrollPane);
        panel.add(instructionsLabel);
        panel.add(instructionsScrollPane);
        panel.add(categoryLabel);

        // Add the panel to the dialog
        getContentPane().add(panel);

        // Set dialog properties
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        setLocation(getX(), 0);
        pack();
        setResizable(true);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RecipeDetailsDialog dialog = new RecipeDetailsDialog(null, "Recipe Details", true,
                    "Sample Recipe", "Ingredients here", "Instructions here", "Sample Category");


        });
    }

}