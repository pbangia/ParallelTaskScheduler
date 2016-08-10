package app.data;

import app.data.Node;
import app.data.Processor;
import app.exceptions.AppException;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.apache.commons.collections4.CollectionUtils.isEqualCollection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by User on 9/08/2016.
 */
public class ProcessorTest {

    private Processor processor;

    @Before
    public void setup(){
        this.processor = new Processor();
    }

    @Test
    public void testProcessorInstantiation_CorrectlyInitialFields() throws AppException {
        assertTrue(processor.getQueueSize() == 0);
        assertTrue(processor.getCurrentTimeStamp() == 0);
        assertTrue(processor.getTimeStampMap().size() == 0);
        assertTrue(processor.getNodeQueue().size() == 0);
    }

    @Test
    public void testAddNodeToQueue_noWaitTime_CorrectlyUpdatesFields() throws AppException {
        Node a = new Node("a", 1);
        processor.addNodeToQueue(a, 0);

        assertTrue(processor.getQueueSize() == 1);
        assertTrue(processor.getCurrentTimeStamp() == 1);
        assertTrue(processor.getTimeStampMap().size() == 1);
        assertTrue(processor.getNodeQueue().size() == 1);

    }

    @Test
    public void testAddNodeToQueue_withWaitTime_CorrectlyUpdatesTimeStampField() throws AppException {
        Node a = new Node("a", 2);
        processor.addNodeToQueue(a, 1);

        assertTrue(processor.getCurrentTimeStamp() == 3);
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