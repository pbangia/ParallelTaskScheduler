package app.io;

import app.Main;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class DigraphFileWriterTest {

    private StringBuilder sb;
    private DigraphFileWriter digraphFileWriter;

    @Before
    public void setup(){
        digraphFileWriter = new DigraphFileWriter("output.dot");
    }

    @Test
    public void testWriteToFile_EmptyString_ResultsInEmptyFile() throws IOException {
        String empty = "";
        digraphFileWriter.writeDigraphFile(empty);
        File outputFile = new File("output.dot");
        assertEquals(FileUtils.readFileToString(outputFile), empty);
    }

    @Test
    public void testWriteToFile_PopulatedString_ResultsInValidFile() throws IOException {
        sb = new StringBuilder();
        sb.append("digraph \"" + "output" + "\" {\n");
        sb.append("a\t[Weight=2,Start=0,Processor=1];\n");
        sb.append("b\t[Weight=3,Start=2,Processor=1];\n");
        sb.append("c\t[Weight=3,Start=4,Processor=2];\n");
        sb.append("d\t[Weight=2,Start=7,Processor=2];\n");
        sb.append("a -> b\t[Weight=1];\n");
        sb.append("a -> c\t[Weight=2];\n");
        sb.append("b -> d\t[Weight=2];\n");
        sb.append("c -> d\t[Weight=1];\n");
        sb.append("}");
        String toWrite = sb.toString();
        digraphFileWriter.writeDigraphFile(toWrite);
        File outputFile = new File("output.dot");
        assertEquals(FileUtils.readFileToString(outputFile), toWrite);
    }

}