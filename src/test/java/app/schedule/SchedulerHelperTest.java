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
    private Set<Node> unscheduledNodes;

    @Before
    public void setup(){
        dataMap = new ConcurrentHashMap<>();
        this.schedulerHelper = new SchedulerHelper();
        this.scheduledNodes = new HashSet<>();
        this.unscheduledNodes = new HashSet<>();
    }

    @Test
    public void testFindRoot_ComplexDAG_ReturnsCorrectRoots() throws AppException {
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
        List<Node> expectedRoots = new ArrayList<>();
        expectedRoots.add(a);
        assertTrue(isEqualCollection(expectedRoots, schedulerHelper.findRoots(dataMap)));
    }

    @Test
    public void testFindRoot_DisconnectedGraph_ReturnsMultipleRoots() throws AppException {
        Node a = new Node("a", 1);
        Node b = new Node("b", 1);
        Node c = new Node("c", 1);
        dataMap.put("a", a);
        dataMap.put("b", b);
        dataMap.put("c", c);
        List<Node> expectedRoots = new ArrayList<>();
        expectedRoots.add(a);
        expectedRoots.add(b);
        expectedRoots.add(c);
        assertTrue(isEqualCollection(expectedRoots, schedulerHelper.findRoots(dataMap)));
    }

    @Test
    public void testFindRoot_NoSingleRoot_ReturnsMultipleRoots() throws AppException {
        Node a = new Node("a", 1);
        Node b = new Node("b", 1);
        Node c = new Node("c", 1);
        Node d = new Node("d", 1);
        Node e = new Node("e", 1);
        Node f = new Node("f", 1);
        a.addChild(c, 1);
        a.addChild(d, 1);
        b.addChild(e, 1);
        d.addChild(f, 1);
        e.addChild(f, 1);
        dataMap.put("a", a);
        dataMap.put("b", b);
        dataMap.put("c", c);
        dataMap.put("d", d);
        dataMap.put("e", e);
        dataMap.put("f", f);
        List<Node> expectedRoots = new ArrayList<>();
        expectedRoots.add(a);
        expectedRoots.add(b);
        assertTrue(isEqualCollection(expectedRoots, schedulerHelper.findRoots(dataMap)));
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
    public void testGetAvailableNodes_EmptyScheduledNodesInput_ReturnsCorrectListOfRoots() throws AppException {
        List<Node> expectedAvailableNodes = new ArrayList<>();
        Node a = new Node("a", 1);
        Node b = new Node("b", 1);
        Node c = new Node("c", 1);
        Node d = new Node("d", 1);
        Node e = new Node("e", 1);
        Node f = new Node("f", 1);
        Node g = new Node("g", 1);
        Node h = new Node("h", 1);

        a.addChild(b, 1);
        a.addChild(c, 1);
        a.addChild(d, 1);
        b.addParent(a, 1);
        c.addParent(a, 1);
        d.addParent(a, 1);

        b.addChild(e, 1);
        e.addParent(b, 1);

        f.addChild(g, 1);
        f.addChild(h, 1);
        g.addParent(f, 1);
        h.addParent(f, 1);

        unscheduledNodes.add(a);
        unscheduledNodes.add(b);
        unscheduledNodes.add(c);
        unscheduledNodes.add(d);
        unscheduledNodes.add(e);
        unscheduledNodes.add(f);
        unscheduledNodes.add(g);
        unscheduledNodes.add(h);

        expectedAvailableNodes.add(a);
        expectedAvailableNodes.add(f);

        assertTrue(isEqualCollection(expectedAvailableNodes, schedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes)));
    }

    @Test
    public void testGetAvailableNodes_EmptyUnscheduledNodesInput_ReturnsEmptyList() throws AppException {
        List<Node> expectedAvailableNodes = new ArrayList<>();
        Node a = new Node("a", 1);
        Node b = new Node("b", 1);
        Node c = new Node("c", 1);
        Node d = new Node("d", 1);
        Node e = new Node("e", 1);
        Node f = new Node("f", 1);
        Node g = new Node("g", 1);
        Node h = new Node("h", 1);

        a.addChild(b, 1);
        a.addChild(c, 1);
        a.addChild(d, 1);
        b.addParent(a, 1);
        c.addParent(a, 1);
        d.addParent(a, 1);

        b.addChild(e, 1);
        e.addParent(b, 1);

        f.addChild(g, 1);
        f.addChild(h, 1);
        g.addParent(f, 1);
        h.addParent(f, 1);

        scheduledNodes.add(a);
        scheduledNodes.add(b);
        scheduledNodes.add(c);
        scheduledNodes.add(d);
        scheduledNodes.add(e);
        scheduledNodes.add(f);
        scheduledNodes.add(g);
        scheduledNodes.add(h);

        assertTrue(isEqualCollection(expectedAvailableNodes, schedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes)));
    }

//    @Test
//    public void testGetAvailableNodes_ComplexDAG_ReturnsCorrectList() throws AppException {
//        List<Node> expectedAvailableNodes = new ArrayList<>();
//        Node a = new Node("a", 1);
//        Node b = new Node("b", 1);
//        Node c = new Node("c", 1);
//        Node d = new Node("d", 1);
//        a.addChild(c, 1);
//        a.addChild(d, 1);
//        b.addChild(c, 1);
//        c.addParent(a, 1);
//        c.addParent(b, 1);
//        d.addParent(a, 1);
//        dataMap.put("a", a);
//        dataMap.put("b", b);
//        dataMap.put("c", c);
//        dataMap.put("d", d);
//        scheduledNodes.add(a);
//
//        expectedAvailableNodes.add(d);
//
//        assertTrue(isEqualCollection(expectedAvailableNodes, schedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes)));
//    }

}
