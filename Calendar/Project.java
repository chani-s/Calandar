import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Project extends Application {

    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ProjectController.fxml"));
        Scene sc = new Scene(root);
        primaryStage.setTitle("Cal-Luach");
        primaryStage.setScene(sc);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);

    }
}
