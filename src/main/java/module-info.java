module karthub{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;


    exports controllers;
    exports beans;
    exports views;
    exports start;
    exports views.topbar;
    opens views.topbar to javafx.fxml;
    opens views to javafx.fxml;
}