package app.io;

import app.exceptions.input.EmptyFileContentsException;
import app.exceptions.input.InvalidFileContentsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import Main;

import java.io.File;
import java.io.IOException;

public class DigraphFileReaderTest {

    private DigraphFileReader digraphReader;
    private DigraphFileReader invalidDigraphReader;
    private DigraphFileReader emptyDigraphReader;

    @Before
    public void setUp() throws Exception {

        File validInputFile = new File(Main.class.getClass().getResource("/shortEx.dot").toURI());
        File invalidInputFile = new File(Main.class.getClass().getResource("/invalid.dot").toURI());
        File emptyInputFile = new File(Main.class.getClass().getResource("/empty.dot").toURI());

        digraphReader = new DigraphFileReader(validInputFile);
        invalidDigraphReader = new DigraphFileReader(invalidInputFile);
        emptyDigraphReader = new DigraphFileReader(emptyInputFile);
    }

    @Test(expected=EmptyFileContentsException.class)
    public void testEmptyDigraph() throws EmptyFileContentsException, InvalidFileContentsException, IOException {
        emptyDigraphReader.readDigraphFile();
    }

    @Test(expected=InvalidFileContentsException.class)
    public void testInvalidText() throws EmptyFileContentsException, InvalidFileContentsException, IOException {
        invalidDigraphReader.readDigraphFile();
    }

    @Test
    public void testValidDigraph() throws EmptyFileContentsException, InvalidFileContentsException, IOException {
        String expected = "a [Weight=1];";
        String result = digraphReader.readDigraphFile().trim();

        Assert.assertEquals(expected, result);
    }
}
