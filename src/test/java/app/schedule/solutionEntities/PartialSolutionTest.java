package app.schedule.solutionEntities;

import app.data.Node;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.*;

public class PartialSolutionTest {

    private PartialSolution firstPS;
    private PartialSolution secondPS;
    private Node newNode;

    @Before
    public void setUp() throws Exception {
        firstPS = new PartialSolution(2);
        secondPS = new PartialSolution(1);
        newNode = new Node("A", 2);
    }

    @Test
    public void testIsBetterThan() throws Exception {

    }

    @Test
    public void testLength() throws Exception {

    }

    @Test
    public void testAddNodeToProcessor() throws Exception {
        firstPS.addNodeToProcessor(newNode, 1);

        assertEquals(firstPS.getLatestNode().getName(), "A");
        assertEquals(firstPS.getLatestNode().getWeight(), 2);
        assertTrue(firstPS.getScheduledNodes().contains(newNode));
    }

    @Test
    public void testClone() throws Exception {

    }

}