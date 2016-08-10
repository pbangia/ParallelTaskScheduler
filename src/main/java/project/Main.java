package project;

import app.BranchAndBoundApp;
import app.argumentparsing.CommandLineArguments;
import app.exceptions.AppException;
import app.exceptions.argumentparsing.InvalidArgumentTypeException;
import app.exceptions.argumentparsing.MissingArgumentsException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException, AppException {

        CommandLineArguments cmdArgs = new CommandLineArguments();
        cmdArgs.parse(args);

        String inputFileName = cmdArgs.getInputFileName();
        String ouputFileName = cmdArgs.getOutputFileName();
        int numProcessors = cmdArgs.getNumberOfProcessors();
        int numThreads = cmdArgs.getNumberOfThreads();
        boolean visualiseSearch = cmdArgs.getVisualiseSearch();

        File inputFile = new File(inputFileName);
        BranchAndBoundApp app = new BranchAndBoundApp(inputFile, ouputFileName, numProcessors, numThreads, visualiseSearch);
        try {
            app.start();
        } catch (IOException e) {
            // add error log message
            e.printStackTrace();
        } catch (AppException e) {
            // add error log message
            e.printStackTrace();
        }

    }


}
