package app.transform;

import app.exceptions.transform.EmptyMapException;

import java.util.Map;

public interface IDataTransformer2 {

    Map<String, Integer> transformIntoMap(String data) throws EmptyMapException;

    int[][] transformIntoMatrix(Map<String, Integer> dataMap, String data);

    String transformIntoString(Map<String, Integer> data);

}