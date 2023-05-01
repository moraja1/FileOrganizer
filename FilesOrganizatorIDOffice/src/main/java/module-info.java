module una.filesorganizatoridoffice {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    opens una.filesorganizatoridoffice to javafx.fxml;
    exports una.filesorganizatoridoffice;
    exports una.filesorganizatoridoffice.viewmodel;
    opens una.filesorganizatoridoffice.viewmodel to javafx.fxml;
}