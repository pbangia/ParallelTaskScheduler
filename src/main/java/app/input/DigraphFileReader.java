package app.input;

import app.exceptions.input.EmptyFileContentsException;
import app.exceptions.input.InvalidFileContentsException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class DigraphFileReader {

    public String readDigraphFile(File digraphFile) throws EmptyFileContentsException, InvalidFileContentsException {
        String fileText = null;
        try {
            fileText = FileUtils.readFileToString(digraphFile);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO add error log message
        }

        if (fileText == null){
            throw new EmptyFileContentsException("File contents are empty.");
        }

        return parse(fileText);
    }

    private String parse(String fileText) throws InvalidFileContentsException {
        if (!validFileText(fileText)){
            throw new InvalidFileContentsException("Invalid text found in file.");
        }

        return fileText.substring(fileText.indexOf("{") + 1, fileText.indexOf("}"));
    }

    private boolean validFileText(String fileText) {
        // TODO more guard clauses required
        if (!fileText.contains("{")){
            return false;
        }
        if (!fileText.contains("}")){
            return false;
        }

        return true;
    }


}
