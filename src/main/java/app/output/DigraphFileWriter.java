package app.output;


import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class DigraphFileWriter {

    private static Logger logger = LoggerFactory.getLogger(DigraphFileWriter.class);

    private File outputFile;

    public DigraphFileWriter(String outputFilename){
        this.outputFile = new File(outputFilename);
    }

    public void writeDigraphFile(String solutionMap){
        try {
            FileUtils.writeStringToFile(outputFile, solutionMap);
        } catch (IOException e) {
            logger.error("Error in writing to output file: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
}
