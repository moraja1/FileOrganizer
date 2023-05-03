package una.filesorganizatoridoffice.viewmodel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class WindowController {

    @FXML
    private Button excelSearchBtn;

    @FXML
    private Button excelSearchBtn_Emp;

    @FXML
    private TextField excelTextBox;

    @FXML
    private TextField excelTextBox_Emp;

    @FXML
    private TextField firstRowTextBox;

    @FXML
    private TextField firstRowTextBox_Emp;

    @FXML
    private TextField lastRowTextBox;

    @FXML
    private TextField lastRowTextBox_Emp;

    @FXML
    private Button outSearchBtn;

    @FXML
    private Button outSearchBtn_Emp;

    @FXML
    private TextField outTextBox;

    @FXML
    private TextField outTextBox_Emp;

    @FXML
    private Button pdfSearchBtn;

    @FXML
    private Button pdfSearchBtn_Emp;

    @FXML
    private TextField pdfTextBox;

    @FXML
    private TextField pdfTextBox_Emp;

    @FXML
    private Button photoSearchBtn;

    @FXML
    private TextField photoTextBox;

    @FXML
    private Button startBtn;

    @FXML
    private Button startBtn_Emp;

    @FXML
    private Tab tabEmployee;

    @FXML
    private Tab tabStudent;

    private String pdfFileURL = "";
    private String photoFileURL = "";
    private String excelFileURL = "";
    private String outputFileURL = "";
    private Integer initialRow = 0;
    private Integer finalRow = initialRow+1;

    @FXML
    void searchBtn_Clicked(ActionEvent e) {
        DirectoryChooser dc = new DirectoryChooser();
        FileChooser fc = new FileChooser();
        if(e.getSource() == pdfSearchBtn){
            dc.setTitle("Seleccione la carpeta de los PDF");
            pdfFileURL = verifyBtnInput(e, pdfTextBox, dc);
        }
        if(e.getSource() == excelSearchBtn){
            fc.setTitle("Seleccione el excel de solicitudes");
            excelFileURL = verifyBtnInput(e, excelTextBox, fc);
        }
        if(e.getSource() == outSearchBtn){
            dc.setTitle("Seleccione la carpeta de salida");
            outputFileURL = verifyBtnInput(e, outTextBox, dc);
        }
        if(e.getSource() == photoSearchBtn){
            dc.setTitle("Seleccione la carpeta de las fotos");
            photoFileURL = verifyBtnInput(e, photoTextBox, dc);
        }
        if(e.getSource() == pdfSearchBtn_Emp){
            dc.setTitle("Seleccione la carpeta de los PDF");
            pdfFileURL = verifyBtnInput(e, pdfTextBox_Emp, dc);
        }
        if(e.getSource() == excelSearchBtn_Emp){
            fc.setTitle("Seleccione el excel de solicitudes");
            excelFileURL = verifyBtnInput(e, excelTextBox_Emp, fc);
        }
        if(e.getSource() == outSearchBtn_Emp){
            dc.setTitle("Seleccione la carpeta de salida");
            outputFileURL = verifyBtnInput(e, outTextBox_Emp, dc);
        }
        uploadWindow();
    }

    public String verifyBtnInput(ActionEvent e, TextField tf, DirectoryChooser dc){
        File fileChosen;
        String url;
        dc.setInitialDirectory(new File("G:\\Mi unidad"));
        if(!processInput(tf.getText())){
            do {
                fileChosen = dc.showDialog(new Stage());
            }while (fileChosen == null);
            url = fileChosen.getAbsolutePath();
        }else{
            url = excelTextBox.getText();
        }
        return correctURL(url);
    }
    public String verifyBtnInput(ActionEvent e, TextField tf, FileChooser dc){
        File fileChosen;
        String url;
        dc.setInitialDirectory(new File("C:\\Users\\N00148095\\Downloads"));
        if(!processInput(tf.getText())){
            do {
                fileChosen = dc.showOpenDialog(new Stage());
            }while(fileChosen == null);
            url = fileChosen.getAbsolutePath();
        }else{
            url = excelTextBox.getText();
        }
        return correctURL(url);
    }

    @FXML
    void startBtn_Clicked(ActionEvent event) {

    }

    private boolean processInput(String text) {
        if(text.isEmpty()){
            return false;
        }
        File f = new File(text);
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

    private void uploadWindow() {
        if(tabStudent.isSelected()){
            excelTextBox.setText(excelFileURL);
            pdfTextBox.setText(pdfFileURL);
            photoTextBox.setText(photoFileURL);
            outTextBox.setText(outputFileURL);
        }else{
            excelTextBox_Emp.setText(excelFileURL);
            pdfTextBox_Emp.setText(pdfFileURL);
            outTextBox_Emp.setText(outputFileURL);
        }

    }
}


