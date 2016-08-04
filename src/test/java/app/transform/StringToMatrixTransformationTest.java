package app.transform;

import app.data.Node;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StringToMatrixTransformationTest {

    private Map<String, Integer> dataMap;
    private StringToMatrixTransformation toMatrixTransformation;
    private int[][] matrix;

    @Before
    public void setUp() throws Exception {
        toMatrixTransformation = new StringToMatrixTransformation();
        dataMap = new ConcurrentHashMap<String, Integer>();
        matrix = new int [5][5];
    }

    @Test
    public void testNodeDeclaration_NodeExists_RemainsTheSame(){
        String definition = "a [Weight=2]";
        dataMap.put("a", 0);

        Assert.assertEquals(0, (int) dataMap.get("a"));

        toMatrixTransformation.parseNodeDeclaration(definition, dataMap, 2);
        Assert.assertEquals(0, (int) dataMap.get("a"));
    }

    @Test
    public void testNodeDeclaration_NodeDoesNotExist_AddsToMap(){
        String definition = "a [Weight=2]";
        toMatrixTransformation.parseNodeDeclaration(definition, dataMap, 2);
        Assert.assertEquals(2, (int) dataMap.get("a"));
    }


//    @Test
//    public void testNodeDependency_ChildDoesNotExist_AddsCorrectDependency(){
//        String definition = "a -> b [Weight=1]";
//        dataMap.put("a", 0);
//
//        toMatrixTransformation.parseNodeDependency(definition, dataMap, matrix);
//        Assert.assertEquals(true, dataMap.get("b") != null);
//        Assert.assertEquals(1, matrix[0][dataMap.get("b")]);
//    }
//
//    @Test
//    public void testNodeDependency_ParentDoesNotExist_AddsCorrectDependency(){
//        String definition = "a -> b [Weight=1]";
//        dataMap.put("b", 1);
//        toMatrixTransformation.parseNodeDependency(definition, dataMap, matrix);
//        Assert.assertEquals(true, dataMap.get("a") != null);
//        Assert.assertEquals(1, matrix[dataMap.get("a")][1]);
//    }
//
//    @Test
//    public void testNodeDependency_ChildAndParentDoNotExist_AddsCorrectDependency(){
//        String definition = "a -> b [Weight=1]";
//        toMatrixTransformation.parseNodeDependency(definition, dataMap, matrix);
//        Assert.assertEquals(true, dataMap.get("a") != null);
//        Assert.assertEquals(true, dataMap.get("b") != null);
//    }

    @Test
    public void testNodeDependency_ChildAndParentExist_AddsCorrectDependency(){
        String definition = "a -> b [Weight=1]";
        dataMap.put("a", 0);
        dataMap.put("b", 1);
        toMatrixTransformation.parseNodeDependency(definition, dataMap, matrix);
        Assert.assertEquals(1, matrix[0][1]);
    }

    @Test
    public void testNodeWeightDeclaration_NodeExistsInDataMap_AddsCorrectWeight(){
        String definition = "a [Weight=2]";
        dataMap.put("a", 0);
        toMatrixTransformation.parseNodeWeight(definition, dataMap, matrix);
        Assert.assertEquals(2, matrix[0][0]);
    }

}