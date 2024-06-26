import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class App extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    public void start(Stage stage) throws Exception {

        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/homepage.fxml"));
            Scene scene = new Scene(root);
            stage.getIcons().add(new Image("/images/silkifylogo.png"));
            stage.setResizable(false);
            stage.setTitle("Silkify");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}