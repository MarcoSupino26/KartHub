package exceptions;

import start.Main;

public class EmptyFieldException extends RuntimeException{
    private static final String ERRMSG = "Compila tutti i campi prima di proseguire";

    public EmptyFieldException() {
        super(ERRMSG);
    }

    public EmptyFieldException(String message) {
        super(message);
    }

    public void handleException() {
        if(Main.isCLI()) System.out.println(ERRMSG);
        else PopUpManager.showPopUp(ERRMSG);
    }

}
