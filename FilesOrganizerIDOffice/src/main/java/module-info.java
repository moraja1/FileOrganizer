module una.filesorganizeridoffice {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    opens una.filesorganizeridoffice to javafx.fxml;
    exports una.filesorganizeridoffice;
    exports una.filesorganizeridoffice.viewmodel;
    opens una.filesorganizeridoffice.viewmodel to javafx.fxml;
}