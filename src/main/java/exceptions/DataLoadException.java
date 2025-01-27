package exceptions;

import java.sql.Connection;

public class DataLoadException extends RuntimeException{

    public DataLoadException(String message){
        super(message);
    }
}
