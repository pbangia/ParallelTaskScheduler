package app.transform;

import app.schedule.datatypes.Node;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StringToMapTransformationTest {

    private Map<String, Node> dataMap;
    private StringToMapTransformation toMapTransformation;

    @Before
    public void setUp() throws Exception {
        toMapTransformation = new StringToMapTransformation();
        dataMap = new ConcurrentHashMap<String, Node>();
    }

    @Test
    public void testNodeDeclaration_NodeExists_UpdatesWeight(){
        String definition = "a [Weight=2]";
        dataMap.put("a", new Node("a"));
        Assert.assertEquals(0, dataMap.get("a").getWeight());
        toMapTransformation.parseNodeDeclaration(definition, dataMap);
        Assert.assertEquals(2, dataMap.get("a").getWeight());
    }

    @Test
    public void testNodeDeclaration_NodeDoesNotExist_CreatesNodeAndAddsToMap(){
        String definition = "a [Weight=2]";
        toMapTransformation.parseNodeDeclaration(definition, dataMap);
        Assert.assertEquals(2, dataMap.get("a").getWeight());
    }

    @Test
    public void testNodeDependency_ChildDoesNotExist_AddsChildDependency(){
        String definition = "a -> b [Weight=1]";
        dataMap.put("a", new Node("a", 100));
        toMapTransformation.parseNodeDependency(definition, dataMap);
        Assert.assertEquals(true, dataMap.get("a").getChildrenMap().containsValue(1));
    }

    @Test
    public void testNodeDependency_ChildDoesNotExist_AddsParentDependency(){
        String definition = "a -> b [Weight=1]";
        dataMap.put("a", new Node("a", 100));
        toMapTransformation.parseNodeDependency(definition, dataMap);
        Assert.assertEquals(true, dataMap.get("b").getParentMap().containsValue(1));
    }

    @Test
    public void testNodeDependency_ParentDoesNotExist_AddsChildDependency(){
        String definition = "a -> b [Weight=1]";
        dataMap.put("b", new Node("b", 100));
        toMapTransformation.parseNodeDependency(definition, dataMap);
        Assert.assertEquals(true, dataMap.get("a").getChildrenMap().containsValue(1));
    }

    @Test
    public void testNodeDependency_ParentDoesNotExist_AddsParentDependency(){
        String definition = "a -> b [Weight=1]";
        dataMap.put("b", new Node("b", 100));
        toMapTransformation.parseNodeDependency(definition, dataMap);
        Assert.assertEquals(true, dataMap.get("b").getParentMap().containsValue(1));
    }

    @Test
    public void testNodeDependency_ChildAndParentDoNotExist_AddsChildDependency(){
        String definition = "a -> b [Weight=1]";
        toMapTransformation.parseNodeDependency(definition, dataMap);
        Assert.assertEquals(true, dataMap.get("a").getChildrenMap().containsValue(1));
    }

    @Test
    public void testNodeDependency_ChildAndParentDoNotExist_AddsParentDependency(){
        String definition = "a -> b [Weight=1]";
        toMapTransformation.parseNodeDependency(definition, dataMap);
        Assert.assertEquals(true, dataMap.get("b").getParentMap().containsValue(1));
    }

    @Test
    public void testNodeDependency_ChildAndParentExist_AddsChildDependency(){
        String definition = "a -> b [Weight=1]";
        Node a = new Node("a", 100);
        Node b = new Node("b", 100);
        dataMap.put("a", a);
        dataMap.put("b", b);
        toMapTransformation.parseNodeDependency(definition, dataMap);
        Assert.assertEquals(new Integer(1), dataMap.get("a").getChildrenMap().get(b));
    }

    @Test
    public void testNodeDependency_ChildAndParentExist_AddsParentDependency(){
        String definition = "a -> b [Weight=1]";
        Node a = new Node("a", 100);
        Node b = new Node("b", 100);
        dataMap.put("a", a);
        dataMap.put("b", b);
        toMapTransformation.parseNodeDependency(definition, dataMap);
        Assert.assertEquals(new Integer(1), dataMap.get("b").getParentMap().get(a));
    }

}