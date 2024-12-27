module ispw.karthub {
    requires javafx.controls;
    requires javafx.fxml;


    opens ispw.karthub to javafx.fxml;
    exports ispw.karthub;
}