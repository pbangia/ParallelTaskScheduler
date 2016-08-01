package project;

import app.input.DigraphFileReader;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import java.io.File;

public class DigraphFileReaderTest extends TestCase {

    private DigraphFileReader digraphReader;
    private DigraphFileReader invalidDigraphReader;
    private DigraphFileReader digraphReaderNull;



    @Before
    public void setUp() throws Exception {
        super.setUp();

        File validInputFile = new File(Main.class.getClass().getResource("/shortEx.dot").toURI());
        File invalidInputFile = new File(Main.class.getClass().getResource("/invalid.dot").toURI());
        File emptyInputFile = new File(Main.class.getClass().getResource("/empty.dot").toURI());

        digraphReader = new DigraphFileReader(validInputFile);
        invalidDigraphReader = new DigraphFileReader(invalidInputFile);
        digraphReaderNull = new DigraphFileReader(emptyInputFile);
    }

    @Test
    public void testNullDigraph() throws Exception {
        try {
            digraphReaderNull.readDigraphFile();
        } catch(Exception e) {
            assertEquals("File contents are empty.", e.getMessage());
        }
    }

    @Test
    public void testInvalidText() throws Exception {
        try {
            invalidDigraphReader.readDigraphFile();
        } catch(Exception e) {
            assertEquals("Invalid text found in file.", e.getMessage());
        }
    }

    @Test
    public void testValidDigraph() throws Exception {
        String expected = "a [Weight=1];";
        String result = digraphReader.readDigraphFile().trim();

        assertEquals(expected, result);
    }
}
