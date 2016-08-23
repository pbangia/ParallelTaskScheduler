package app;

import app.argumentparsing.CommandLineArguments;
import app.exceptions.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws URISyntaxException, AppException {

        CommandLineArguments cmdArgs = new CommandLineArguments();
        cmdArgs.parse(args);

        String inputFileName = cmdArgs.getInputFileName();
        String outputFileName = cmdArgs.getOutputFileName();
        int numProcessors = cmdArgs.getNumberOfProcessors();
        int numThreads = cmdArgs.getNumberOfThreads();
        boolean visualiseSearch = cmdArgs.getVisualiseSearch();

        File inputFile = new File(inputFileName);
        BranchAndBoundApp app = new BranchAndBoundApp(inputFile, outputFileName, numProcessors, numThreads, visualiseSearch);

        try {
            app.start();
        } catch (IOException | AppException | InterruptedException e) {
            logger.error("Error : " + e.getMessage());
        }

    }


}
