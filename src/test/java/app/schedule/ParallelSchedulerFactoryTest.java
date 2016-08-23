package app.schedule;

import app.data.Node;
import app.schedule.parallel.ParallelScheduler;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ParallelSchedulerFactoryTest {

    @Test
    public void testFactoryCreation() {
        Map<String, Node> map = new HashMap<>();
        int numberOfProcessors = 0;
        int numThreads = 1;
        Assert.assertTrue(CommonSchedulerFactory.createTaskScheduler(map, numberOfProcessors, numThreads) instanceof ParallelScheduler);
    }
}
