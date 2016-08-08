package app.exceptions.argumentparsing;

import app.exceptions.AppException;

public class InvalidArgumentTypeException extends AppException {

    public InvalidArgumentTypeException(String message) { super(message);   }
}