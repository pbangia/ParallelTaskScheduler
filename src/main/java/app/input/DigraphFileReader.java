package app.input;

import app.exceptions.input.EmptyFileContentsException;
import app.exceptions.input.InvalidFileContentsException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class DigraphFileReader {

    private static Logger logger = LoggerFactory.getLogger(DigraphFileReader.class);

    private File digraphFile;

    public DigraphFileReader(File inputFile) {
        this.digraphFile = inputFile;
    }

    public String readDigraphFile() throws EmptyFileContentsException, InvalidFileContentsException, IOException {
        String fileText = FileUtils.readFileToString(digraphFile);

        if (fileText == null || fileText.length() == 0) {
            throw new EmptyFileContentsException("File contents are empty.");
        }

        return parse(fileText);
    }

    private String parse(String fileText) throws InvalidFileContentsException {
        if (!validFileText(fileText)) {
            throw new InvalidFileContentsException("Invalid text found in file.");
        }

        return fileText.substring(fileText.indexOf("{") + 1, fileText.indexOf("}"));
    }

    private boolean validFileText(String fileText) {
        // TODO more guard clauses required
        if (!fileText.contains("{")) {
            return false;
        }
        if (!fileText.contains("}")) {
            return false;
        }

        return true;
    }


}
