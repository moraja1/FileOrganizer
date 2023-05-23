package una.filesorganizeridoffice.viewmodel;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

public class WindowController implements Initializable {
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
    private IntegerProperty initialRow = new SimpleIntegerProperty();
    private IntegerProperty finalRow = new SimpleIntegerProperty();
    private final WindowInfo info = new WindowInfo();

    /***
     * Performs activities at the beginning at the system such as configuration of TabSelectionChanged events. Formatters'
     * configuration for inputs and binding configuration for the ViewModel.
     * @param url
     * @param resourceBundle
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

    /***
     * Creates a formatter to be placed in any TextField. This formatter allow the TextField to receive only numbers
     * Keyboard.
     * @return TexTFormatter
     */
    private TextFormatter<?> getFormatter() {
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            } else {
                return null;
            }
        });

        return formatter;
    }

    /***
     * Event at any search button in the window. Verifies the source of the event and calls verifyBtnInput method.
     * @param e
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

    /***
     * Makes sure the TextField related to that Button is empty or not. If not, calls processInput method to check
     * if the information in the TextField is an existing File or not. If not, opens a DirectoryChooser or a FileChooser
     * depending on what is supposed to be selected by the user.
     * @param obj Object DirectoryChooser or FileChooser
     * @return String
     */
    public String verifyBtnInput(Object obj){
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
            fileChosen = null;
        }
        url = fileChosen.getAbsolutePath();
        return correctURL(url);
    }

    /***
     * Verifies if the url passed by parameter corresponds to an existing file or not.
     * @param urlInserted
     * @return boolean
     */
    private boolean verifyURLExistence(String urlInserted) {
        File f = new File(urlInserted);
        if(f.exists()){
           return true;
        }
        return false;
    }

    /***
     * Verifies if the URL has wildcard at the beginning and delete it if it does.
     * @param url
     * @return String
     */
    private String correctURL(String url) {
        if(url.startsWith("\\\\?\\")){
            url = url.substring(4);
        }
        return url;
    }

    /***
     * Clears all TextFields in the Window.
     */
    private void clearWindow(){
        excelTextBox.clear();
        pdfTextBox.clear();
        photoTextBox.clear();
        outTextBox.clear();
        firstRowTextBox.clear();
        lastRowTextBox.clear();
    }

    /***
     * Enable or Disable controls to stop user of changing any information during the organization of the files. This
     * is an additional security method.
     * @param isEnable
     */
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

    /***
     * Verifies that the initial row number is not a higher number than the final, and the files are existing files.
     * This method does not check if the files contains the proper fileTypes, this operation will be performed by the
     * Security layer.
     * @return
     */
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

    /***
     * Bind the WindowInfo to the Window controls to be change in realtime.
     */
    private void bindInfoModelProperties() {
        //Binding Properties
        Bindings.bindBidirectional(firstRowTextBox.textProperty(), initialRow, info.getConverter());
        Bindings.bindBidirectional(lastRowTextBox.textProperty(), finalRow, info.getConverter());

        info.initialRowProperty().bind(initialRow);
        info.finalRowProperty().bind(finalRow);
        info.pdfFileUrlProperty().bindBidirectional(pdfTextBox.textProperty());
        info.outputFileUrlProperty().bindBidirectional(outTextBox.textProperty());
        info.photoFileUrlProperty().bindBidirectional(photoTextBox.textProperty());
        info.excelFileUrlProperty().bindBidirectional(excelTextBox.textProperty());
    }

    /***
     * Unbind the WindowInfo object from the window to secure the information from changes.
     */
    private void unbindInfoModelProperties() {
        //Binding Properties
        Bindings.unbindBidirectional(firstRowTextBox.textProperty(), initialRow);
        Bindings.unbindBidirectional(lastRowTextBox.textProperty(), finalRow);

        info.initialRowProperty().unbindBidirectional(initialRow);
        info.finalRowProperty().unbindBidirectional(finalRow);
        info.pdfFileUrlProperty().unbindBidirectional(pdfTextBox.textProperty());
        info.outputFileUrlProperty().unbindBidirectional(outTextBox.textProperty());
        info.photoFileUrlProperty().unbindBidirectional(photoTextBox.textProperty());
        info.excelFileUrlProperty().unbindBidirectional(excelTextBox.textProperty());
    }

    /***
     * Performs close and minimize operations.
     * @param e
     */
    public void menuBarBtn_Clicked(ActionEvent e) {
        if(e.getSource() == closeBtn){
            System.exit(0);
        }
        if(e.getSource() == minimizeBtn){
            ((Stage) anchor.getScene().getWindow()).setIconified(true);
        }
    }

    /***
     * Shows an error message when necessary.
     * @param title
     * @param message
     */
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
                        b.startOrganization(info, isStudent);
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
        System.out.println();
    }

    /***
     * Contains variables to drag window.
     */
    private static class WindowOffsetInfo {
        public static Double xOffset;
        public static Double yOffset;
    }
}