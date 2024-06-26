package controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class AddPantyController {

    
    @FXML 
    private TextField addPantyID, addPantyStyle, addPantyColor, addPantyMaterial, addPantySize;

    public void AddPanty(ActionEvent event) {

        final String URL = "jdbc:mysql://localhost:3306/silkify";
        final String USERNAME = "root";
        final String PASSWORD = "";

        // Check if any text field is empty
        if (addPantyID.getText().isEmpty() || addPantyStyle.getText().isEmpty() || addPantyColor.getText().isEmpty() || addPantyMaterial.getText().isEmpty() || addPantySize.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Empty Fields", "Please fill in all fields.");
            return;
        }

        // Check for duplicate panty ID and Style
        if (isDuplicatePanty(addPantyID.getText(), addPantyStyle.getText())) {
            showAlert(AlertType.ERROR, "Duplicate Panty.", "A panty with the same ID or style already exists in the system.");
            return;
        }

        try {
            // Establish connection
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to database.");

            // Check the current count of panties
            int currentCount = getCurrentPantyCount(connection);
            if (currentCount >= 5) {
                showAlert(AlertType.ERROR, "Maximum Capacity Reached", "Maximum capacity of 5 panty has been reached.");
                return;
            }

            // Prepare SQL statement
            String query = "INSERT INTO panties (panty_id, panty_Style, panty_Color, panty_Material, panty_Size) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameters
            preparedStatement.setInt(1, Integer.parseInt(addPantyID.getText()));
            preparedStatement.setString(2, addPantyStyle.getText());
            preparedStatement.setString(3, addPantyColor.getText());
            preparedStatement.setString(4, addPantyMaterial.getText());
            preparedStatement.setString(5, addPantySize.getText());

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(AlertType.INFORMATION,"Panty Added.", "Panty added successfully!");
            } else {
                showAlert(AlertType.ERROR, "Failed.", "Failed to add the panty.");
            }

            // Close resources
            preparedStatement.close();
            connection.close();
            System.out.println("Connection closed.");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getCurrentPantyCount(Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM panties";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
            return 0;
        }
    }

         //check if there is a duplicate
    private boolean isDuplicatePanty(String pantyID, String pantyStyle) {
        final String URL = "jdbc:mysql://localhost:3306/silkify";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String query = "SELECT * FROM panties WHERE panty_id = ? OR panty_Style = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, pantyID);
            preparedStatement.setString(2, pantyStyle);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if a row is found, indicating a duplicate
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }

    public void numberOnlyChecker(){
         addPantyID.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!"-0123456789".contains(event.getCharacter())) {
                event.consume();
            }
        });
     }

    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        
        // Set the icon for the alert dialog
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/images/silkifylogo.png"));
    
        ImageView imageView = new ImageView(new Image("/images/alertpic.png"));
        imageView.setFitWidth(48); // Set the desired width
        imageView.setFitHeight(48); // Set the desired height

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setGraphic(imageView);

        alert.showAndWait();
        
    }
}