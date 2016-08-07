package app.schedule;

import app.data.Node;
import app.exceptions.AppException;
import app.exceptions.utils.NoRootFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.commons.collections4.CollectionUtils.isEqualCollection;
import static org.junit.Assert.*;

public class SchedulerHelperTest {

    private Map<String, Node> dataMap;
    private SchedulerHelper schedulerHelper;
    private Set<Node> scheduledNodes;

    @Before
    public void setup(){
        dataMap = new ConcurrentHashMap<>();
        this.schedulerHelper = new SchedulerHelper();
        this.scheduledNodes = new HashSet<>();
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
        assertEquals(a, schedulerHelper.findRoots(dataMap));
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
        assertEquals(a, schedulerHelper.findRoots(dataMap));
    }


    @Test(expected = NoRootFoundException.class)
    public void testFindRoot_DisconnectedGraph_ThrowsException() throws AppException {
        dataMap.put("a", new Node("a", 1));
        dataMap.put("b", new Node("b", 1));
        schedulerHelper.findRoots(dataMap);
    }

    @Test(expected = NoRootFoundException.class)
    public void testFindRoot_NoSingleRoot_ThrowsException() throws AppException {
        Node b = new Node("b", 1);
        dataMap.put("a", new Node("a", 1));
        dataMap.put("b", b);
        dataMap.put("c", new Node("c", 1));
        dataMap.get("a").addChild(b, 1);
        dataMap.get("c").addChild(b, 1);
        schedulerHelper.findRoots(dataMap);
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
        schedulerHelper.findRoots(dataMap);
    }

    @Test
    public void testGetAvailableNodes_OnlyOneParent_ReturnsCorrectList() throws AppException {
        List<Node> expectedAvailableNodes = new ArrayList<>();
        Node a = new Node("a", 1);
        Node b = new Node("b", 1);
        Node c = new Node("c", 1);
        Node d = new Node("d", 1);
        a.addChild(b, 1);
        a.addChild(c, 1);
        a.addChild(d, 1);
        dataMap.put("a", a);
        dataMap.put("b", b);
        dataMap.put("c", c);
        dataMap.put("d", d);
        scheduledNodes.add(a);
        expectedAvailableNodes.add(b);
        expectedAvailableNodes.add(c);
        expectedAvailableNodes.add(d);

        assertTrue(isEqualCollection(expectedAvailableNodes, schedulerHelper.getAvailableNodes(a, dataMap, scheduledNodes)));
    }

}