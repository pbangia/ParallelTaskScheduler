package app.data;

import app.data.Node;
import app.data.PartialSolution;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class PartialSolutionTest {

    private PartialSolution firstPS;
    private PartialSolution secondPS;
    private Node newNode1;
    private Node newNode2;

    @Before
    public void setUp() throws Exception {
        firstPS = new PartialSolution(2, new HashSet<Node>());
        secondPS = new PartialSolution(3, new HashSet<Node>());
        newNode1 = new Node("A", 2);
        newNode2 = new Node("B", 4);
    }

    @Test
    public void testIsBetterThan() throws Exception {
        firstPS.addNodeToProcessor(newNode1, 1);
        secondPS.addNodeToProcessor(newNode2, 2);

        assertTrue(firstPS.isBetterThan(secondPS));
    }

//    @Test
//    public void testLength() throws Exception {
//        firstPS.addNodeToProcessor(newNode1, 1);
//        assertEquals(firstPS.length(), 2);
//    }

    @Test
    public void testAddNodeToProcessor() throws Exception {

    }

    @Test
    public void testClone() throws Exception {
        PartialSolution clonedSolution = new PartialSolution(3, firstPS);

        assertEquals(clonedSolution.getNumberOfProcessors(), firstPS.getNumberOfProcessors());
    }

}