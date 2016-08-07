package app.schedule;

import app.data.Node;
import app.exceptions.AppException;
import app.exceptions.utils.NoRootFoundException;
import app.schedule.SchedulerHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class SchedulerHelperTest {

    private Map<String, Node> dataMap;
    private SchedulerHelper schedulerHelper;

    @Before
    public void setup(){
        dataMap = new ConcurrentHashMap<>();
        this.schedulerHelper = new SchedulerHelper();
    }

    @Test
    public void testFindRoot_SimpleDAG_ReturnsCorrectRoot() throws AppException {
        Node a = new Node("a", 1);
        Node b = new Node("b", 1);
        Node c = new Node("c", 1);
        a.addChild(b, 1);
        b.addChild(c, 1);
        dataMap.put("a", a);
        dataMap.put("b", b);
        dataMap.put("c", c);
        assertEquals(a, schedulerHelper.findRoot(dataMap));
    }

    @Test
    public void testFindRoot_ComplexDAG_ReturnsCorrectRoot() throws AppException {
        Node a = new Node("a", 1);
        Node b = new Node("b", 1);
        Node c = new Node("c", 1);
        Node d = new Node("d", 1);
        Node e = new Node("e", 1);
        Node f = new Node("f", 1);
        Node g = new Node("g", 1);
        a.addChild(b, 1);
        a.addChild(e, 1);
        b.addChild(d, 1);
        b.addChild(c, 1);
        e.addChild(f, 1);
        e.addChild(g, 1);
        dataMap.put("a", a);
        dataMap.put("b", b);
        dataMap.put("c", c);
        dataMap.put("d", d);
        dataMap.put("e", e);
        dataMap.put("f", f);
        dataMap.put("g", g);
        assertEquals(a, schedulerHelper.findRoot(dataMap));
    }


    @Test(expected = NoRootFoundException.class)
    public void testFindRoot_DisconnectedGraph_ThrowsException() throws AppException {
        dataMap.put("a", new Node("a", 1));
        dataMap.put("b", new Node("b", 1));
        schedulerHelper.findRoot(dataMap);
    }

    @Test(expected = NoRootFoundException.class)
    public void testFindRoot_NoSingleRoot_ThrowsException() throws AppException {
        Node b = new Node("b", 1);
        dataMap.put("a", new Node("a", 1));
        dataMap.put("b", b);
        dataMap.put("c", new Node("c", 1));
        dataMap.get("a").addChild(b, 1);
        dataMap.get("c").addChild(b, 1);
        schedulerHelper.findRoot(dataMap);
    }

    @Test(expected = NoRootFoundException.class)
    public void testFindRoot_CyclicGraph_ThrowsException() throws AppException {
        Node a = new Node("a", 1);
        Node b = new Node("b", 1);
        Node c = new Node("c", 1);
        a.addChild(b, 1);
        b.addChild(c, 1);
        c.addChild(a, 1);
        dataMap.put("a", a);
        dataMap.put("b", b);
        dataMap.put("c", c);
        schedulerHelper.findRoot(dataMap);
    }

}