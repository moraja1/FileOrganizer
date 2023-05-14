package una.filesorganizeridoffice;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class FlowController {
    private Stage mainStage;
    public FlowController(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FlowController.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage = stage;
        mainStage.setTitle("Organizador de Archivos de Carné Estudiantil");
        mainStage.setScene(scene);
        mainStage.getIcons().add(new Image(getClass().getResource("icons/carpet.png").openStream()));
        mainStage.setResizable(false);
        mainStage.initStyle(StageStyle.UNDECORATED);
        mainStage.show();
    }
}
