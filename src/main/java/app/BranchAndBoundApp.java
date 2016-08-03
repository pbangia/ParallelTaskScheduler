package app;

import app.data.Node;
import app.exceptions.AppException;
import app.input.DigraphFileReader;
import app.transform.IDataTransformer;
import app.transform.TransformModuleFactory;
import app.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class BranchAndBoundApp {

    private static Logger logger = LoggerFactory.getLogger(BranchAndBoundApp.class);

    private IDataTransformer dataTransformer;
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
    public void start() throws AppException, IOException {
        loadModules();
        init();
    }

    /**
     * Loads any modules required to run the application.
     */
    public void loadModules() {
        this.digraphFileReader = new DigraphFileReader(inputFile);
        this.dataTransformer = TransformModuleFactory.createTransformer();
    }

    private void init() throws AppException, IOException {
        String fileContents = digraphFileReader.readDigraphFile();
        Map<String, Node> dataMap = dataTransformer.transformIntoMap(fileContents);
        Node root = MapUtils.findRoot(dataMap);

        Iterator<String> keyIterator = dataMap.keySet().iterator();
        while (keyIterator.hasNext()) {
            System.out.println(dataMap.get(keyIterator.next()));
        }
    }

}
