package exceptions;

import start.Main;

public class InvalidDateFormatException extends RuntimeException{
    private static final String errorMessage = "Formato data non valido";

    public InvalidDateFormatException(){
        super(errorMessage);
    }

    public void handleException(){
        if(Main.isCLI()){
            System.out.println(errorMessage);
        }else PopUpManager.showPopUp(errorMessage);
    }
}
