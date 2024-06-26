package controller;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;



public class DeletePantyController {


    @FXML
    private TextField pantyID;

    public void DeletePanty(ActionEvent event) {
        final String URL = "jdbc:mysql://localhost:3306/silkify";
        final String USERNAME = "root";
        final String PASSWORD = "";

        if (pantyID.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Empty Fields", "Please fill in all fields.");
            return;
        }

        try {
            // Establish connection
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to database.");

            // Prepare SQL statement
            String query = "DELETE FROM panties WHERE panty_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameter
            preparedStatement.setInt(1, Integer.parseInt(pantyID.getText()));

            // Execute the delete
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(AlertType.INFORMATION, "Panty Deleted", "Panty Deleted Successfully");
            } else {
            
                showAlert(AlertType.ERROR, "Panty ID Not Found", "The Panty ID entered does not exist.");
            }

 
            preparedStatement.close();
            connection.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     public void numberOnlyChecker(){
         pantyID.addEventFilter(KeyEvent.KEY_TYPED, event -> {
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
