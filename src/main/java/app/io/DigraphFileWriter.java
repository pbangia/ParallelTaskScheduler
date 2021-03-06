package app.io;


import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class DigraphFileWriter {

    private static Logger logger = LoggerFactory.getLogger(DigraphFileWriter.class);

    private File outputFile;

    public DigraphFileWriter(String outputFilename) {
        this.outputFile = new File(outputFilename);
    }

    /**
     * Writes the input String to the output file.
     * @param solutionMap input String to write to the file.
     */
    public void writeDigraphFile(String solutionMap) {
        try {
            FileUtils.writeStringToFile(outputFile, solutionMap);
            logger.info("Schedule successfully written to output file.");
        } catch (IOException e) {
            logger.error("Error in writing to output file: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
