package una.filesorganizatoridoffice.viewmodel;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

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
    private Tab tabEmployee;
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

    private void bindInfoModelProperties() {
        //Binding Properties
        Bindings.bindBidirectional(firstRowTextBox.textProperty(), initialRow, info.getConverter());
        Bindings.bindBidirectional(lastRowTextBox.textProperty(), finalRow, info.getConverter());

        info.initialRowProperty().bind(initialRow);
        info.finalRowProperty().bind(finalRow);
        info.pdfFileURLProperty().bindBidirectional(pdfTextBox.textProperty());
        info.outputFileURLProperty().bindBidirectional(outTextBox.textProperty());
        info.photoFileURLProperty().bindBidirectional(photoTextBox.textProperty());
        info.excelFileURLProperty().bindBidirectional(excelTextBox.textProperty());
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
            info.setPdfFileURL(verifyBtnInput(e, info.getPdfFileURL(), dc));
        }
        if(e.getSource() == excelSearchBtn){
            fc.setTitle("Seleccione el excel de solicitudes");
            info.setExcelFileURL(verifyBtnInput(e, info.getExcelFileURL(), fc));
        }
        if(e.getSource() == outSearchBtn){
            dc.setTitle("Seleccione la carpeta de salida");
            info.setOutputFileURL(verifyBtnInput(e, info.getOutputFileURL(), dc));
        }
        if(e.getSource() == photoSearchBtn){
            dc.setTitle("Seleccione la carpeta de las fotos");
            info.setPhotoFileURL(verifyBtnInput(e, info.getPhotoFileURL(), dc));
        }
    }

    /***
     * Makes sure the TextField related to that Button is empty or not. If not, calls processInput method to check
     * if the information in the TextField is an existing File or not. If not, opens a DirectoryChooser or a FileChooser
     * depending on what is supposed to be selected by the user.
     * @param e
     * @param urlInserted
     * @param dc
     * @return String
     */
    public String verifyBtnInput(ActionEvent e, String urlInserted, DirectoryChooser dc){
        File fileChosen;
        String url;
        File initialDir = new File("G:\\Mi unidad");
        if(initialDir.exists()){
            dc.setInitialDirectory(initialDir);
        }
        if(!processInput(urlInserted)){
            do {
                fileChosen = dc.showDialog(new Stage());
            }while (fileChosen == null);
            url = fileChosen.getAbsolutePath();
        }else{
            url = urlInserted;
        }
        return correctURL(url);
    }

    /***
     * Makes sure the TextField related to that Button is empty or not. If not, calls processInput method to check
     * if the information in the TextField is an existing File or not. If not, opens a DirectoryChooser or a FileChooser
     * depending on what is supposed to be selected by the user.
     * @param e
     * @param urlInserted
     * @param dc
     * @return String
     */
    public String verifyBtnInput(ActionEvent e, String urlInserted, FileChooser dc){
        File fileChosen;
        String url;
        Path downloadsDir = Paths.get(System.getProperty("user.home"), "Downloads");
        if (Files.exists(downloadsDir)) {
            dc.setInitialDirectory(downloadsDir.toFile());
        }
        if(!processInput(urlInserted)){
            do {
                fileChosen = dc.showOpenDialog(new Stage());
            }while(fileChosen == null);
            url = fileChosen.getAbsolutePath();
        }else{
            url = urlInserted;
        }
        return correctURL(url);
    }

    /***
     * Verifies if the url passed by parameter corresponds to an existing file or not.
     * @param urlInserted
     * @return boolean
     */
    private boolean processInput(String urlInserted) {
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
     * Verifies if the row numbers are correctly introduced. If it is, calls service classes to process the organization
     * of the files.
     * @param event
     */
    @FXML
    void startBtn_Clicked(ActionEvent event) {
        Thread startThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(tabStudent.isSelected()){
                    if(isStudentInfoComplete()){

                    }else{

                    }
                }else{
                    if (isWorkerInfoComplete()) {

                    }else{

                    }
                }
            }
        });
        startThread.start();
    }

    private boolean isWorkerInfoComplete() {
        return !(info.getPdfFileURL().isEmpty() && info.getOutputFileURL().isEmpty()
                && info.getExcelFileURL().isEmpty()) && (info.getInitialRow() <= info.getFinalRow());
    }

    private boolean isStudentInfoComplete() {
        return !(info.getPdfFileURL().isEmpty() && info.getPhotoFileURL().isEmpty() && info.getOutputFileURL().isEmpty()
                && info.getExcelFileURL().isEmpty()) && (info.getInitialRow() <= info.getFinalRow());
    }

    private void unbindInfoModelProperties() {
        //Binding Properties
        Bindings.unbindBidirectional(firstRowTextBox.textProperty(), initialRow);
        Bindings.unbindBidirectional(lastRowTextBox.textProperty(), finalRow);

        info.initialRowProperty().unbindBidirectional(initialRow);
        info.finalRowProperty().unbindBidirectional(finalRow);
        info.pdfFileURLProperty().unbindBidirectional(pdfTextBox.textProperty());
        info.outputFileURLProperty().unbindBidirectional(outTextBox.textProperty());
        info.photoFileURLProperty().unbindBidirectional(photoTextBox.textProperty());
        info.excelFileURLProperty().unbindBidirectional(excelTextBox.textProperty());
    }

    public void menuBarBtn_Clicked(ActionEvent e) {
        if(e.getSource() == closeBtn){
            System.exit(0);
        }
        if(e.getSource() == minimizeBtn){
            ((Stage) anchor.getScene().getWindow()).setIconified(true);
        }
    }

    private static class WindowOffsetInfo {
        public static Double xOffset;
        public static Double yOffset;
    }
}