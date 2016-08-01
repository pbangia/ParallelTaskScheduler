package project;

import app.input.DigraphFileReader;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

/**
 * Created by John on 1/08/2016.
 */
public class DigraphFileReaderTest {

    private DigraphFileReader digraphReader;
    private DigraphFileReader invalidDigraphReader;
    private DigraphFileReader digraphReaderNull;



    @Before
    public void setUp() throws URISyntaxException {
        File validInputFile = new File(Main.class.getClass().getResource("/src/test/resources/shortEx.dot").toURI());
        File invalidInputFile = new File(Main.class.getClass().getResource("/src/test/resources/invalid.dot").toURI());
        File emptyInputFile = new File(Main.class.getClass().getResource("/src/test/resources/empty.dot").toURI());

        digraphReader = new DigraphFileReader(validInputFile);
        invalidDigraphReader = new DigraphFileReader(invalidInputFile);
        digraphReaderNull = new DigraphFileReader(emptyInputFile);
    }

    @Test
    public void NullDigraphTest() throws Exception {
        try {
            digraphReaderNull.readDigraphFile();
        } catch(Exception e) {
            assertEquals("File contents are empty.", e.getMessage());
        }
    }

    @Test
    public void InvalidTextTest() throws Exception {
        try {
            invalidDigraphReader.readDigraphFile();
        } catch(Exception e) {
            assertEquals("Invalid text found in file.", e.getMessage());
        }
    }

    @Test
    public void ValidDigraphTest() throws Exception {
        String expected = "a [Weight=1];";
        String result = digraphReader.readDigraphFile().trim();

        assertEquals(expected, result);
    }
}
