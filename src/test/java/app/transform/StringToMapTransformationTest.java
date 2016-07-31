package app.transform;

import app.data.Node;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StringToMapTransformationTest extends TestCase {

    private Map<String, Node> dataMap;
    private StringToMapTransformation toMapTransformation;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        toMapTransformation = new StringToMapTransformation();
        dataMap = new ConcurrentHashMap<String, Node>();
    }

    @Test
    public void testNodeDeclaration_NodeExists_UpdatesWeight(){
        String definition = "a [Weight=2]";
        dataMap.put("a", new Node("a"));
        assertEquals(0, dataMap.get("a").getWeight());
        toMapTransformation.parseNodeDeclaration(definition, dataMap);
        assertEquals(2, dataMap.get("a").getWeight());
    }

    @Test
    public void testNodeDeclaration_NodeDoesNotExist_CreatesNodeAndAddsToMap(){
        String definition = "a [Weight=2]";
        toMapTransformation.parseNodeDeclaration(definition, dataMap);
        assertEquals(2, dataMap.get("a").getWeight());
    }

    @Test
    public void testNodeDependency_ChildDoesNotExist_AddsCorrectDependency(){
        String definition = "a -> b [Weight=1]";
        dataMap.put("a", new Node("a", 100));
        toMapTransformation.parseNodeDependency(definition, dataMap);
        assertEquals(true, dataMap.get("a").getChildrenMap().containsValue(1));
    }

    @Test
    public void testNodeDependency_ParentDoesNotExist_AddsCorrectDependency(){
        String definition = "a -> b [Weight=1]";
        dataMap.put("b", new Node("b", 100));
        toMapTransformation.parseNodeDependency(definition, dataMap);
        assertEquals(true, dataMap.get("a").getChildrenMap().containsValue(1));
    }

    @Test
    public void testNodeDependency_ChildAndParentDoNotExist_AddsCorrectDependency(){
        String definition = "a -> b [Weight=1]";
        toMapTransformation.parseNodeDependency(definition, dataMap);
        assertEquals(true, dataMap.get("a").getChildrenMap().containsValue(1));
    }

    @Test
    public void testNodeDependency_ChildAndParentExist_AddsCorrectDependency(){
        String definition = "a -> b [Weight=1]";
        Node a = new Node("a", 100);
        Node b = new Node("b", 100);
        dataMap.put("a", a);
        dataMap.put("b", b);
        toMapTransformation.parseNodeDependency(definition, dataMap);
        assertEquals(new Integer(1), dataMap.get("a").getChildrenMap().get(b));
    }

}