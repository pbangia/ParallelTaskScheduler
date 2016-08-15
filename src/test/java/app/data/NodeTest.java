package app.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class NodeTest {

    private Node nodeInitialisedWithoutWeight;
    private Node nodeInitialisedWithWeight;
    private Node a;
    private Node b;
    private Integer dependencyWeight;

    @Before
    public void setUp() throws Exception {
        nodeInitialisedWithoutWeight = new Node("name");
        nodeInitialisedWithWeight = new Node("name", 1);
        a = new Node("a", 2);
        b = new Node("b", 2);
        dependencyWeight = 2;
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

    @Test
    public void testAddChildNode() {

        a.addChild(b, dependencyWeight);

        Assert.assertTrue(a.getChildrenMap().containsKey(b));
        Assert.assertEquals(a.getChildrenMap().get(b), dependencyWeight);
    }

    @Test
    public void testAddParentNode() {
        b.addParent(a, 2);
        b.getParentMap().containsKey(a);
        Assert.assertEquals(b.getParentMap().get(a), dependencyWeight);
    }

}