package app.schedule;

import app.data.Node;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TaskSchedulerFactoryTest {

    @Test
    public void testFactoryCreation() {
        Map<String, Node> map = new HashMap<>();
        int numberOfProcessors = 0;
        Assert.assertTrue(TaskSchedulerFactory.createTaskScheduler(map, numberOfProcessors) instanceof TaskScheduler);
    }
}
