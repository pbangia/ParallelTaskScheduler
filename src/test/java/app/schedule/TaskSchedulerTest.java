package app.schedule;

import app.data.Node;
import app.data.PartialSolution;
import app.data.Processor;
import app.exceptions.AppException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskSchedulerTest {

    private Map<String, Node> dataMap;
    private SchedulerHelper schedulerHelper;
    private int numberOfPrcoessors;

    @Before
    public void setup(){
        this.schedulerHelper = new SchedulerHelper();
        this.dataMap = new ConcurrentHashMap<>();
    }

    @Test
    public void testScheduler_TwoProcessors() throws AppException {
        Node a = new Node("a", 3);
        Node b = new Node("b", 2);

        a.addChild(b, 20);

        dataMap.put("a", a);
        dataMap.put("b", b);

        TaskScheduler taskScheduler = new TaskScheduler(dataMap.values(), schedulerHelper, 2, 1);
        PartialSolution bestSolution = taskScheduler.scheduleTasks();

        Assert.assertEquals(bestSolution.getNumberOfProcessors(), 2);

        Processor[] processors = bestSolution.getProcessors();
        Processor processor = processors [1];

        Assert.assertEquals(processor.getCurrentTimeStamp(), 5);
    }
}