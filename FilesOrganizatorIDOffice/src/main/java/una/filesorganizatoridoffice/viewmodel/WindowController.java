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

    private String pdfFileURL;
    private String photoFileURL;
    private String excelFileURL;
    private String outputFileURL;
    private Integer initialRow;
    private Integer finalRow;

    @FXML
    void searchBtn_Clicked(ActionEvent e) {
        File fileChosen;
        DirectoryChooser dc = new DirectoryChooser();
        FileChooser fc = new FileChooser();
        if(e.getSource() == pdfSearchBtn){

        }
        if(e.getSource() == photoSearchBtn){

        }
        if(e.getSource() == excelSearchBtn){

        }
        if(e.getSource() == outSearchBtn){

        }
        if(e.getSource() == pdfSearchBtn_Emp){

        }
        if(e.getSource() == excelSearchBtn_Emp){

        }
        if(e.getSource() == outSearchBtn_Emp){

        }
    }

    @FXML
    void startBtn_Clicked(ActionEvent event) {

    }
}


