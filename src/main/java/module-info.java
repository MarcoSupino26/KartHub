module karthub{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    exports views;
    exports start;
    opens views to javafx.fxml;
}