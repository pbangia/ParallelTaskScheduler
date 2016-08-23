package app.io;


import app.schedule.datatypes.PartialSolution;
import app.schedule.datatypes.Node;
import app.schedule.datatypes.Processor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class OutputGeneratorTest {

    private Node a;
    private Node b;
    private Node c;
    private Node d;
    private String dependencyAB;
    private String dependencyAC;
    private String dependencyBD;
    private String dependencyCD;
    private List<String> dependencies;
    private PartialSolution partialSolution;
    private Processor[] processors;
    private StringBuilder sb;


    @Before
    public void setUp() throws Exception {
        Node a = new Node("a", 2);
        Node b = new Node("b", 3);
        Node c = new Node("c", 3);
        Node d = new Node("d", 2);

        dependencies = new ArrayList<>();
        dependencyAB = new String("a " + Syntax.DEPENDENCY_ARROW + " b\t" + Syntax.OPENING_BRACE
                + Syntax.WEIGHT_FIELD_NAME + "1" + Syntax.CLOSING_BRACE + Syntax.DEFINITION_DELIMITER + "\n");
        dependencyAC = new String("a " + Syntax.DEPENDENCY_ARROW + " c\t" + Syntax.OPENING_BRACE
                + Syntax.WEIGHT_FIELD_NAME + "2" + Syntax.CLOSING_BRACE + Syntax.DEFINITION_DELIMITER + "\n");
        dependencyBD = new String("b " + Syntax.DEPENDENCY_ARROW + " d\t" + Syntax.OPENING_BRACE
                + Syntax.WEIGHT_FIELD_NAME + "2" + Syntax.CLOSING_BRACE + Syntax.DEFINITION_DELIMITER + "\n");
        dependencyCD = new String("c " + Syntax.DEPENDENCY_ARROW + " d\t" + Syntax.OPENING_BRACE
                + Syntax.WEIGHT_FIELD_NAME + "1" + Syntax.CLOSING_BRACE + Syntax.DEFINITION_DELIMITER + "\n");

        dependencies.add(dependencyAB);
        dependencies.add(dependencyAC);
        dependencies.add(dependencyBD);
        dependencies.add(dependencyCD);

        partialSolution = new PartialSolution(2, new HashSet<Node>());
        processors = partialSolution.getProcessors();
        processors[0].addNodeToQueue(a, 0);
        processors[0].addNodeToQueue(b, 2);
        processors[1].addNodeToQueue(c, 4);
        processors[1].addNodeToQueue(d, 7);

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
    }

    @Test
    public void testValidOutputGenerated() {
        OutputGenerator outputGenerator = new OutputGenerator("output");
        String result = outputGenerator.generateOutput(partialSolution, dependencies);
        String expected = sb.toString();

        Assert.assertEquals(expected, result);

    }

}
