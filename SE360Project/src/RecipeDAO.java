import java.sql.*;
import java.util.ArrayList;

public class RecipeDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;

    public RecipeDAO(){
        connection = DatabaseConnection.getConnection();
    }

    public boolean login(String username , String pw){
        try {
            String sql = "Select * from users WHERE username = ? and password = ?";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,username);
            preparedStatement.setString(2,pw);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void signIn(String username, String password) {
        try {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace(); // Print the exception details to identify any issues
            throw new RuntimeException("Error signing in.");
        }
    }
    public void addRecipes(String nameOfRecipe, String ingredientOfRecipe , String instructionOfRecipe , String categoryOfRecipe){
        try {
            String sql = "INSERT INTO recipes(name,ingredients,instructions,category) VALUES(?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,nameOfRecipe);
            preparedStatement.setString(2,ingredientOfRecipe);
            preparedStatement.setString(3,instructionOfRecipe);
            preparedStatement.setString(4,categoryOfRecipe);

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateRecipe(int id, String name, String ingredient, String instruction, String category) {
        String sql = "UPDATE recipes SET name=? , ingredients=? , instructions=? , category=? WHERE id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, ingredient);
            preparedStatement.setString(3, instruction);
            preparedStatement.setString(4, category);
            preparedStatement.setInt(5,id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteRecipes(int id){
        try {

            String sql = "DELETE FROM recipes WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Recipe> bringRecipes() {
        ArrayList<Recipe> output = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM recipes";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String ingredients= resultSet.getString("ingredients");
                String instructions = resultSet.getString("instructions");
                String category = resultSet.getString("category");

                Recipe recipe = new Recipe(id,name,ingredients,instructions,category);
                output.add(recipe);

            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDetailedInstructions(int recipeId){
        String instructions="";
        String sql = "SELECT instructions FROM recipes WHERE id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,recipeId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                instructions = resultSet.getString("instructions");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instructions;

    }
    public String getDetailedIngredients(int recipeId){
        String ingredients ="";
        String sql = "SELECT ingredients FROM recipes WHERE id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,recipeId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ingredients = resultSet.getString("ingredients");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredients;

    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}