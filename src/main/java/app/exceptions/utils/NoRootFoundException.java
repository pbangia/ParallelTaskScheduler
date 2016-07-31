package app.exceptions.utils;

import app.exceptions.AppException;

public class NoRootFoundException extends AppException {

    public NoRootFoundException(String message) {
        super(message);
    }

}
