package app.argumentparsing;

import app.exceptions.argumentparsing.MissingArgumentsException;
import app.exceptions.argumentparsing.InvalidArgumentTypeException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by gtiongco95 on 6/08/16.
 */
public class CommandLineArgumentsTest {

    private String[] validArgumentsNoOptions;
    private String[] validArgumentsWithOrderedOptions;
    private String[] validArgumentsWithUnorderedOptions;
    private String[] invalidFileName;
    private String[] missingArguments;

    @Before
    public void setUp() throws Exception {
        validArgumentsNoOptions = new String[] {"validFile.dot", "4"};
        validArgumentsWithOrderedOptions = new String[] {"validFile.dot", "4", "-p", "3", "-v", "-o", "outputName"};
        validArgumentsWithUnorderedOptions = new String[] {"validFile.dot", "4", "-o", "outputName", "-p", "3"};
        invalidFileName = new String[] {"invalidFile.pdf", "4"};
        missingArguments = new String[] {"validFile.dot"};

    }

    @Test
    public void testValidArgumentsNoOptions() throws InvalidArgumentTypeException, MissingArgumentsException {
        String expectedFileName = "validFile.dot";
        int expectedNumberOfProcessors = 4;

        CommandLineArguments result = new CommandLineArguments();
        result.parse(validArgumentsWithOrderedOptions);

        Assert.assertEquals(expectedFileName, result.getInputFileName());
        Assert.assertEquals(expectedNumberOfProcessors, result.getNumberOfProcessors());
    }

    @Test
    public void testValidArgumentsWithOrderedOptions() throws InvalidArgumentTypeException, MissingArgumentsException {
        String expectedFileName = "validFile.dot";
        int expectedNumberOfProcessors = 4;
        int expectedNumberOfThreads = 3;
        String expectedOutputFileName = "outputName.dot";
        boolean expectedVisualiseSearch = true;

        CommandLineArguments result = new CommandLineArguments();
        result.parse(validArgumentsWithOrderedOptions);

        Assert.assertEquals(expectedFileName, result.getInputFileName());
        Assert.assertEquals(expectedNumberOfProcessors, result.getNumberOfProcessors());
        Assert.assertEquals(expectedNumberOfThreads, result.getNumberOfThreads());
        Assert.assertEquals(expectedOutputFileName, result.getOutputFileName());
        Assert.assertEquals(expectedVisualiseSearch, result.getVisualiseSearch());
    }

    @Test
    public void testValidArgumentsWithUnorderedOptions() throws InvalidArgumentTypeException, MissingArgumentsException {
        String expectedFileName = "validFile.dot";
        int expectedNumberOfProcessors = 4;
        int expectedNumberOfThreads = 3;
        String expectedOutputFileName = "outputName.dot";
        boolean expectedVisualiseSearch = false;

        CommandLineArguments result = new CommandLineArguments();
        result.parse(validArgumentsWithUnorderedOptions);

        Assert.assertEquals(expectedFileName, result.getInputFileName());
        Assert.assertEquals(expectedNumberOfProcessors, result.getNumberOfProcessors());
        Assert.assertEquals(expectedNumberOfThreads, result.getNumberOfThreads());
        Assert.assertEquals(expectedOutputFileName, result.getOutputFileName());
        Assert.assertEquals(expectedVisualiseSearch, result.getVisualiseSearch());
    }

    @Test(expected=InvalidArgumentTypeException.class)
    public void testInvalidFileName() throws InvalidArgumentTypeException, MissingArgumentsException {
        CommandLineArguments cmdArgs = new CommandLineArguments();
        cmdArgs.parse(invalidFileName);
    }

    @Test(expected=MissingArgumentsException.class)
    public void testMissingArguments() throws InvalidArgumentTypeException, MissingArgumentsException {
        CommandLineArguments cmdArgs = new CommandLineArguments();
        cmdArgs.parse(missingArguments);
    }

}
