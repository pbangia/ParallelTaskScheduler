package app.exceptions.input;

import app.exceptions.AppException;

public class EmptyFileContentsException extends AppException {

    public EmptyFileContentsException(String message) {
        super(message);
    }
}
