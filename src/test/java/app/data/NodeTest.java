package app.data;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class NodeTest {

    private Node nodeInitialisedWithoutWeight;
    private Node nodeInitialisedWithWeight;

    @Before
    public void setUp() throws Exception {
        nodeInitialisedWithoutWeight = new Node("name");
        nodeInitialisedWithWeight = new Node("name", 1);
    }

    @Test
    public void testNode_PostConstruct_InitialisesScheduledBool() throws Exception {
        assertFalse(nodeInitialisedWithoutWeight.hasBeenScheduled());
        assertFalse(nodeInitialisedWithoutWeight.hasBeenScheduled());
    }

    @Test
    public void getChildrenMap() throws Exception {
        Map<Node, Integer> childrenMap1 = nodeInitialisedWithoutWeight.getChildrenMap();
        Map<Node, Integer> childrenMap2 = nodeInitialisedWithWeight.getChildrenMap();
        assertNotNull(childrenMap1);
        assertNotNull(childrenMap2);
    }

    @Test
    public void getParentMap() throws Exception {
        Map<Node, Integer> parentMap1 = nodeInitialisedWithoutWeight.getParentMap();
        Map<Node, Integer> parentMap2 = nodeInitialisedWithWeight.getParentMap();
        assertNotNull(parentMap1);
        assertNotNull(parentMap2);
    }

}