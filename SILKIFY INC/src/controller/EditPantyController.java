package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditPantyController {

    @FXML
    TextField editPantyID, editPantyStyle, editPantyColor, editPantyMaterial, editPantySize;

    public void EditPanty(ActionEvent event) {
        final String URL = "jdbc:mysql://localhost:3306/silkify";
        final String USERNAME = "root";
        final String PASSWORD = "";
    
        if (editPantyID.getText().isEmpty() || editPantyStyle.getText().isEmpty() || editPantyColor.getText().isEmpty() || editPantyMaterial.getText().isEmpty() || editPantySize.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Empty Fields", "Please fill in all fields.");
            return;
        }
    
        try {
            // Establish connection
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to database.");
    
            // Prepare SQL statement
            String query = "UPDATE panties SET panty_Style = ?, panty_Color = ?, panty_Material = ?, panty_Size = ? WHERE panty_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
    
            // Set parameters
            preparedStatement.setString(1, editPantyStyle.getText());
            preparedStatement.setString(2, editPantyColor.getText());
            preparedStatement.setString(3, editPantyMaterial.getText());
            preparedStatement.setString(4, editPantyStyle.getText());
            preparedStatement.setInt(5, Integer.parseInt(editPantyID.getText()));
    
            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(AlertType.INFORMATION, "Panty Updated", "Panty Updated Successfully");
            } else {
                showAlert(AlertType.ERROR, "Panty Information Not Found", "The panty information entered does not exist.");
            }
    
            preparedStatement.close();
            connection.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void numberOnlyChecker(){
         editPantyID.addEventFilter(KeyEvent.KEY_TYPED, event -> {
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