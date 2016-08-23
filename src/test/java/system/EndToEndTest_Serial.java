package system;

import app.BranchAndBoundApp;
import app.Main;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EndToEndTest_Serial {

    private String inputFile;
    private int numProcessors;
    private int numThreads;
    private boolean visualisation;
    private String outputFile;

    @Test
    public void testNodes7_TwoProcessorsSerial_ReturnsCorrectLength() throws Exception {
        inputFile = "Nodes_7_OutTree.dot";
        numProcessors = 2;
        outputFile = "output.dot";
        numThreads = 0;

        int actualLength = runner(inputFile, numProcessors, numThreads, visualisation, outputFile);
        int expectedLength = 28;
        Assert.assertEquals(expectedLength, actualLength);
    }

    @Test
    public void testNodes7_FourProcessorsSerial_ReturnsCorrectLength() throws Exception {
        inputFile = "Nodes_7_OutTree.dot";
        numProcessors = 4;
        outputFile = "output.dot";
        numThreads = 0;

        int actualLength = runner(inputFile, numProcessors, numThreads, visualisation, outputFile);
        int expectedLength = 22;
        Assert.assertEquals(expectedLength, actualLength);
    }

    @Test
    public void testNodes8_TwoProcessorsSerial_ReturnsCorrectLength() throws Exception {
        inputFile = "Nodes_8_Random.dot";
        numProcessors = 2;
        outputFile = "output.dot";
        numThreads = 0;

        int actualLength = runner(inputFile, numProcessors, numThreads, visualisation, outputFile);
        int expectedLength = 581;
        Assert.assertEquals(expectedLength, actualLength);
    }

    @Test
    public void testNodes8_FourProcessorsSerial_ReturnsCorrectLength() throws Exception {
        inputFile = "Nodes_8_Random.dot";
        numProcessors = 4;
        outputFile = "output.dot";
        numThreads = 0;

        int actualLength = runner(inputFile, numProcessors, numThreads, visualisation, outputFile);
        int expectedLength = 581;
        Assert.assertEquals(expectedLength, actualLength);
    }

    @Test
    public void testNodes9_TwoProcessorsSerial_ReturnsCorrectLength() throws Exception {
        inputFile = "Nodes_9_SeriesParallel.dot";
        numProcessors = 2;
        outputFile = "output.dot";
        numThreads = 0;

        int actualLength = runner(inputFile, numProcessors, numThreads, visualisation, outputFile);
        int expectedLength = 55;
        Assert.assertEquals(expectedLength, actualLength);
    }

    @Test
    public void testNodes9_FourProcessorsSerial_ReturnsCorrectLength() throws Exception {
        inputFile = "Nodes_9_SeriesParallel.dot";
        numProcessors = 4;
        outputFile = "output.dot";
        numThreads = 0;

        int actualLength = runner(inputFile, numProcessors, numThreads, visualisation, outputFile);
        int expectedLength = 55;
        Assert.assertEquals(expectedLength, actualLength);
    }

    @Test
    public void testNodes10_TwoProcessorsSerial_ReturnsCorrectLength() throws Exception {
        inputFile = "Nodes_10_Random.dot";
        numProcessors = 2;
        outputFile = "output.dot";
        numThreads = 0;

        int actualLength = runner(inputFile, numProcessors, numThreads, visualisation, outputFile);
        int expectedLength = 50;
        Assert.assertEquals(expectedLength, actualLength);
    }

    @Test
    public void testNodes10_FourProcessorsSerial_ReturnsCorrectLength() throws Exception {
        inputFile = "Nodes_10_Random.dot";
        numProcessors = 4;
        outputFile = "output.dot";
        numThreads = 0;

        int actualLength = runner(inputFile, numProcessors, numThreads, visualisation, outputFile);
        int expectedLength = 50;
        Assert.assertEquals(expectedLength, actualLength);
    }

    @Test
    public void testNodes11_TwoProcessorsSerial_ReturnsCorrectLength() throws Exception {
        inputFile = "Nodes_11_OutTree.dot";
        numProcessors = 2;
        outputFile = "output.dot";
        numThreads = 0;

        int actualLength = runner(inputFile, numProcessors, numThreads, visualisation, outputFile);
        int expectedLength = 350;
        Assert.assertEquals(expectedLength, actualLength);
    }

    @Test
    public void testNodes11_FourProcessorsSerial_ReturnsCorrectLength() throws Exception {
        inputFile = "Nodes_11_OutTree.dot";
        numProcessors = 4;
        outputFile = "output.dot";
        numThreads = 0;

        int actualLength = runner(inputFile, numProcessors, numThreads, visualisation, outputFile);
        int expectedLength = 227;
        Assert.assertEquals(expectedLength, actualLength);
    }

    private int runner(String inputFileName, int numProcessors, int numThreads, boolean visualisation, String outputFileName) throws Exception {
        File inputFile = new File(Main.class.getClass().getResource("/" + inputFileName).toURI());
        BranchAndBoundApp app = new BranchAndBoundApp(inputFile, outputFileName, numProcessors, numThreads, visualisation);
        app.start();

        return extractLength(outputFileName);
    }

    private int extractLength(String outputFileName) throws IOException {

        String output = FileUtils.readFileToString(new File(outputFileName));

        String[] lines = output.split("\n");
        int latestEndTime = 0;
        for (String line : lines){
            if (line.contains("Weight") && line.contains("Start") && line.contains("Processor")){
                line = line.replaceAll("[^-?0-9]+", " ");
                List<String> splitUp = Arrays.asList(line.trim().split(" "));
                int weight = Integer.parseInt(splitUp.get(splitUp.size() - 3));
                int startTime = Integer.parseInt(splitUp.get(splitUp.size() - 2));
                int endTime = startTime + weight;
                if (endTime > latestEndTime){
                    latestEndTime = endTime;
                }
            }
        }

        return latestEndTime;

    }



}
