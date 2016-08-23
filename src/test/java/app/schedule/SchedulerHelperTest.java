package app.schedule;

import app.schedule.datatypes.Node;
import app.schedule.datatypes.PartialSolution;
import app.schedule.datatypes.Processor;
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

//    @Test
//    public void testGetAvailableNodes_EmptyScheduledNodesInput_ReturnsCorrectListOfRoots() throws AppException {
//        List<Node> expectedAvailableNodes = new ArrayList<>();
//        Node a = new Node("a", 1);
//        Node b = new Node("b", 1);
//        Node c = new Node("c", 1);
//        Node d = new Node("d", 1);
//        Node e = new Node("e", 1);
//        Node f = new Node("f", 1);
//        Node g = new Node("g", 1);
//        Node h = new Node("h", 1);
//
//        a.addChild(b, 1);
//        a.addChild(c, 1);
//        a.addChild(d, 1);
//        b.addParent(a, 1);
//        c.addParent(a, 1);
//        d.addParent(a, 1);
//
//        b.addChild(e, 1);
//        e.addParent(b, 1);
//
//        f.addChild(g, 1);
//        f.addChild(h, 1);
//        g.addParent(f, 1);
//        h.addParent(f, 1);
//
//        unscheduledNodes.add(a);
//        unscheduledNodes.add(b);
//        unscheduledNodes.add(c);
//        unscheduledNodes.add(d);
//        unscheduledNodes.add(e);
//        unscheduledNodes.add(f);
//        unscheduledNodes.add(g);
//        unscheduledNodes.add(h);
//
//        expectedAvailableNodes.add(a);
//        expectedAvailableNodes.add(f);
//
//        assertTrue(isEqualCollection(expectedAvailableNodes, schedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes)));
//    }
//
//    @Test
//    public void testGetAvailableNodes_EmptyUnscheduledNodesInput_ReturnsEmptyList() throws AppException {
//        List<Node> expectedAvailableNodes = new ArrayList<>();
//        Node a = new Node("a", 1);
//        Node b = new Node("b", 1);
//        Node c = new Node("c", 1);
//        Node d = new Node("d", 1);
//        Node e = new Node("e", 1);
//        Node f = new Node("f", 1);
//        Node g = new Node("g", 1);
//        Node h = new Node("h", 1);
//
//        a.addChild(b, 1);
//        a.addChild(c, 1);
//        a.addChild(d, 1);
//        b.addParent(a, 1);
//        c.addParent(a, 1);
//        d.addParent(a, 1);
//
//        b.addChild(e, 1);
//        e.addParent(b, 1);
//
//        f.addChild(g, 1);
//        f.addChild(h, 1);
//        g.addParent(f, 1);
//        h.addParent(f, 1);
//
//        scheduledNodes.add(a);
//        scheduledNodes.add(b);
//        scheduledNodes.add(c);
//        scheduledNodes.add(d);
//        scheduledNodes.add(e);
//        scheduledNodes.add(f);
//        scheduledNodes.add(g);
//        scheduledNodes.add(h);
//
//        assertTrue(isEqualCollection(expectedAvailableNodes, schedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes)));
//    }

//    @Test
//    public void testGetAvailableNodes_ComplexDAG_ReturnsCorrectList() throws AppException {
//        List<Node> expectedAvailableNodes = new ArrayList<>();
//        Node a = new Node("a", 1);
//        Node b = new Node("b", 1);
//        Node c = new Node("c", 1);
//        Node d = new Node("d", 1);
//        Node e = new Node("e", 1);
//        Node f = new Node("f", 1);
//        Node g = new Node("g", 1);
//        Node h = new Node("h", 1);
//        Node i = new Node("i", 1);
//        Node j = new Node("j", 1);
//        Node k = new Node("k", 1);
//        Node l = new Node("l", 1);
//
//        a.addChild(b, 1);
//        a.addChild(c, 1);
//        b.addParent(a, 1);
//        c.addParent(a, 1);
//
//        b.addChild(d, 1);
//        b.addChild(e, 1);
//        d.addParent(b, 1);
//        e.addParent(b, 1);
//
//        c.addChild(f, 1);
//        c.addChild(g, 1);
//        f.addParent(c, 1);
//        g.addParent(c, 1);
//
//        d.addChild(h, 1);
//        h.addParent(d, 1);
//
//        g.addChild(h, 1);
//        h.addParent(g, 1);
//
//        i.addChild(j, 1);
//        i.addChild(k, 1);
//        j.addParent(i, 1);
//        k.addParent(i, 1);
//
//        j.addChild(g, 1);
//        j.addChild(l, 1);
//        g.addParent(j, 1);
//        l.addParent(j, 1);
//
//        scheduledNodes.add(a);
//        scheduledNodes.add(b);
//        scheduledNodes.add(c);
//        scheduledNodes.add(d);
//        scheduledNodes.add(i);
//        scheduledNodes.add(j);
//
//        unscheduledNodes.add(e);
//        unscheduledNodes.add(f);
//        unscheduledNodes.add(g);
//        unscheduledNodes.add(h);
//        unscheduledNodes.add(k);
//        unscheduledNodes.add(l);
//
//        expectedAvailableNodes.add(e);
//        expectedAvailableNodes.add(f);
//        expectedAvailableNodes.add(g);
//        expectedAvailableNodes.add(k);
//        expectedAvailableNodes.add(l);
//
//        assertTrue(isEqualCollection(expectedAvailableNodes, schedulerHelper.getAvailableNodes(scheduledNodes, unscheduledNodes)));
//    }


    @Test
    public void testGetAvailablePartialSolutions_SingleProcessor_ReturnsCorrectList() {
        PartialSolution currentPartialSolution = new PartialSolution(1, new HashSet<Node>());

        Node a = new Node("a", 1);

        List<PartialSolution> actualAvailablePartialSolutions = schedulerHelper.getAvailablePartialSolutions(a, currentPartialSolution);

        Processor processor = actualAvailablePartialSolutions.get(0).getProcessors()[0];
        Queue<Node> nodeQueue = processor.getNodeQueue();
        Iterator<Node> nodeQueueIterator = nodeQueue.iterator();

        Node latestNode = null;
        while (nodeQueueIterator.hasNext()) {
            latestNode = nodeQueueIterator.next();
        }

        assertTrue(latestNode == a);

    }

    @Test
    public void testGetAvailablePartialSolutions_MultipleProcessors_ReturnsCorrectList(){
        PartialSolution currentPartialSolution = new PartialSolution(2, new HashSet<Node>());

        Node a = new Node("a", 1);

        List<PartialSolution> actualAvailablePartialSolutions = schedulerHelper.getAvailablePartialSolutions(a, currentPartialSolution);

        for (int i = 0; i < actualAvailablePartialSolutions.size(); i++){
            Processor processor = actualAvailablePartialSolutions.get(i).getProcessors()[i];
            Queue<Node> nodeQueue = processor.getNodeQueue();
            Iterator<Node> nodeQueueIterator = nodeQueue.iterator();

            Node latestNode = null;
            while(nodeQueueIterator.hasNext()){
                latestNode = nodeQueueIterator.next();
            }

            assertTrue(latestNode == a);
        }
    }


}
