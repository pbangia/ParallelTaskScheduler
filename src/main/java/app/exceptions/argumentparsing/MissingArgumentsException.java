package app.exceptions.argumentparsing;

import app.exceptions.AppException;

public class MissingArgumentsException extends AppException{

    public MissingArgumentsException(String message) {
        super(message);
    }
}
