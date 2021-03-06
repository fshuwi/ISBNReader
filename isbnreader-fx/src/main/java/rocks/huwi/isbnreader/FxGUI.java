package rocks.huwi.isbnreader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FxGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FxGUI.fxml"));
        primaryStage.setTitle("ISBN Reader");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
