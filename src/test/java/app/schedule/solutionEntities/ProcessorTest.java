package app.schedule.solutionEntities;

import app.data.Node;
import app.exceptions.AppException;
import app.schedule.SchedulerHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.apache.commons.collections4.CollectionUtils.isEqualCollection;
import static org.junit.Assert.assertEquals;
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


}