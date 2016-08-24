package app.schedule.datatypes;

import app.exceptions.AppException;
import org.junit.Before;
import org.junit.Test;

import static org.apache.commons.collections4.CollectionUtils.isEqualCollection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ProcessorTest {

    private Processor processor;

    @Before
    public void setup(){
        this.processor = new Processor();
    }

    @Test
    public void testProcessorInstantiation_CorrectlyInitialFields() throws AppException {
        assertTrue(processor.getCurrentTimeStamp() == 0);
        assertTrue(processor.getNodeStartTimeMap().size() == 0);
        assertTrue(processor.getNodeQueue().size() == 0);
    }

    @Test
    public void testAddNodeToQueue_NoWaitTime_CorrectlyUpdatesFields() throws AppException {
        Node a = new Node("a", 1);
        processor.addNodeToQueue(a, 0);

        assertTrue(processor.getCurrentTimeStamp() == 1);
        assertTrue(processor.getNodeStartTimeMap().get(a) == 0);
        assertTrue(processor.getNodeQueue().size() == 1);

    }

    @Test
    public void testAddNodeToQueue_WithWaitTime_CorrectlyUpdatesTimeStampField() throws AppException {
        Node a = new Node("a", 2);
        processor.addNodeToQueue(a, 1);

        assertTrue(processor.getCurrentTimeStamp() == 3);
    }

    @Test
    public void testClone() throws Exception {
        Node nodeA = new Node("A", 1);
        Node nodeB = new Node("B", 1);
        processor.addNodeToQueue(nodeA, 0);
        processor.addNodeToQueue(nodeB, 3);

        Processor clone = processor.clone();

        assertEquals(true, isEqualCollection(processor.getNodeQueue(), clone.getNodeQueue()));
        assertEquals(processor.getNodeSet(), clone.getNodeSet());
        assertEquals(processor.getCurrentTimeStamp(), clone.getCurrentTimeStamp());
    }

    @Test
    public void testGetTimeStamp() {
        Node node = new Node("A", 1);
        processor.addNodeToQueue(node, 1);
        assertEquals(1, processor.getTimeStamp(node));
    }

    @Test
    public void testGetTimeStamp_Null() {
        Node node = new Node("B", 2);

        assertEquals(-1, processor.getTimeStamp(node));
    }

}