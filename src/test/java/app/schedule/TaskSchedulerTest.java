package app.schedule;

import app.schedule.datatypes.Node;
import org.junit.Before;

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
//
//    @Test
//    public void testScheduler_TwoProcessors() throws AppException, InterruptedException {
//        Node a = new Node("a", 3);
//        Node b = new Node("b", 2);
//
//        a.addChild(b, 20);
//
//        dataMap.put("a", a);
//        dataMap.put("b", b);
//
//        ParallelScheduler taskScheduler = new ParallelScheduler(dataMap.values(), schedulerHelper, 2, 1);
//        PartialSolution bestSolution = taskScheduler.scheduleTasks();
//
//        Assert.assertEquals(bestSolution.getNumberOfProcessors(), 2);
//
//        Processor[] processors = bestSolution.getProcessors();
//        Processor processor = processors [1];
//
//        Assert.assertEquals(processor.getCurrentTimeStamp(), 5);
//    }
}