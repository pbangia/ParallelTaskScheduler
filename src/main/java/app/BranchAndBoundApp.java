package app;

import app.data.Node;
import app.exceptions.input.EmptyFileContentsException;
import app.exceptions.input.InvalidFileContentsException;
import app.input.DigraphFileReader;
import app.input.InputModuleFactory;
import app.transform.DataTransformer;
import app.transform.TransformModuleFactory;

import java.io.File;
import java.util.Map;

public class BranchAndBoundApp {

    private DataTransformer dataTransformer;
    private DigraphFileReader digraphFileReader;
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
        loadModules();
        init();
        // more method calls underneath
    }

    /**
     * Loads any modules required to run the application.
     */
    public void loadModules(){
        this.dataTransformer = TransformModuleFactory.createTransformer();
        this.digraphFileReader = InputModuleFactory.createReader();
    }

    private void init() {
        String fileContents = readFile();
        Map<String, Node> dataMap = dataTransformer.transformIntoMap(fileContents);
        System.out.println(fileContents);
    }

    private String readFile() {
        String fileContents = null;
        try {
            fileContents = digraphFileReader.readDigraphFile(inputFile);
        } catch (InvalidFileContentsException e) {
            // print error log message
        } catch (EmptyFileContentsException e) {
            // print error log message
        }
        return fileContents;
    }

}
