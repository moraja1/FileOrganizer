package una.filesorganizeridoffice.viewmodel;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import una.filesorganizeridoffice.business.Business;
import una.filesorganizeridoffice.business.exceptions.BusinessException;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/***
 * Initializable class that contains a reference for each FXML.xml file of the window.
 * @author Jaison Mora Víquez <a href="https://github.com/moraja1">Github</a>
 */
public final class WindowController implements Initializable {
    @FXML
    private AnchorPane anchor;
    @FXML
    private Button closeBtn;
    @FXML
    private HBox excelHBox;
    @FXML
    private Button excelSearchBtn;
    @FXML
    private TextField excelTextBox;
    @FXML
    private VBox excelVBox;
    @FXML
    private VBox excelVBox_Emp;
    @FXML
    private TextField firstRowTextBox;
    @FXML
    private VBox firstRowVBox;
    @FXML
    private VBox firstRowVBox_Emp;
    @FXML
    private TextField lastRowTextBox;
    @FXML
    private VBox lastRowVBox;
    @FXML
    private VBox lastRowVBox_Emp;
    @FXML
    private VBox mainVBox;
    @FXML
    private VBox mainVBox_Emp;
    @FXML
    private Button minimizeBtn;
    @FXML
    private HBox outHBox;
    @FXML
    private Button outSearchBtn;
    @FXML
    private TextField outTextBox;
    @FXML
    private VBox outVBox;
    @FXML
    private VBox outVBox_Emp;
    @FXML
    private HBox pdfHBox;
    @FXML
    private Button pdfSearchBtn;
    @FXML
    private TextField pdfTextBox;
    @FXML
    private VBox pdfVBox;
    @FXML
    private VBox pdfVBox_Emp;
    @FXML
    private Button photoSearchBtn;
    @FXML
    private TextField photoTextBox;
    @FXML
    private Button startBtn;
    @FXML
    private Tab tabStudent;
    private final WindowInfo info = new WindowInfo();

