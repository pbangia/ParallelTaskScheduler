package app.schedule;

import app.data.Node;
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
    public void testNodes_7_OutTree() {
        Node a = new Node("a", 5);
        Node b = new Node("b", 6);
        Node c = new Node("c", 5);
        Node d = new Node("d", 6);
        Node e = new Node("e", 4);
        Node f = new Node("f", 7);
        Node g = new Node("g", 7);

        a.addChild(b, 15);
        b.addParent(a, 15);










    }
}