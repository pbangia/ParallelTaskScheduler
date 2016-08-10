package app.argumentparsing;

import app.exceptions.argumentparsing.InvalidArgumentTypeException;
import app.exceptions.argumentparsing.MissingArgumentsException;

public class CommandLineArguments {

    private String inputFileName;
    private int numberOfProcessors;
    private int numberOfThreads;
    private boolean visualiseSearch;
    private String outputFileName;

    public CommandLineArguments() {
        numberOfThreads = 1;
        visualiseSearch = false;
    }

    /**
     * This method takes in the arguments passed through the command line and parses them to initialise
     * the CommandLineArguments instance
     * @param args arguments passed through the command line
     */
    public void parse(String[] args) throws InvalidArgumentTypeException, MissingArgumentsException {
        if(args.length < 2) {
            throw new MissingArgumentsException("Arguments missing.");
        }

        if(!args[0].endsWith(".dot")){
            throw new InvalidArgumentTypeException("Input File not of .dot format.");

        } else {
            inputFileName = args[0];
            outputFileName = args[0].substring(0, args[0].length() - 4).concat("-output.dot");
        }

        numberOfProcessors = Integer.parseInt(args[1]);

        for(int i = 2; i < args.length; i++) {
            switch(args[i]) {
                case "-p":
                    i++;
                    numberOfThreads = Integer.parseInt(args[i]);
                    break;
                case "-v":
                    visualiseSearch = true;
                    break;
                case "-o":
                    i++;
                    outputFileName = args[i];
                    if(!outputFileName.endsWith(".dot")) {
                        outputFileName = outputFileName.concat(".dot");
                    }
                    break;
            }
        }
    }

    // Getters
    public String getInputFileName() { return this.inputFileName; }
    public int getNumberOfProcessors() { return this.numberOfProcessors; }
    public int getNumberOfThreads() { return this.numberOfThreads; }
    public boolean getVisualiseSearch() { return this.visualiseSearch; }
    public String getOutputFileName() { return this.outputFileName; }
}
