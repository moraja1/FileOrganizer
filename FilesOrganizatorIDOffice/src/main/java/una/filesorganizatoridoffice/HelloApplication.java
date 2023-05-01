package una.filesorganizatoridoffice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import una.filesorganizatoridoffice.model.IdentificationType;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        new FlowController(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}