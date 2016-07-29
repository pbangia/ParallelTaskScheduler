package project;

import app.BranchAndBoundApp;

import java.io.File;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException {

        String filename = "/input.dot";
        File inputFile = new File(Main.class.getClass().getResource(filename).toURI());
        BranchAndBoundApp app = new BranchAndBoundApp(inputFile, null, 0, 0, false);
        app.start();

    }


}
