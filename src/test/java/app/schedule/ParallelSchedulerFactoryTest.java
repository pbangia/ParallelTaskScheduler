package app.schedule;

import app.data.Node;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ParallelSchedulerFactoryTest {

    @Test
    public void testFactoryCreation() {
        Map<String, Node> map = new HashMap<>();
        int numberOfProcessors = 0;
        int numThreads = 0;
        Assert.assertTrue(TaskSchedulerFactory.createTaskScheduler(map, numberOfProcessors, numThreads) instanceof ParallelScheduler);
    }
}
