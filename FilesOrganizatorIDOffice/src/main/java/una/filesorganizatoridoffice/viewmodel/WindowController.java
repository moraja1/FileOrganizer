package una.filesorganizatoridoffice.viewmodel;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
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
import javafx.util.StringConverter;
import una.filesorganizatoridoffice.business.Protocol;
import una.filesorganizatoridoffice.business.Security;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class WindowController implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Change tab event
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
            url = excelTextBox.getText();
        }
        return correctURL(url);
    }
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
            url = excelTextBox.getText();
        }
        return correctURL(url);
    }

    private boolean processInput(String urlInserted) {
        File f = new File(urlInserted);
        if(f.exists()){
           return true;
        }
        return false;
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

    @FXML
    void startBtn_Clicked(ActionEvent event) {
        if(!(info.getFinalRow() < info.getInitialRow())){
        }
    }
}