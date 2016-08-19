package app;

import app.argumentparsing.CommandLineArguments;
import app.exceptions.AppException;
import app.schedule.BranchThread;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException, AppException {

//        CommandLineArguments cmdArgs = new CommandLineArguments();
//        cmdArgs.parse(args);
//
//        String inputFileName = cmdArgs.getInputFileName();
//        String outputFileName = cmdArgs.getOutputFileName();
//        int numProcessors = cmdArgs.getNumberOfProcessors();
//        int numThreads = cmdArgs.getNumberOfThreads();
//        boolean visualiseSearch = cmdArgs.getVisualiseSearch();

//        File inputFile = new File(inputFileName);
//        BranchAndBoundApp app = new BranchAndBoundApp(inputFile, outputFileName, numProcessors, numThreads, visualiseSearch);

        String filename = "/Nodes_7_OutTree.dot";
        File inputFile = new File(Main.class.getClass().getResource(filename).toURI());
        BranchAndBoundApp app = new BranchAndBoundApp(inputFile,"out.dot", 2, 1, false);
        try {
            app.start();
        } catch (IOException e) {
            // add error log message
            e.printStackTrace();
        } catch (AppException e) {
            // add error log message
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
