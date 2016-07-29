package app.input;

import app.exceptions.input.EmptyFileContentsException;
import app.exceptions.input.InvalidFileContentsException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class DigraphFileReader {

    public static String readDigraphFile(File digraphFile) throws EmptyFileContentsException, InvalidFileContentsException {
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

        DigraphFileParser fileParser = new DigraphFileParser(fileText);
        return fileParser.parse();
    }


}
