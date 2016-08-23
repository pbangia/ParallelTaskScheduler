package app.schedule;

import app.schedule.datatypes.Node;
import app.schedule.serial.SerialScheduler;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SerialSchedulerFactoryTest {

    @Test
    public void testFactoryCreation() {
        Map<String, Node> map = new HashMap<>();
        int numberOfProcessors = 0;
        int numThreads = 0;
        Assert.assertTrue(CommonSchedulerFactory.createTaskScheduler(map, numberOfProcessors, numThreads) instanceof SerialScheduler);
    }
}