package project;

import app.BranchAndBoundApp;
import app.exceptions.input.EmptyFileContentsException;
import app.exceptions.input.InvalidFileContentsException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException {

        String filename = "/input.dot";
        File inputFile = new File(Main.class.getClass().getResource(filename).toURI());
        BranchAndBoundApp app = new BranchAndBoundApp(inputFile, null, 0, 0, false);
        try {
            app.start();
        } catch (EmptyFileContentsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFileContentsException e) {
            e.printStackTrace();
        }

    }


}
