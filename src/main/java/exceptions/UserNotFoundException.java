package exceptions;

import start.Main;

public class UserNotFoundException extends RuntimeException {
    private static final String errorMessage = "Credenziali non valide. Riprova";

    public UserNotFoundException() {
        super(errorMessage);
    }

    public void handleException(){
        if(Main.isCLI()){
            System.out.println(errorMessage);
        }else PopUpManager.showPopUp(errorMessage);
    }
}
