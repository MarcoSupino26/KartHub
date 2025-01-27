package exceptions;

import start.Main;

public class InvalidDateException extends RuntimeException{
    private static final String ERRMSG = "La data inserita è antecedente a oggi.";

    public InvalidDateException(){
        super(ERRMSG);
    }

    public InvalidDateException(String message){
        super(message);
    }

    public void handleException(){
        if(Main.isCLI()) System.out.println(ERRMSG);
        else PopUpManager.showPopUp(ERRMSG);
    }

}
