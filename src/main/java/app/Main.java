package app;

import app.argumentparsing.CommandLineArguments;
import app.exceptions.AppException;
import graphvisualization.SolutionVisualizer;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


public class Main {

    public static void main(String[] args) throws Exception, URISyntaxException, AppException {

        Graph g = new SingleGraph("hello");

        SolutionVisualizer sv = new SolutionVisualizer();

        sv.setSolutionTree(g);

        if (sv.getSolutionTree() == null) {
            System.out.println("Is null");
        } else {
            System.out.println("Not null");
        }
        //sv.test();

//        CommandLineArguments cmdArgs = new CommandLineArguments();
//        cmdArgs.parse(args);
//
//        String inputFileName = cmdArgs.getInputFileName();
//        String outputFileName = cmdArgs.getOutputFileName();
//        int numProcessors = cmdArgs.getNumberOfProcessors();
//        int numThreads = cmdArgs.getNumberOfThreads();
//        boolean visualiseSearch = cmdArgs.getVisualiseSearch();
//
//        File inputFile = new File(inputFileName);
//        BranchAndBoundApp app = new BranchAndBoundApp(inputFile, outputFileName, numProcessors, numThreads, visualiseSearch);
//        try {
//            app.start();
//        } catch (IOException e) {
//            // add error log message
//            e.printStackTrace();
//        } catch (AppException e) {
//            // add error log message
//            e.printStackTrace();
//        }

    }


}
