package exceptions;

import start.Main;

public class UserNotFoundException extends RuntimeException {
    private static final String ERRMSG = "Credenziali non valide. Riprova";

    public UserNotFoundException() {
        super(ERRMSG);
    }

    public void handleException(){
        if(Main.isCLI()){
            System.out.println(ERRMSG);
        }else PopUpManager.showPopUp(ERRMSG);
    }
}