    /***
     * Performs activities at the beginning at the system such as configuration of TabSelectionChanged events. Formatters'
     * configuration for inputs and binding configuration for the ViewModel.
     * @param url inherited parameter of initialize function
     * @param resourceBundle inherited parameter of initialize function
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Set Events to drag window
        anchor.setOnMousePressed(mouseEvent -> {
            Stage stage = (Stage)anchor.getScene().getWindow();
            WindowOffsetInfo.xOffset = stage.getX() - mouseEvent.getScreenX();
            WindowOffsetInfo.yOffset = stage.getY() - mouseEvent.getScreenY();
        });

        anchor.setOnMouseDragged(mouseEvent -> {
            Stage stage = (Stage)anchor.getScene().getWindow();
            stage.setX(mouseEvent.getScreenX() + WindowOffsetInfo.xOffset);
            stage.setY(mouseEvent.getScreenY() + WindowOffsetInfo.yOffset);
        });
        //Change components at tab event
        tabStudent.setOnSelectionChanged(event -> {
            if(tabStudent.isSelected()){
                pdfVBox.getChildren().add(1, pdfHBox);
                excelVBox.getChildren().add(1, excelHBox);
                outVBox.getChildren().add(1, outHBox);
                firstRowVBox.getChildren().add(1, firstRowTextBox);
                lastRowVBox.getChildren().add(1, lastRowTextBox);
                mainVBox.getChildren().add(1, startBtn);

            }else{
                pdfVBox_Emp.getChildren().add(1, pdfHBox);
                excelVBox_Emp.getChildren().add(1, excelHBox);
                outVBox_Emp.getChildren().add(1, outHBox);
                firstRowVBox_Emp.getChildren().add(1, firstRowTextBox);
                lastRowVBox_Emp.getChildren().add(1, lastRowTextBox);
                mainVBox_Emp.getChildren().add(3, startBtn);
            }
            clearWindow();
        });

        //Row number input (Numbers Only)
        firstRowTextBox.setTextFormatter(getFormatter());
        lastRowTextBox.setTextFormatter(getFormatter());

        bindInfoModelProperties();
    }
    private TextFormatter<?> getFormatter() {
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d+")) {
                return change;
            } else {
                return null;
            }
        });
        return formatter;
    }

    /***
     * Event at any search button in the window. Verifies the source of the event and calls verifyBtnInput method.
     * @param e action event of any search button
     */
    @FXML
    void searchBtn_Clicked(ActionEvent e) {
        DirectoryChooser dc = new DirectoryChooser();
        FileChooser fc = new FileChooser();
        if(e.getSource() == pdfSearchBtn){
            dc.setTitle("Seleccione la carpeta de los PDF");
            info.setPdfFileUrl(verifyBtnInput(dc));
        }
        if(e.getSource() == excelSearchBtn){
            fc.setTitle("Seleccione el excel de solicitudes");
            info.setExcelFileUrl(verifyBtnInput(fc));
        }
        if(e.getSource() == outSearchBtn){
            dc.setTitle("Seleccione la carpeta de salida");
            info.setOutputFileUrl(verifyBtnInput(dc));
        }
        if(e.getSource() == photoSearchBtn){
            dc.setTitle("Seleccione la carpeta de las fotos");
            info.setPhotoFileUrl(verifyBtnInput(dc));
        }
    }
    private String verifyBtnInput(Object obj){
        File fileChosen = null;
        String url;
        if(obj instanceof DirectoryChooser){
            File initialDir = new File("G:\\Mi unidad");
            if(initialDir.exists()){
                ((DirectoryChooser)obj).setInitialDirectory(initialDir);
                do {
                    fileChosen = ((DirectoryChooser)obj).showDialog(new Stage());
                }while (fileChosen == null);
            }
        } else if (obj instanceof FileChooser) {
            Path downloadsDir = Paths.get(System.getProperty("user.home"), "Downloads");
            if (Files.exists(downloadsDir)) {
                ((FileChooser)obj).setInitialDirectory(downloadsDir.toFile());
            }
            do {
                fileChosen = ((FileChooser)obj).showOpenDialog(new Stage());
            }while(fileChosen == null);
        }else{
            return null;
        }
        url = fileChosen.getAbsolutePath();
        return correctURL(url);
    }
    private boolean verifyURLExistence(String urlInserted) {
        File f = new File(urlInserted);
        return f.exists();
    }
    private String correctURL(String url) {
        if(url.startsWith("\\\\?\\")){
            url = url.substring(4);
        }
        return url;
    }
    private void clearWindow(){
        excelTextBox.clear();
        pdfTextBox.clear();
        photoTextBox.clear();
        outTextBox.clear();
        firstRowTextBox.clear();
        lastRowTextBox.clear();
    }
    private void enableControls(boolean isEnable) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                pdfTextBox.setDisable(!isEnable);
                firstRowTextBox.setDisable(!isEnable);
                lastRowTextBox.setDisable(!isEnable);
                photoTextBox.setDisable(!isEnable);
                excelTextBox.setDisable(!isEnable);
                outTextBox.setDisable(!isEnable);
                pdfSearchBtn.setDisable(!isEnable);
                excelSearchBtn.setDisable(!isEnable);
                photoSearchBtn.setDisable(!isEnable);
                outSearchBtn.setDisable(!isEnable);
                startBtn.setDisable(!isEnable);
            }
        });
    }
    private boolean isInfoCorrect() {
        if(tabStudent.isSelected()){
            return (info.getInitialRow() <= info.getFinalRow()) && (verifyURLExistence(info.getPhotoFileUrl()) &&
                    verifyURLExistence(info.getOutputFileUrl()) && verifyURLExistence(info.getExcelFileUrl()) &&
                    verifyURLExistence(info.getPdfFileUrl()));
        }else{
            return (info.getInitialRow() <= info.getFinalRow()) && (verifyURLExistence(info.getOutputFileUrl()) &&
                    verifyURLExistence(info.getExcelFileUrl()) && verifyURLExistence(info.getPdfFileUrl()));
        }
    }
    private void bindInfoModelProperties() {
        //Binding Properties
        Bindings.bindBidirectional(firstRowTextBox.textProperty(), info.initialRowProperty(), info.getConverter());
        Bindings.bindBidirectional(lastRowTextBox.textProperty(), info.finalRowProperty(), info.getConverter());
        info.pdfFileUrlProperty().bindBidirectional(pdfTextBox.textProperty());
        info.outputFileUrlProperty().bindBidirectional(outTextBox.textProperty());
        info.photoFileUrlProperty().bindBidirectional(photoTextBox.textProperty());
        info.excelFileUrlProperty().bindBidirectional(excelTextBox.textProperty());
    }
    private void unbindInfoModelProperties() {
        //Binding Properties
        Bindings.unbindBidirectional(firstRowTextBox.textProperty(), info.initialRowProperty());
        Bindings.unbindBidirectional(lastRowTextBox.textProperty(), info.finalRowProperty());
        info.pdfFileUrlProperty().unbindBidirectional(pdfTextBox.textProperty());
        info.outputFileUrlProperty().unbindBidirectional(outTextBox.textProperty());
        info.photoFileUrlProperty().unbindBidirectional(photoTextBox.textProperty());
        info.excelFileUrlProperty().unbindBidirectional(excelTextBox.textProperty());
    }

    /***
     * Performs close and minimize operations.
     * @param e
     */
    @FXML
    void menuBarBtn_Clicked(ActionEvent e) {
        if(e.getSource() == closeBtn){
            System.exit(0);
        }
        if(e.getSource() == minimizeBtn){
            ((Stage) anchor.getScene().getWindow()).setIconified(true);
        }
    }
    private void showDialog(String title, String message, Alert.AlertType type) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(type);
                alert.setTitle(title);
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.setOnCloseRequest(e -> {
                    enableControls(true);
                    bindInfoModelProperties();
                });
                alert.showAndWait();
            }
        });
    }

    /***
     * Verifies the consistence of the information sent. If it is consistent, calls service classes to process the files'
     * organization.
     * @param event
     */
    @FXML
    void startBtn_Clicked(ActionEvent event) {
        enableControls(false);
        Thread startThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(isInfoCorrect()){
                    Boolean isStudent = tabStudent.isSelected();
                    unbindInfoModelProperties();
                    try{
                        Business b = new Business();

                        if(b.startOrganization(info, isStudent)){
                            showDialog("Proceso completado", "El sistema concluyó satisfactoriamente, puedes" +
                                    " revisar el reporte en el archivo llamado log.txt", Alert.AlertType.INFORMATION);
                        }
                    }catch(BusinessException e) {
                        showDialog("Error en la información", e.getMessage(), Alert.AlertType.ERROR);
                    }
                }else{
                    showDialog("Error en la Información",
                            "Existe algún error en la información ingresada, favor verificar.", Alert.AlertType.ERROR);
                }
            }
        });
        startThread.start();
    }
    private static class WindowOffsetInfo {
        public static Double xOffset;
        public static Double yOffset;
    }
}