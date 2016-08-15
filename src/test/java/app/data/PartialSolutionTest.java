package app.data;

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

    @Test
    public void testToString() {
        newNode1.addChild(newNode2, 2);
        newNode2.addParent(newNode1, 2);
        firstPS.addNodeToProcessor(newNode1, 0);
        firstPS.addNodeToProcessor(newNode2, 1);

        String expected = "A\t[Weight=2,Start=0,Processor=1];\nB\t[Weight=4,Start=4,Processor=2];\n";
        String result = firstPS.toString();

        assertEquals(expected, result);
    }

    @Test
    public void testClone() throws Exception {
        firstPS.getScheduledNodes().add(newNode1);
        PartialSolution clonedSolution = new PartialSolution(3, firstPS);

        assertEquals(clonedSolution.getNumberOfProcessors(), firstPS.getNumberOfProcessors());
        assertTrue(clonedSolution.getScheduledNodes().contains(newNode1));
    }

    @Test
    public void testAddNodeToProcessor_SingleProcessor_CorrectlyUpdatesPartialSolutionAndProcessor(){
        Node nodeToAdd = new Node("A", 1);
        HashSet<Node> unscheduledNodes = new HashSet<>();
        unscheduledNodes.add(nodeToAdd);
        PartialSolution partialSolution = new PartialSolution(1, unscheduledNodes);
        partialSolution.addNodeToProcessor(nodeToAdd, 0);

        assertTrue(partialSolution.getScheduledNodes().contains(nodeToAdd));
        assertEquals(0, partialSolution.getUnscheduledNodes().size());
        assertEquals(1, partialSolution.getProcessors()[0].getCurrentTimeStamp());
    }

    @Test
    public void testAddNodeToProcessor_ConflictingParent_CorrectlyUpdatesProcessor(){
        Node nodeToAdd = new Node("A", 1);
        Node parent = new Node("B", 5);
        parent.addChild(nodeToAdd, 10);
        nodeToAdd.addParent(parent, 10);
        HashSet<Node> unscheduledNodes = new HashSet<>();
        unscheduledNodes.add(nodeToAdd);
        unscheduledNodes.add(parent);
        PartialSolution partialSolution = new PartialSolution(2, unscheduledNodes);
        partialSolution.addNodeToProcessor(parent, 0);
        partialSolution.addNodeToProcessor(nodeToAdd, 1);

        assertEquals(15, partialSolution.getProcessors()[1].getTimeStamp(nodeToAdd));
    }

    @Test
    public void testAddNodeToProcessor_NonConflictingParent_CorrectlyUpdatesProcessor(){
        Node nodeToAdd = new Node("A", 1);
        Node parent = new Node("B", 5);
        parent.addChild(nodeToAdd, 10);
        nodeToAdd.addParent(parent, 10);
        HashSet<Node> unscheduledNodes = new HashSet<>();
        unscheduledNodes.add(nodeToAdd);
        unscheduledNodes.add(parent);
        PartialSolution partialSolution = new PartialSolution(2, unscheduledNodes);
        partialSolution.addNodeToProcessor(parent, 1);
        partialSolution.addNodeToProcessor(nodeToAdd, 1);

        assertEquals(5, partialSolution.getProcessors()[1].getTimeStamp(nodeToAdd));
    }

}