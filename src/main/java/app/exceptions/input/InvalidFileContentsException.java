package app.exceptions.input;

import app.exceptions.AppException;

public class InvalidFileContentsException extends AppException {

    public InvalidFileContentsException(String message) {
        super(message);
    }
}
