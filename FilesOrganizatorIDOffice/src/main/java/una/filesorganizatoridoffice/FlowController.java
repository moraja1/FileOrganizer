package una.filesorganizatoridoffice;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FlowController {
    private Stage mainStage;
    public FlowController(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FlowController.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage = stage;
        mainStage.setTitle("Hello!");
        mainStage.setScene(scene);
        mainStage.show();
    }
}
