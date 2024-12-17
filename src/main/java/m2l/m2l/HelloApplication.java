package m2l.m2l;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/m2l/m2l/menu.fxml"));
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/m2l/m2l/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle("application m2l");
            stage.setMaxHeight(1200);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
