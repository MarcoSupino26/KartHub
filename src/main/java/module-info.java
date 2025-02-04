module karthub{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;

    exports utils.session;
    exports utils;
    exports models.slots;
    exports models.track;
    exports models.event;
    exports models.user;
    exports controllers;
    exports beans;
    exports start;
    exports graphicalcontroller.gui;
    opens graphicalcontroller.gui to javafx.fxml;
}