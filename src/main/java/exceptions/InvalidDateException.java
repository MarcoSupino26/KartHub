package exceptions;

import start.Main;

public class InvalidDateException extends RuntimeException{
    private static final String errorMessage = "La data inserita è antecedente a oggi.";

    public InvalidDateException(){
        super(errorMessage);
    }

    public InvalidDateException(String message){
        super(message);
    }

    public void handleException(){
        if(Main.isCLI()) System.out.println(errorMessage);
        else PopUpManager.showPopUp(errorMessage);
    }

}
