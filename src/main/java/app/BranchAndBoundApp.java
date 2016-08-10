package app;

import app.data.Node;
import app.exceptions.AppException;
import app.io.DigraphFileReader;
import app.io.DigraphFileWriter;
import app.io.OutputGenerator;
import app.schedule.TaskScheduler;
import app.schedule.TaskSchedulerFactory;
import app.data.PartialSolution;
import app.transform.IDataTransformer;
import app.transform.IDataTransformer2;
import app.transform.TransformModuleFactory;
import app.transform.TransformModuleFactory2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class BranchAndBoundApp {

    private static Logger logger = LoggerFactory.getLogger(BranchAndBoundApp.class);

    // Input parameters
    private File inputFile;
    private String outputFilename;
    private int numThreads;
    private int numProcessors;
    private boolean graphRequired;

    // Implemented modules
    private DigraphFileReader digraphFileReader;
    private DigraphFileWriter digraphFileWriter;
    private IDataTransformer dataTransformer;
    private IDataTransformer2 dataTransformer2;
    private TaskScheduler taskScheduler;
    private OutputGenerator outputGenerator;

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
        Map<String, Node> dataMap = readInput();
        PartialSolution bestSolution = run(dataMap);
        String output = outputGenerator.generateOutput(bestSolution, dataTransformer.getDependencies());
        logger.error(output);
        digraphFileWriter.writeDigraphFile(output);
    }

    /**
     * Loads any modules required to run the application.
     */
    private void loadModules() {
        this.digraphFileReader = new DigraphFileReader(inputFile);
        this.dataTransformer = TransformModuleFactory.createTransformer();
        this.dataTransformer2 = TransformModuleFactory2.createTransformer();
        this.outputGenerator = new OutputGenerator(outputFilename);
        this.digraphFileWriter = new DigraphFileWriter(outputFilename);
        logger.info("Loaded all modules required for application.");
    }

    /**
     * Reads the io file and transforms that data.
     * @return The transformed data in a Map representation
     * @throws AppException
     * @throws IOException
     */
    private Map<String, Node> readInput() throws AppException, IOException {
        String fileContents = digraphFileReader.readDigraphFile();
        Map<String, Node> dataMap = dataTransformer.transformIntoMap(fileContents);

        // TODO figure out where and how to use this data transformation
        Map<String, Integer> dataMap2 = dataTransformer2.transformIntoMap(fileContents);
        int[][] matrix = dataTransformer2.transformIntoMatrix(dataMap2, fileContents);

        logger.info("Initialised all required components for application.");
        return dataMap;
    }

    /**
     * Runs the scheduler algorithm on the provided io data.
     * @param dataMap The transformed data provided from the io file of the program.
     * @return The best scheduled solution generated by the algorithm.
     */
    private PartialSolution run(Map<String, Node> dataMap) throws AppException {
        taskScheduler = TaskSchedulerFactory.createTaskScheduler(dataMap, numProcessors);
        return taskScheduler.scheduleTasks();
    }



}
