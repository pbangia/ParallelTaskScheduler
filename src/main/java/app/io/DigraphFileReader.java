package app.io;

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

    /**
     * Reads the input file to a String object.
     * @return String representation of the input file.
     * @throws EmptyFileContentsException
     * @throws InvalidFileContentsException
     * @throws IOException
     */
    public String readDigraphFile() throws EmptyFileContentsException, InvalidFileContentsException, IOException {
        String fileText = FileUtils.readFileToString(digraphFile);

        if (fileText == null || fileText.length() == 0) {
            throw new EmptyFileContentsException("File contents are empty.");
        }

        return parse(fileText);
    }

    /**
     * Strips the input file to return the text representing only relevant information.
     * @param fileText Raw string representation of the input file.
     * @return Stripped string representation of the input file.
     * @throws InvalidFileContentsException
     */
    private String parse(String fileText) throws InvalidFileContentsException {
        if (!validFileText(fileText)) {
            throw new InvalidFileContentsException("Invalid text found in file.");
        }

        return fileText.substring(fileText.indexOf("{") + 1, fileText.indexOf("}"));
    }

    private boolean validFileText(String fileText) {
        if (!fileText.contains("{") || !fileText.contains("}")) {
            return false;
        }
        return true;
    }


}
