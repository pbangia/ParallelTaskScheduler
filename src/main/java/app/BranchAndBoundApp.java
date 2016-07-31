package app;

import app.data.Node;
import app.exceptions.input.EmptyFileContentsException;
import app.exceptions.input.InvalidFileContentsException;
import app.input.DigraphFileReader;
import app.transform.DataTransformer;

import java.io.File;
import java.util.Map;

public class BranchAndBoundApp {

    private File inputFile;
    private String outputFilename;
    private int numThreads;
    private int numProcessors;
    private boolean graphRequired;

    public BranchAndBoundApp(File inputFile, String outputFilename, int numProcessors,
                             int numThreads, boolean graphRequired) {
        this.inputFile = inputFile;
        this.outputFilename = outputFilename;
        this.numProcessors = numProcessors;
        this.numThreads = numThreads;
        this.graphRequired = graphRequired;
    }

    /**
     * Starting point for the branch and bound application.
     */
    public void start() {
        init();
        // more method calls underneath
    }

    /**
     * Loads any modules required to run the application.
     */
    private void init() {
        String fileContents = readFile();
        Map<String, Node> dataMap = DataTransformer.get().transformIntoMap(fileContents);
        System.out.println(fileContents);
    }

    private String readFile() {
        String fileContents = null;
        try {
            fileContents = DigraphFileReader.readDigraphFile(inputFile);
        } catch (InvalidFileContentsException e) {
            // print error log message
        } catch (EmptyFileContentsException e) {
            // print error log message
        }
        return fileContents;
    }

}
