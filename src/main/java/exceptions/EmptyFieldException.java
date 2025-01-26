package exceptions;

import start.Main;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.io.Serializable;

public class EmptyFieldException extends RuntimeException{
    private static final String errorMessage = "Compila tutti i campi prima di proseguire";

    public EmptyFieldException() {
        super(errorMessage);
    }

    public EmptyFieldException(String message) {
        super(message);
    }

    public void handleException() {
        if(Main.isCLI()) System.out.println(errorMessage);
        else PopUpManager.showPopUp(errorMessage);
    }

}
