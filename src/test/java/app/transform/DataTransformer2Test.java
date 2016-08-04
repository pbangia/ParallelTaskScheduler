package app.transform;

import app.exceptions.transform.EmptyMapException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class DataTransformer2Test {

    private DataTransformer2 dataTransformer;
    private Map<String, Integer> dataMap;

    @Before
    public void setUp() throws Exception {
        dataTransformer = new DataTransformer2(new StringToMatrixTransformation());
    }

    @Test(expected=EmptyMapException.class)
    public void testEmptyDigraph() throws EmptyMapException {
        String emptyDigraph = "";
        dataTransformer.transformIntoMap(emptyDigraph);
    }

    @Test
    public void testMapTransformation() throws EmptyMapException {

        Map<String, Integer> expectedMap = new ConcurrentHashMap<String, Integer>();

        expectedMap.put("a", 0);
        expectedMap.put("b", 1);

        String digraph = "a [Weight=2];\nb [Weight=3];\na -> b [Weight=1];";
        dataMap = dataTransformer.transformIntoMap(digraph);

        Assert.assertEquals(expectedMap, dataMap);
    }


    @Test
    public void testMatrixTransformation() throws EmptyMapException{

        String digraph = "a [Weight=2];\nb [Weight=3];\na -> b [Weight=1];";
        dataMap = dataTransformer.transformIntoMap(digraph);
        int [][] matrix = dataTransformer.transformIntoMatrix(dataMap, digraph);

        int[][] expectedMatrix = new int[dataMap.size()][dataMap.size()];
        expectedMatrix[0][0] = 2;
        expectedMatrix[1][1] = 3;
        expectedMatrix[0][1] = 1;
        expectedMatrix[1][0] = -1;
        Assert.assertArrayEquals(expectedMatrix, matrix);
    }

    @Test
    public void testMatrixTransformationForInitialization() throws EmptyMapException{

        String digraph = "a [Weight=2];\nb [Weight=3];";
        dataMap = dataTransformer.transformIntoMap(digraph);
        int [][] matrix = dataTransformer.transformIntoMatrix(dataMap, digraph);

        Assert.assertEquals(true, matrix [0][1] == -1);
    }
}