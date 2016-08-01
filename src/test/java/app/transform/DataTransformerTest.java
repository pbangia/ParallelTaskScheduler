package app.transform;

import app.data.Node;
import app.exceptions.transform.EmptyMapException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class DataTransformerTest {

    private DataTransformer dataTransformer;
    private Map<String, Node> dataMap;

    @Before
    public void setUp() throws Exception {
        dataTransformer = new DataTransformer(new StringToMapTransformation());
    }

    @Test(expected=EmptyMapException.class)
    public void testEmptyDigraph() throws EmptyMapException {
        String emptyDigraph = "";
        dataTransformer.transformIntoMap(emptyDigraph);
    }

    @Test
    public void testMapTransformation() throws EmptyMapException {

        Map<String, Node> expectedMap = new ConcurrentHashMap<String, Node>();
        Node a = new Node("a", 2);
        Node b = new Node("b", 3);

        a.addChild(b, 1);

        expectedMap.put("a", a);
        expectedMap.put("b", b);

        String digraph = "a [Weight=2];\nb [Weight=3];\na -> b [Weight=1];";
        dataMap = dataTransformer.transformIntoMap(digraph);

        Assert.assertEquals(expectedMap, dataMap);
    }
}