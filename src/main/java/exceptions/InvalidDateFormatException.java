package exceptions;

import start.Main;

public class InvalidDateFormatException extends RuntimeException{
    private static final String ERRMSG = "Formato data non valido";

    public InvalidDateFormatException(){
        super(ERRMSG);
    }

    public void handleException(){
        if(Main.isCLI()){
            System.out.println(ERRMSG);
        }else PopUpManager.showPopUp(ERRMSG);
    }
}
