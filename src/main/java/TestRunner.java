import app.BranchAndBoundApp;
import app.exceptions.AppException;
import graphvisualization.MainGUI;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class TestRunner {

    public static void main(String[] args) throws URISyntaxException, AppException {

        String inputFileName = "/Nodes_7_OutTree.dot";
        String outputFileName = "output.dot";
        int numProcessors = 2;
        int numThreads = 1;
        boolean visualiseSearch = false;

        File inputFile = new File(TestRunner.class.getClass().getResource(inputFileName).toURI());
        BranchAndBoundApp app = new BranchAndBoundApp(inputFile, outputFileName, numProcessors, numThreads, visualiseSearch);
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
