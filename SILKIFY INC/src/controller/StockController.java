package controller;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StockController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Text style1, color1, material1, size1, style2, color2, material2, size2, style3, color3, material3, size3, style4, color4, material4, size4, style5, color5, material5, size5, pantyID1, pantyID2, pantyID3, pantyID4, pantyID5;

    @FXML
    Pane pane1, pane2, pane3, pane4, pane5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            refreshPantyList(); 
    }

    public void refreshPantyList() {
        final String URL = "jdbc:mysql://localhost:3306/silkify";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            // Establish connection
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to database.");

            // Retrieve and display book data for each book in the database
            String query = "SELECT * FROM panties";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process results
            int index = 0;
            while (resultSet.next() && index < 5) { // Limit to 5 panties for display
                int pantyId = resultSet.getInt("panty_id");
                String pantyStyle = resultSet.getString("panty_Style");
                String pantyColor = resultSet.getString("panty_Color");
                String pantyMaterial = resultSet.getString("panty_Material");
                String pantySize = resultSet.getString("panty_Size");
    
                // Retrieve references for Text and Pane based on index
                Text sizeText = getSizeText(index);
                Text materialText = getMaterialText(index);
                Text colorText = getColorText(index);
                Text styleText = getStyleText(index);
                Text pantyIDText = getPantyIDText(index);
                Pane pane = getPane(index);

                // Display values in text fields
                sizeText.setText(pantySize);
                materialText.setText(pantyMaterial);
                colorText.setText(pantyColor);
                styleText.setText(pantyStyle);
                pantyIDText.setText("PANTY ID# " + pantyId);
                pane.setVisible(true);

                index++;
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Text getSizeText(int index) {
        switch (index) {
            case 0:
                return size1;
            case 1:
                return size2;
            case 2:
                return size3;
            case 3:
                return size4;
            case 4:
                return size5;
            default:
                return null;
        }
    }

    private Text getMaterialText(int index) {
        switch (index) {
            case 0:
                return material1;
            case 1:
                return material2;
            case 2:
                return material3;
            case 3:
                return material4;
            case 4:
                return material5;
            default:
                return null;
        }
    }

    private Text getColorText(int index) {
        switch (index) {
            case 0:
                return color1;
            case 1:
                return color2;
            case 2:
                return color3;
            case 3:
                return color4;
            case 4:
                return color5;
            default:
                return null;
        }
    }

    private Text getStyleText(int index) {
        switch (index) {
            case 0:
                return style1;
            case 1:
                return style2;
            case 2:
                return style3;
            case 3:
                return style4;
            case 4:
                return style5;
            default:
                return null;
        }
    }

    private Text getPantyIDText(int index) {
        switch (index) {
            case 0:
                return pantyID1;
            case 1:
                return pantyID2;
            case 2:
                return pantyID3;
            case 3:
                return pantyID4;
            case 4:
                return pantyID5;
            default:
                return null;
        }
    }

    private Pane getPane(int index) {
        switch (index) {
            case 0:
                return pane1;
            case 1:
                return pane2;
            case 2:
                return pane3;
            case 3:
                return pane4;
            case 4:
                return pane5;
            default:
                return null;
        }
    }
    
    public void ffStocks(ActionEvent event)throws IOException{
        root = FXMLLoader.load(getClass().getResource("/view/stock_page.fxml")); 
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void goToAddPantypage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addPantypage.fxml"));
        Parent addPantyPageRoot = loader.load();
        Stage addPantyStage = new Stage();
        addPantyStage.setTitle("Add Panty");
        addPantyStage.setScene(new Scene(addPantyPageRoot));
        addPantyStage.setResizable(false);
        addPantyStage.showAndWait(); // Show and wait for it to be closed before refreshing
        refreshPantyList(); 
    }

    public void goToEditPantypage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editPantypage.fxml"));
        Parent PantyPageRoot = loader.load();
        Stage PantyStage = new Stage();
        PantyStage.setTitle("Edit Panty");
        PantyStage.setScene(new Scene(PantyPageRoot));
        PantyStage.setResizable(false);
        PantyStage.showAndWait(); // Show and wait for it to be closed before refreshing
        refreshPantyList(); 
    }

    public void goToDeletePantypage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/deletePantypage.fxml"));
        Parent PantyPageRoot = loader.load();
        Stage PantyStage = new Stage();
        PantyStage.setTitle("Delete Panty");
        PantyStage.setScene(new Scene(PantyPageRoot));
        PantyStage.setResizable(false);
        PantyStage.showAndWait(); // Show and wait for it to be closed before refreshing
    }

    public void goToHomepage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/homepage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}