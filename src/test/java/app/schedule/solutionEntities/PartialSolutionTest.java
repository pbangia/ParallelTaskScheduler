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

    @Before
    public void setUp() throws Exception {
        firstPS = new PartialSolution(2);
        secondPS = new PartialSolution(1);
    }

    @Test
    public void testIsBetterThan() throws Exception {

    }

    @Test
    public void testLength() throws Exception {

    }

    @Test
    public void testAddNodeToProcessor() throws Exception {
        Node newNode = new Node("A", 2);
        firstPS.addNodeToProcessor(newNode, 1, 2);

        assertEquals(firstPS.getLatestNode().getName(), "A");
        assertEquals(firstPS.getLatestNode().getWeight(), 2);
        assertTrue(firstPS.getScheduledNodes().contains(newNode));
    }

    @Test
    public void testClone() throws Exception {
        Node n1a = new Node("A",1);
        Node n1b = new Node("B",1);
        Queue<Node> nq1 = new ConcurrentLinkedQueue<Node>();
        nq1.add(n1a);
        nq1.add(n1b);
        Map<Integer, Integer> ts1=new ConcurrentHashMap<Integer, Integer>();
        ts1.put(1,1);
        ts1.put(2,2);
        Map<Node, Integer> ets1=new ConcurrentHashMap<Node, Integer>();
        ets1.put(n1a,1);
        ets1.put(n1b,1);
        Processor p1 = new Processor();

        p1.setQueueSize(1);
        p1.setCurrentTimeStamp(1);
        p1.setTimeStampMap(ts1);
        p1.setNodeQueue(nq1);
        p1.setNodeEndTimeMap(ets1);

        Processor p2 = p1.clone();

        assertNotEquals(p1,p2);

        assertEquals(p1.getCurrentTimeStamp(),p2.getCurrentTimeStamp());

        assertEquals(p1.getQueueSize(),p2.getQueueSize());

        assertTrue(CollectionUtils.isEqualCollection(p1.getNodeQueue(),p2.getNodeQueue()));
        assertNotEquals(p1.getNodeQueue(),p2.getNodeQueue());

        //Keys+Values are equal collections. Keys (nodes) are the same objects, values are not.
        assertTrue(CollectionUtils.isEqualCollection(p1.getNodeEndTimeMap().values(),p2.getNodeEndTimeMap().values()));
        assertNotEquals(p1.getNodeEndTimeMap().values(),p2.getNodeEndTimeMap().values());
        assertTrue(CollectionUtils.isEqualCollection(p1.getNodeEndTimeMap().keySet(),p2.getNodeEndTimeMap().keySet()));
        assertEquals(p1.getNodeEndTimeMap().keySet(),p2.getNodeEndTimeMap().keySet());
        assertEquals(p1.getNodeEndTimeMap(),p2.getNodeEndTimeMap());

        //unsure. assertEquals for maps compares contents of maps according to docs, not reference.
        assertEquals(p1.getTimeStampMap(),p2.getTimeStampMap());
        assertNotEquals(p1.getTimeStampMap().values(),p2.getTimeStampMap().values());


    }

}