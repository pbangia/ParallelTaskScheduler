package app.input;

import java.io.File;

public class InputModuleFactory {

    public static DigraphFileReader createReader(File inputFile) {
        return new DigraphFileReader(inputFile);
    }

}
