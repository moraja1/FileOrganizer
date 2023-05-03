package una.filesorganizatoridoffice;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import una.filesorganizatoridoffice.viewmodel.WindowController;

import java.io.IOException;
import java.util.Objects;

public class FlowController {
    private Stage mainStage;
    public FlowController(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FlowController.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage = stage;
        mainStage.setTitle("Hello!");
        mainStage.setScene(scene);
        mainStage.getIcons().add(new Image(getClass().getResource("icons/carpet.png").openStream()));
        mainStage.setResizable(false);
        mainStage.show();
    }
}
